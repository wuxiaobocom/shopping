package com.bobo.shopping.manage.dao.shopping.mapper;

import com.bobo.shopping.manage.dao.shopping.bean.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

    /**
     * 获取一共有多少条数据
     * @return
     * @param map
     */
    Integer getCount(Map<String,Object> map);

    /**
     * 分页查询数据
     * @param paramMap
     * @return
     */
    List<Test> getListByPage(Map<String,Object> paramMap);
}