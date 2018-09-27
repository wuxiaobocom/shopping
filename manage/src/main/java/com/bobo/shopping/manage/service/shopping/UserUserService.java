package com.bobo.shopping.manage.service.shopping;

import com.bobo.shopping.manage.common.BeanUtil;
import com.bobo.shopping.manage.common.CheckNotNull;
import com.bobo.shopping.manage.dao.shopping.bean.UserUser;
import com.bobo.shopping.manage.dao.shopping.mapper.UserUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 用户功能
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-27 14:55
 **/
@Service
@Slf4j
@Transactional(transactionManager = "shoppingTransactionManager",propagation = Propagation.REQUIRES_NEW,
                rollbackFor =Exception.class)
public class UserUserService {

    @Autowired
    private UserUserMapper  userUserMapper;

    public Map<String, Object> initParams(Integer page, Integer limit, String name,
                                          String phone) {
        Map<String,Object> map = new HashMap<>(4);
        map.put("page", CheckNotNull.getPage(page));
        map.put("limit",CheckNotNull.getLimit(limit));
        if (StringUtils.isNotBlank(name)) {
            name = "%"+name+"%";
            map.put("name",name);
        }
        map.put("phone",CheckNotNull.getString(phone));
        return  map;
    }

    public List<Map<String, Object>> getUserDataByPage(Map<String, Object> paramMap)
            throws Exception{

        List<UserUser> userUserList  = new LinkedList<>();
        userUserList= userUserMapper.getUserUserByPage(paramMap);
        if (userUserList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = BeanUtil.beanToMapList(userUserList);
        return list;
    }

    public Integer getUserCount(Map<String, Object> paramMap) throws Exception {
        return  userUserMapper.getUserUserCount(paramMap);
    }
}
