package com.bobo.shopping.manage.service.shopping;

import com.alibaba.fastjson.JSONObject;
import com.bobo.shopping.manage.common.CheckNotNull;
import com.bobo.shopping.manage.common.JsonUtil;
import com.bobo.shopping.manage.dao.shopping.bean.Test;
import com.bobo.shopping.manage.dao.shopping.mapper.TestMapper;
import com.bobo.shopping.manage.service.BaseService;;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * test表
 *
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-23 19:51
 **/
@Service
@Slf4j
@Transactional(transactionManager = "shoppingTransactionManager",rollbackFor = Exception.class)
public class TestService extends BaseService {

    @Autowired
    private TestMapper testMapper;

    public Test getTestInfo(Integer id) {
        Test test = testMapper.selectByPrimaryKey(id);
        log.info("id为{}查询到的实体类是{}",id, JSONObject.toJSON(test));
        return test;
    }

    public Integer getCount(Map map) {
        return testMapper.getCount(map);
    }

    public List<Map<String, Object>> getListByPage(Map<String,Object> paramMap) {
        List<Test> testList = testMapper.getListByPage(paramMap);
        if (testList.isEmpty()) {
            return  new ArrayList<Map<String, Object>>();
        }
        List<Map<String,Object>> list = new ArrayList<>();
        testList.iterator().forEachRemaining(test -> {
            Map<String,Object> map = new HashMap<>();
            Integer id = test.getId();
            map.put("id",id);
            String name = test.getName();
            map.put("name",name);
            Integer age = test.getAge();
            map.put("age",age);
            list.add(map);
        });
        return list;
    }

    public Map<String, Object> initParams(Integer page, Integer limit, String name) {
        Map<String,Object> map = new HashMap<>(4);
        if (StringUtils.isNotBlank(name)) {
            map.put("name","%"+name+"%");
        }
        if (page != null) {
            map.put("page", page-1);
        } else {
            map.put("page",0);
        }
        if (limit != null) {
            map.put("limit",limit);
        } else {
            map.put("limit",10);
        }
        log.info("TestService initParams result :{}", JsonUtil.ObjectToJson(map));
        return map;
    }
}
