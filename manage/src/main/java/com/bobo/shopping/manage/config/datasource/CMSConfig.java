package com.bobo.shopping.manage.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.bobo.shopping.manage.config.entity.datasource.ShoppingCMSDataSourceProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * cms数据库
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 17:20
 **/
@Configuration
@MapperScan(basePackages = CMSConfig.PACKAGE, sqlSessionFactoryRef = "cmsSqlSessionFactory")
public class CMSConfig {

    static final String PACKAGE = "com.bobo.shopping.manage.dao.cms.mapper";
    static final String MAPPER_LOCATION = "classpath:mapper/cms/*.xml";

    @Bean(name = "cmsDataSource")
    public DataSource configurationDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        //设置数据源的属性
        setDruidProperties(dataSource);
        return dataSource;
    }

    /**
     * 注入数据源配置信息
     */
    @Autowired
    ShoppingCMSDataSourceProperties cmsConfig;

    /**
     * 设置数据源的属性的方法
     * @param dataSource
     */
    private void setDruidProperties(DruidDataSource dataSource) {
        dataSource.setUrl(cmsConfig.url);
        dataSource.setUsername(cmsConfig.username);
        dataSource.setPassword(cmsConfig.password);
        dataSource.setDriverClassName(cmsConfig.driverClassName);
        dataSource.setMaxActive(cmsConfig.maxActive);
        dataSource.setInitialSize(cmsConfig.initialSize);
        dataSource.setMinIdle(cmsConfig.minIdle);
        dataSource.setMaxWait(cmsConfig.maxWait);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(cmsConfig.maxPoolPreparedStatementPerConnectionSize);
        dataSource.setTimeBetweenEvictionRunsMillis(cmsConfig.timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(cmsConfig.minEvictableIdleTimeMillis);
        dataSource.setPoolPreparedStatements(cmsConfig.poolPreparedStatements);
    }

    @Bean(name = "cmsTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(configurationDataSource());
    }

    @Bean(name = "cmsSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("cmsDataSource") DataSource clusterDataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(CMSConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
