package com.bobo.shopping.manage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录，登出，首页功能
 *
 * @authorwuxiaobo@didachuxing.com
 * @create 2018-09-23 21:14
 **/
@Controller
@Slf4j
public class LoginController {

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    /**
     * 跳转到首页
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexPage() {
        return "index";
    }

    @GetMapping(value = "/welcome")
    public String toWelcomePage () {
        return "welcome";
    }
}