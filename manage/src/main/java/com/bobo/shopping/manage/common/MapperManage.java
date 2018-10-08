package com.bobo.shopping.manage.common;

/**
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-30 18:17
 **/

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MapperManage {
    private static final Logger logger = LoggerFactory.getLogger(MapperManage.class);

    private static Map<String, Object> xmlLoadedCache = new ConcurrentHashMap<>();

    public static <T> T getBean(Class<T> clazz){

        if (xmlLoadedCache.get(clazz.getName()) == null){
            synchronized (MapperManage.class){
                if (xmlLoadedCache.get(clazz.getName()) == null){
                    //load xml
                    loadMapperXml(clazz.getName());
                    xmlLoadedCache.put(clazz.getName(), getProxyMapper(clazz));
                }
            }
        }

        return (T)xmlLoadedCache.get(clazz.getName());
    }

    private static <T> Object getProxyMapper(Class<T> clazz) {
        String schema = getSchema(clazz.getName());
        DefaultSqlSessionFactory defaultSqlSessionFactory = (DefaultSqlSessionFactory) SpringUtils.getBean("sqlSessionFactory_" + schema);
        return new SqlSessionTemplate(defaultSqlSessionFactory).getMapper(clazz);
    }


    private static void loadMapperXml(String clzFullName){

        String schema = getSchema(clzFullName);
        String mapperName = getMapperName(clzFullName);

        DefaultSqlSessionFactory defaultSqlSessionFactory = (DefaultSqlSessionFactory) SpringUtils.getBean("sqlSessionFactory_" + schema);
        if (defaultSqlSessionFactory == null){
            logger.error("{} is null,请添加依赖：compile('com.didapinche:autoconfigure-mysql-didapinche-slave:1.0.0-SNAPSHOT')", "sqlSessionFactory_" + schema);
            return;
        }

        try {
            Configuration configuration =defaultSqlSessionFactory.getConfiguration();

            Resource mapperLocation = new ClassPathResource(String.format("%s/%s.xml", getXmlFullPath(clzFullName), mapperName));
            if(mapperLocation != null) {
                try {
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(), configuration, mapperLocation.toString(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
                } finally {
                    ErrorContext.instance().reset();
                }
                logger.info("Parsed mapper file: '" + mapperLocation + "'");
            }

        } catch (Exception e) {
            logger.error("loadMapperXml failed.", e);
        }
    }


    private static String getSchema(String clzFullName) {
        String[] path  = clzFullName.split("\\.");
        return path[path.length - 3];
    }


    private static String getMapperName(String clzFullName) {
        return clzFullName.substring(clzFullName.lastIndexOf(".") + 1);
    }


    private static String getXmlFullPath(String clzFullName) {
        String[] path  = clzFullName.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < path.length - 2; i++) {
            sb.append(path[i]);
            sb.append("/");
        }
        return sb.toString() + "xml";
    }
}
