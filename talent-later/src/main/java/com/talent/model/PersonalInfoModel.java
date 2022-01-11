package com.talent.model;

import com.talent.domain.PersonalInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电话和个人信息数据模型
 * @author jmj
 * @since 2021/12/8 0:18
 */
@Data
@ApiModel(value = "PersonalInfoModel",description = "用户个人信息数据模型")
public class PersonalInfoModel {
    @ApiModelProperty("手机号")
    private String tel;
    @ApiModelProperty(value = "个人信息")
    private PersonalInfo info;
}
