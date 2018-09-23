package com.bobo.shopping.manage.controller.cms;

import com.bobo.shopping.manage.service.cms.CmsTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 20:19
 **/
@Slf4j
@Controller
@RequestMapping(value = "cmsTest")
public class CmsTestController {

    @Autowired
    private CmsTestService cmsTestService;


}
