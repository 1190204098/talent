package com.talent.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 用户实体类
 * @author: luffy
 * @time: 2021/7/10 下午 01:08
 */
@Data
@ApiModel(value = "User",description = "用户类")
public class User implements Serializable {

    @ApiModelProperty("用户id")
    private Integer uid;
    @ApiModelProperty("用户名")
    private String uName;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("用户登录时间")
    private Date startDate;
    @ApiModelProperty("用户登出时间")
    private Date lastDate;
    @ApiModelProperty("用户手机号")
    private String tel;
    @ApiModelProperty("用户是否被锁定")
    private Integer locked;
    @ApiModelProperty("用户标识, 1:企业账号")
    private Integer flag;
}
