package com.talent.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jmj
 * @since 2021/12/16 23:36
 */
@Data
@ApiModel(value = "ResumeSearchParamModel",description = "简历搜索参数模型类")
public class ResumeSearchParamModel{
    /**
     * 姓名
     **/
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 求职地点
     **/
    @ApiModelProperty(value = "求职地点")
    private String workplace;
    /**
     * 职业
     **/
    @ApiModelProperty(value = "职业")
    private String occupation;
    /**
     * 工作年限
     **/
    @ApiModelProperty(value = "工作年限",dataType = "Integer")
    private Integer workTime;

}
