package com.talent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端显示的企业模型
 * @author: luffy
 * @time: 2021/12/10 下午 08:36
 */
@Data
@ApiModel("企业DTO类")
public class EnterpriseDTO {

    @ApiModelProperty(value = "企业所属用户名",required = false)
    private String uname;
    @ApiModelProperty(value = "企业名称",required = true)
    private String ename;
    @ApiModelProperty(value = "企业地址",required = true)
    private String address;
    @ApiModelProperty(value = "企业电话",required = true)
    private String tel;
    @ApiModelProperty(value = "企业邮箱",required = true)
    private String email;
    @ApiModelProperty(value = "企业介绍",required = true)
    private String intro;
}

