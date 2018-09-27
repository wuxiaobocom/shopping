package com.bobo.shopping.manage.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: wuxiaobo
 * @Description:
 * @Date: 2019/9/27
 */
public class BeanUtil {

    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * bean对象转化成  Map<String, Object>
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T t) {
        if (t == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = t.getClass().getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                map.put(field.getName(), field.get(t));
            }
        } catch (IllegalAccessException e) {
            logger.error("beanToMap error  t:{} ", t, e);
        }
        return map;
    }

    /**
     * bean对象转化成  Map<String, String>
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Map<String, String> beanToMapStr(T t) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (t == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        Field[] declaredFields = t.getClass().getDeclaredFields();
        String fieldValue = null;
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                if (field.get(t) == null) {
                    fieldValue = "";
                } else {
                    if ("java.util.Date".equals(field.getType().getName())) {
                        fieldValue = sdf.format((Date) field.get(t));
                    } else {
                        fieldValue = field.get(t).toString();
                    }
                }
                map.put(field.getName(), fieldValue);
            }
        } catch (IllegalAccessException e) {
            logger.error("beanToMapStr error t:{} ", t, e);
        }
        return map;
    }

    /**
     * bean对象集合转化成 List<Map<String, Object>>
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> beanToMapList(List<T> list) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (list.isEmpty()) {
            return  new ArrayList<>();
        }
        if (list != null && list.size() > 0){
            for (T t : list) {
                mapList.add(beanToMap(t));
            }
        }
        return mapList;
    }

    /**
     * bean对象集合转化成 List<Map<String, String>>
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, String>> beanToMapStrList(List<T> list) {
        List<Map<String, String>> mapList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (T t : list) {
                mapList.add(beanToMapStr(t));
            }
        }
        return mapList;
    }

    /**
     * 获取实体集合的某个字段的集合
     * @param list 实体集合
     * @param fieldName 字段名
     * @param fieldClass 字段类型
     * @param <T> 泛型实体类型
     * @param <E> 泛型的字段类型
     * @return
     */
    public static <T,E> List<E> getFileldValueList(List<T> list,String fieldName,Class<E> fieldClass){
        List<E> fieldList = new ArrayList<>();
        try {
            if (list != null && list.size() > 0) {
                Field field = null;
                for (T t : list) {
                    E value = null;
                    if (t instanceof Map){
                        Map map = (Map) t;
                        value = (E) map.get(fieldName);
                    } else {
                        field = t.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        value = (E) field.get(t);
                    }
                    fieldList.add(value);

                }
            }
        } catch (Exception e) {
            logger.error("getFileldList error  list:{}, fieldName:{}, fieldClass:{}", list, fieldName, fieldClass, e);
        }
        return fieldList;
    }

    /**
     * 获取实体集合以实体的某个字段为key，该实体为value的Map
     * @param list  实体集合
     * @param fieldName 字段名
     * @param fieldClass 字段类型
     * @param <T> 泛型实体类型
     * @param <E> 泛型的字段类型
     * @return
     */
    public static <T, E> Map<E, T> getFileldBeanMap(List<T> list, String fieldName, Class<E> fieldClass) {
        Map<E, T> resultMap = new HashMap<>();
        try {
            if (list != null && list.size() > 0){
                Field field = null;
                for (T t : list) {
                    field = t.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    E fieldValue = (E) field.get(t);
                    resultMap.put(fieldValue, t);
                }
            }
        } catch (Exception e) {
            logger.error("getFileldBeanMap error  list:{}, fieldName:{}, fieldClass:{}", list, fieldName, fieldClass, e);
        }
        return resultMap;
    }

    /**
     * 获取实体集合的某个字段的set集合 除去非空
     * @param list 实体集合
     * @param fieldName 字段名
     * @param fieldClass 字段类型
     * @param <T> 泛型实体类型
     * @param <E> 泛型的字段类型
     * @return
     */
    public static <T,E> Set<E> getFileldValueSet(List<T> list,String fieldName,Class<E> fieldClass){
        Set<E> fieldSet = new HashSet<>();
        try {
            if (list != null && list.size() > 0){
                Field field = null;
                for (T t : list) {
                    field = t.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    E value = (E) field.get(t);
                    if (value != null){
                        fieldSet.add(value);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("getFileldList error  list:{}, fieldName:{}, fieldClass:{}", list, fieldName, fieldClass, e);
        }
        return fieldSet;
    }

    public static <T,E extends Comparable> T getMatchValueInList(List<T> list,String fieldName,E matchKey){
        try {
            if (list.isEmpty() || matchKey == null){
                return null;
            }
            Field field = null;
            for (T t : list) {
                field = t.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                E value = (E) field.get(t);
                if (value != null && value.compareTo(matchKey) == 0){
                    return t;
                }
            }
        } catch (Exception e) {
            logger.error("getMatchValueInList error  list:{}, fieldName:{}, matchKey:{}", list, fieldName, matchKey, e);
        }
        return null;
    }

    /**
     * 数字型的集合数据转换类型
     * @param list 待转换的数字集合，(可以为String,Long,Integer,Short,Byte)
     * @param targetClass 目标类型 (可以为String,Long,Integer,Short,Byte)
     * @param <E>
     * @param <T>
     * @return
     */
    public static <E,T> List<E> convertNumberList(List<T> list,Class<E> targetClass){
        List<E> targetList = new ArrayList<>();
        if (list != null && list.size() > 0){
            for (T t : list){
                E e = convertNumber(t,targetClass);
                targetList.add(e);
            }
        }
        return targetList;
    }

    /**
     * 数字型的数据转换类型
     * @param t 待转换的数字，(可以为String,Long,Integer,Short,Byte)
     * @param targetClass 目标类型 (可以为String,Long,Integer,Short,Byte)
     * @param <E>
     * @param <T>
     * @return
     */
    public static <E, T> E convertNumber(T t, Class<E> targetClass) {
        E e = null;
        try{
            if (t != null) {
                String className = targetClass.getSimpleName();
                String value = t.toString();
                if (String.class.getSimpleName().equals(className)) {
                    e = (E) value;
                } else if (Long.class.getSimpleName().equals(className)) {
                    e = (E) Long.valueOf(value);
                } else if (Integer.class.getSimpleName().equals(className)) {
                    e = (E) Integer.valueOf(value);
                } else if (Short.class.getSimpleName().equals(className)) {
                    e = (E) Short.valueOf(value);
                } else if (Byte.class.getSimpleName().equals(className)) {
                    e = (E) Byte.valueOf(value);
                }
            }
        }catch (Exception ex){
            logger.error("convertNumber error  t:{}, targetClass:{}", t, targetClass, ex);
        }
        return e;
    }
}
