package com.bobo.shopping.manage.config.entity.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件中关于shopping数据库配置
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 17:15
 **/
@ConfigurationProperties(prefix = "spring.datasource.shopping")
@Component
@Getter
@Setter
public class ShoppingDataSourceProperties {
    public String url;

    public String username;

    public String password;

    public String driverClassName;

    public Integer maxActive;

    public Integer initialSize;

    public Integer minIdle;

    public Integer maxWait;

    public Integer maxPoolPreparedStatementPerConnectionSize;

    public Integer timeBetweenEvictionRunsMillis;

    public Integer minEvictableIdleTimeMillis;

    public Boolean poolPreparedStatements;
}
