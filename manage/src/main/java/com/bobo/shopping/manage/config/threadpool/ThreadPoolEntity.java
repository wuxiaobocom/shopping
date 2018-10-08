package com.bobo.shopping.manage.config.threadpool;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义线程池对应的属性
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-30 10:50
 **/
@Component
@ConfigurationProperties(prefix = "com.bobo.threadpool")
@Getter
@Setter
public class ThreadPoolEntity {

    private String corePoolSize;
    private String maxPoolSize;
    private String keepAliveTime;
    private String unit;
    private String queue;
    private String threadFactory;
    private String queueCapacity;
    private String rejectHandler;
}
