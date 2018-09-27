package com.bobo.shopping.manage.dao.shopping.bean;

import com.bobo.shopping.manage.dao.BaseBean;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wuxiaobo@didachuxing.com
 */
@Setter
@Getter
public class UserUser extends BaseBean {
    private Integer id;

    private String cid;

    private String phone;

    private String nickName;

    private Byte gender;

    private String imgUrl;

    private Integer age;

    private String email;

    private Date createTime;

    private Integer status;

    private String lastName;

    private String firstName;
}