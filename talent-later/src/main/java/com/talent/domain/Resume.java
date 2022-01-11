package com.talent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 个人简历
 * @author: luffy
 * @time: 2021/7/20 下午 02:22
 */
@Data
@ApiModel(value = "Resume",description = "简历类")
public class Resume implements Serializable {

    /**
     * 简历id
     **/
   @ApiModelProperty("简历id")
    private Integer reid;
    /**
     * 用户id
     **/
    @ApiModelProperty("简历对应用户id")
    private Integer uid;
    /**
     * 用户姓名
     **/
    @ApiModelProperty("用户姓名")
    private String name;
    /**
     * 籍贯
     **/
    @ApiModelProperty("籍贯")
    private String origin;
    /**
     * 政治面貌
     **/
    @ApiModelProperty("政治面貌")
    private String politicalLandscape;
    /**
     * 毕业时间
     **/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "毕业时间")
    private Date graduationTime;
    /**
     * 求职地点
     **/
    @ApiModelProperty("求职地点")
    private String workplace;
    /**
     * 职业
     **/
    @ApiModelProperty("职业")
    private String occupation;
    /**
     * 工作年限
     **/
    @ApiModelProperty("工作年限")
    private Integer workTime;
    /**
     * 教育经历
     **/
    @ApiModelProperty("教育经历")
    private String educationalExperience;
    /**
     * 实习经历
     **/
    @ApiModelProperty("实习经历")
    private String internshipExperience;
    /**
     * 获奖情况
     **/
    @ApiModelProperty("获奖情况")
    private String award;
    /**
     * 技能证书
     **/
    @ApiModelProperty("技能证书")
    private String skillCertificate;
    /**
     * 职业技能或特长
     **/
    @ApiModelProperty("职业技能或特长")
    private String professionalSkills;
    /**
     * 自我描述、评价
     **/
    @ApiModelProperty("自我描述")
    private String selfEvaluation;
    /**
     * 第三方推荐
     **/
    @ApiModelProperty("第三方推荐")
    private String recommend;
    /**
     * 简历封面？其他图片
     **/
    @ApiModelProperty("简历封面")
    private byte[] pic;
}
