package com.bobo.shopping.manage.dao.shopping.mapper;


import com.bobo.shopping.manage.dao.shopping.bean.UserUser;

import java.util.List;
import java.util.Map;

public interface UserUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserUser record);

    int insertSelective(UserUser record);

    UserUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserUser record);

    int updateByPrimaryKey(UserUser record);

    /**
     * useruser列表查询
     * @param paramMap
     * @return
     */
    List<UserUser> getUserUserByPage(Map<String, Object> paramMap);

    /**
     * 条件查询useruser表的大小
     * @param paramMap
     * @return
     */
    Integer getUserUserCount(Map<String, Object> paramMap);
}