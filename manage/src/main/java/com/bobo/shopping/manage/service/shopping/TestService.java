package com.bobo.shopping.manage.service.shopping;

import com.alibaba.fastjson.JSONObject;
import com.bobo.shopping.manage.dao.shopping.bean.Test;
import com.bobo.shopping.manage.dao.shopping.mapper.TestMapper;
import com.bobo.shopping.manage.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * test表
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 19:51
 **/
@Service
@Slf4j
public class TestService extends BaseService {

    @Autowired
    private TestMapper testMapper;

    @Transactional(transactionManager = "shoppingTransactionManager",rollbackFor = Exception.class)
    public Test getTestInfo(Integer id) {
        Test test = testMapper.selectByPrimaryKey(id);
        log.info("id为{}查询到的实体类是{}",id, JSONObject.toJSON(test));
        return test;
    }
}
