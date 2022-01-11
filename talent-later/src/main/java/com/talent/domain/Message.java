package com.talent.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * (Message)表实体类
 * @author jmj
 * @since 2021-12-15 23:36:31
 */
@Data
@ApiModel("消息类")
public class Message {

    @ApiModelProperty(value = "消息id",required = false,dataType = "Integer")
    private Integer mid;
    /**
     * 消息接收方用户id
     * @author jmj
     * @since 23:44 2021/12/15
     **/
    @ApiModelProperty(value = "接收方用户id",required = false,dataType = "Integer")
    private Integer uid;
    /**
     * 消息名称
     * @author jmj
     * @since 23:36 2021/12/15
     **/
    @ApiModelProperty(value = "消息标题",required = true,dataType = "String")
    private String mname;
    /**
     * 消息内容
     * @author jmj
     * @since 23:37 2021/12/15
     **/
    @ApiModelProperty(value = "消息内容",required = true,dataType = "String")
    private String context;
    /**
     * 消息时间
     * @author jmj
     * @since 23:37 2021/12/15
     **/
    @ApiModelProperty(value = "消息发送时间",required = false,dataType = "date")
    private Date time;
    /**
     * 消息是否已被查看标记
     * @author jmj
     * @since 23:49 2021/12/15
     **/
    @ApiModelProperty(value = "是否被查看标记",required = false,dataType = "Integer")
    private Integer flag;


}