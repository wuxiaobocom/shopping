package com.bobo.shopping.manage.controller.shopping;

import com.bobo.shopping.manage.common.JsonUtil;
import com.bobo.shopping.manage.common.ResultInfo;
import com.bobo.shopping.manage.controller.BaseController;
import com.bobo.shopping.manage.service.shopping.UserUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理相关
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-27 15:03
 **/
@Slf4j
@Controller
@RequestMapping(value = "user")
public class UserUserController extends BaseController {

    @Autowired
    private UserUserService userUserService;

    @GetMapping(value = "/toUserListPage")
    public ModelAndView toUserListPage() {
        return new ModelAndView("/user/userList");
    }

    @GetMapping(value = "/userListData",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ResultInfo getUserListData(@RequestParam Integer page, @RequestParam Integer limit,
                                      @RequestParam (required = false) String name,
                                      @RequestParam (required = false) String phone) {
        ResultInfo resultInfo = new ResultInfo();
        Map<String,Object> paramMap = new HashMap<>(4);
        try {
            paramMap = userUserService.initParams(page,limit,name,phone);
            List<Map<String,Object>> list = userUserService.getUserDataByPage(paramMap);
            Integer size = userUserService.getUserCount(paramMap);
            resultInfo.setCount(size);
            resultInfo.setCode(ResultInfo.responseCode.SUCCESS);
            resultInfo.setData(list);
        }catch (Exception e) {
            resultInfo = ResultInfo.fail();
            log.error("UserUserController getUserListData error, the param is :{}",
                    JsonUtil.ObjectToJson(paramMap));
        }
        return resultInfo;
    }
}
