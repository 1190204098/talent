package com.talent.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 管理员实体类
 * @author: luffy
 * @time: 2021/6/30 下午 01:46
 */
@Data
@ApiModel(value = "Admin",description = "管理员类")
public class Admin implements Serializable {

    @ApiModelProperty("管理员id")
    private Integer aid;
    @ApiModelProperty("管理员用户名")
    private String aName;
    @ApiModelProperty("管理员密码")
    private String password;
    @ApiModelProperty("无用")
    private Integer privilege;
    @ApiModelProperty("最后一次登录日期")
    private Date date;
    @ApiModelProperty("是否被锁定")
    private Integer locked;
}
