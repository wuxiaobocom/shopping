package com.bobo.shopping.manage.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.bobo.shopping.manage.config.entity.datasource.ShoppingDataSourceProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * shopping数据库
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 17:20
 **/
@Configuration
@MapperScan(basePackages = ShoppingConfig.PACKAGE, sqlSessionFactoryRef = "shoppingSqlSessionFactory")
public class ShoppingConfig {

    static final String PACKAGE = "com.bobo.shopping.manage.dao.shopping.mapper";
    static final String MAPPER_LOCATION = "classpath:mapper/shopping/*.xml";

    @Bean(name = "shoppingDataSource")
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
    ShoppingDataSourceProperties config;

    /**
     * 设置数据源的属性的方法
     * @param dataSource
     */
    private void setDruidProperties(DruidDataSource dataSource) {
        dataSource.setUrl(config.url);
        dataSource.setUsername(config.username);
        dataSource.setPassword(config.password);
        dataSource.setDriverClassName(config.driverClassName);
        dataSource.setMaxActive(config.maxActive);
        dataSource.setInitialSize(config.initialSize);
        dataSource.setMinIdle(config.minIdle);
        dataSource.setMaxWait(config.maxWait);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(config.maxPoolPreparedStatementPerConnectionSize);
        dataSource.setTimeBetweenEvictionRunsMillis(config.timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(config.minEvictableIdleTimeMillis);
        dataSource.setPoolPreparedStatements(config.poolPreparedStatements);
    }

    @Bean(name = "shoppingTransactionManager")
    @Primary
    public DataSourceTransactionManager clusterTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager =
                new DataSourceTransactionManager(configurationDataSource());
        return dataSourceTransactionManager;
    }

    @Bean(name = "shoppingSqlSessionFactory")
    @Primary
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("shoppingDataSource") DataSource clusterDataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ShoppingConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String dll;
    @Value("${spring.jpa.show-sql}")
    String showSql;

    private Map<String, Object> buildProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
        properties.put("hibernate.hbm2ddl.auto", dll);
        properties.put("hibernate.show_sql", showSql);
        return properties;
    }
}
