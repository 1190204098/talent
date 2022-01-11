package com.talent.controller.utils;

import lombok.Getter;

/**
 * 状态码
 * @author luffy
 * @date 下午 02:43 2021/12/4
 **/
@Getter
public enum CodeEnum{

    SUCCESS(200,"成功"),
    FAILURE(400,"失败"),
    NO_AUTH(403,"权限不足"),
    NO_PAGE(404,"页面不存在"),
    ERROR(500,"服务器故障");

    private Integer code;
    private String desc;

    CodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CodeEnum getEnum(Integer code){
        for (CodeEnum codeEnum : CodeEnum.values()) {
            if(codeEnum.getCode().equals(code)){
                return codeEnum;
            }
        }
        return null;
    }
}
