package com.bobo.shopping.manage.controller.shopping;

import com.bobo.shopping.manage.controller.BaseController;
import com.bobo.shopping.manage.dao.shopping.bean.Test;
import com.bobo.shopping.manage.service.shopping.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * test表
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 20:01
 **/
@Controller
@RequestMapping(value = "test")
public class TestController extends BaseController {
    @Autowired
    private TestService testService;

    /**
     * 通过Id拿到Test表中的信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getTestInfo",method = RequestMethod.GET)
    @ResponseBody
    public Test getTestInfo(Integer id) {
        return testService.getTestInfo(id);
    }

    /**
     * 去test表的列表页面
     * @return
     */
    @GetMapping(value = "/toTestListPage")
    public String toTestListPage() {
        return "/pages/member/list";
    }
}
