package com.bobo.shopping.manage.dao.cms.mapper;


import com.bobo.shopping.manage.dao.cms.bean.CmsTest;

public interface CmsTestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsTest record);

    int insertSelective(CmsTest record);

    CmsTest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsTest record);

    int updateByPrimaryKey(CmsTest record);
}