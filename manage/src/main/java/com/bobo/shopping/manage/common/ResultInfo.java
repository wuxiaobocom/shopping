package com.bobo.shopping.manage.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表返回值
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-25 15:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo {
    private Integer code;

    private Integer total;

    private List<?> data;

    private String message;

    public static ResultInfo success(Integer total, List<?> rows) {
        return new ResultInfo(responseCode.SUCCESS,total,rows,"");
    }

    public static ResultInfo fail () {
        return new ResultInfo(responseCode.FIAL,0,new ArrayList<>(),"");
    }

    static class responseCode {
        /**
         * 表示返回成功
         */
       public static final int SUCCESS = 0;
        /**
         * 表示返回失败
         */
       public static final int FIAL = 1;
    }
}
