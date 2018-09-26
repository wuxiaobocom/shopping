package com.bobo.shopping.manage.common;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 使用FastJson格式化各种数据
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-25 19:19
 **/
@Slf4j
public class JsonUtil {

    /**
     * 对象转json字符串
     * @param object
     * @return
     */
    public static String ObjectToJson(Object object) {
        Class<?> objectClass = object.getClass();
        // 如果obj是基本类型，那么都转化为string类型
        if (objectClass.isPrimitive()) {
            String str = String.valueOf(object);
            return JSONObject.toJSONString(str);
        } else {
            //如果不是基本类型的
            return JSONObject.toJSONString(object);
        }
    }

    public static <T> T jsonToObject (String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            try {
                T ts = JSONObject.parseObject(json,cls);
                return  ts;
            }catch (JSONException e) {
                log.error("json:{} translate to class :{} error",json,cls,
                        e);
                return null;
            }
        }
    }
}
