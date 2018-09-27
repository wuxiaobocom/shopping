package com.bobo.shopping.manage.dao;

import lombok.Data;

/**
 * 传递页面上的两个参数
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-27 14:56
 **/
@Data
public class BaseBean {
    /**
     * 页面上传来过的第几页
     */
    private Integer page;

    /**
     * 页面上传过来的页面大小
     */
    private Integer limit;
}
