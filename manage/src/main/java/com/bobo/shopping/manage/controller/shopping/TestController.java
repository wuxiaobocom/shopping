package com.bobo.shopping.manage.controller.shopping;

import com.bobo.shopping.manage.common.ResultInfo;
import com.bobo.shopping.manage.controller.BaseController;
import com.bobo.shopping.manage.dao.shopping.bean.Test;
import com.bobo.shopping.manage.service.shopping.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * test表
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 20:01
 **/
@Controller
@Slf4j
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
        return "testListPage";
    }

    @GetMapping(value = "/testListData",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ResultInfo getTestListData (@RequestParam Integer page, @RequestParam Integer limit,
                                       @RequestParam (required = false) String name){
       Map<String,Object> map = testService.initParams(page,limit,name);
       Integer count =  testService.getCount(map);
       List<Map<String,Object>> rows = testService.getListByPage(map);
       ResultInfo resultInfo = ResultInfo.success(count, rows);
       return resultInfo;
    }
}
