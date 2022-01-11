package com.talent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author jmj
 * @since 2021/12/9 20:29
 */
@Data
@ApiModel("管理员信息及其权限数据模型")
public class AdminDTO implements Serializable {

    @ApiModelProperty("账号")
    private String aName;
    @ApiModelProperty("时间")
    private Date date;
    @ApiModelProperty("是否被锁定")
    private Integer locked;
    @ApiModelProperty("角色")
    private List<String> role;
}
