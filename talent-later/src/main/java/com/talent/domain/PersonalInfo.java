package com.talent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 个人信息表
 * @author: luffy
 * @time: 2021/7/12 下午 01:10
 */
@Data
@ApiModel(value = "PersonalInfo",description = "用户个人信息类")
public class PersonalInfo implements Serializable {

    @ApiModelProperty("id")
   private Integer pid;
    /**
     * 用户编号
     **/
    @ApiModelProperty("用户id")
    private Integer uid;
    /**
     * 头像图片，二进制存入数据库
     **/
    @ApiModelProperty("头像图片")
    private byte[] pic;
    @ApiModelProperty("性别")
   private String sex;
    @ApiModelProperty("住址")
   private String address;
    @ApiModelProperty("邮箱")
   private String email;
    @ApiModelProperty("出生日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
   private Date birthday;
    /**
     * 专业
     **/
    @ApiModelProperty("专业")
    private String specialty;
    /**
     * 工资
     **/
    @ApiModelProperty("期望工资")
    private String wages;
    @ApiModelProperty("大学")
   private String college;
}
