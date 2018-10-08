package com.bobo.shopping.manage.common;

/**
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-30 18:18
 **/
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 说明：配置到spring配置文件,可以通过此工具类,获取到spring bean.在一些线程和非spring管控的类型中,很方便
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    /**
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
     */
    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringUtils.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }


    public static <T> T getBean(Class<T> clazz){
        try {
            return getContext().getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getBean(String beanId){
        try {   
            return   getContext().getBean(beanId);
        } catch (Exception e) {
            return null;
        }
    }
    public static <T> T getBean(String beanId , Class<T> clazz){
        try {
            return getContext().getBean(beanId , clazz);
        } catch (Exception e) {
            return null;
        }
    }
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz){
        try {
            return getContext().getBeansOfType(clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
