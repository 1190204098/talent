package com.talent.controller.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 结果集
 * @author: luffy
 * @time: 2021/12/4 下午 02:09
 */
@Data
@ApiModel(value = "返回类",description = "Result")
public class Result<T> {

    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("返回数据")
    private T data;
    @ApiModelProperty("消息")
    private String msg;

    public Result(){}

    public Result(Integer code){
        this.code = code;
    }

    /**
     * 成功时默认返回true
     * @author luffy
     * @date 下午 02:23 2021/12/4
     * @param data 数据
     **/
    public Result(T data){
        this.code = CodeEnum.SUCCESS.getCode();
        this.data = data;
    }

    public Result(CodeEnum codeEnum,String msg){
        this.code = codeEnum.getCode();
        this.data = null;
        this.msg = msg;
    }

    public Result(CodeEnum codeEnum,T data,String msg){
        this.code = codeEnum.getCode();
        this.data = data;
        this.msg = msg;
    }
}
