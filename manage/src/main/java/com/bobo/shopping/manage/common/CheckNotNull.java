package com.bobo.shopping.manage.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * 判断各种类型是否为空
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-25 19:07
 **/
@Slf4j
public class CheckNotNull {

    public static String getString(Object obj){
        if(obj == null){
            return "";
        }else{
            return obj.toString();
        }
    }

    public static String getStringZero(Object obj){
        if(obj == null){
            return "0";
        }else{
            if (StringUtils.isBlank(obj.toString())){
                return "0";
            }else {
                return obj.toString();
            }
        }
    }

    public static BigDecimal checkBigDecimal(BigDecimal obj){
        if(obj == null){
            return new BigDecimal("0");
        }else{
            return obj;
        }
    }

    public static boolean checkIsEmpty(Object... obj){
        for(Object o : obj){
            if(isEmpty(o)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串，集合，数组，map等常见对象是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {

        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            return StringUtils.isBlank(str);
        }

        if (obj instanceof Collection) {
            Collection<?> col = (Collection<?>) obj;
            return col.isEmpty();
        }

        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            return map.isEmpty();
        }
        return false;
    }


    public static <T> T checkIsEmptyReturnNew(T obj,Class<T> cls){
        try {
            if (isEmpty(obj)){
                obj = cls.newInstance();
            }
        }catch (Exception e){
            log.error("CheckNotNull checkIsEmptyReturnNew is error,obj:{},cls:{}",obj,cls,e);
        }
        return obj;
    }


    public static Integer getIntegerFromObj(Object object){
        if(object instanceof Integer){
            return (Integer)object;
        }
        try {
            return Integer.parseInt(getString(object));
        }catch (Exception e){
            log.error("CheckNotNull getIntegerFromObj is error :param is not String or Integer ,obj:{},and return null",object);
        }
        return null;
    }
}
