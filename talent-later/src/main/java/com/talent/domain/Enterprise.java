package com.talent.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业类
 * @author jmj
 * @TableName enterprise
 */
@Data
@ApiModel(value = "Enterprise",description = "企业类")
public class Enterprise implements Serializable {

    private Integer eid;

    private Integer uid;

    private String ename;

    private String address;

    private String tel;

    private String email;

    /**
     * 企业描述
     */
    private String intro;

    /**
     * 是否认证，1为通过
     */
    private Integer status;

}