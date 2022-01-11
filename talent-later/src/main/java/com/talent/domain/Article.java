package com.talent.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jmj
 * @date 2021/7/4 15:43
 */
@Data
@ApiModel(value = "Article",description = "文章类")
public class Article implements Serializable {
    @ApiModelProperty("文章id")
    private Integer artid;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("所属用户id")
    private Integer aid;
    @ApiModelProperty("所属用户名")
    private String aName;
    @ApiModelProperty("文章内容")
    private String content;
    @ApiModelProperty("发布日期时间")
    private Date date;
}
