package com.bobo.shopping.manage.controller;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 基础控制器
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 19:55
 **/
@Getter
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

}
