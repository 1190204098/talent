package com.talent.controller.Advice;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 * @author: luffy
 * @time: 2021/12/4 下午 02:09
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(AuthorizationException.class)
    public Result accessDeniedExceptionHandler(AuthorizationException e){
        log.error("错误 - {}:{}, cause:{}",e.toString(),e.getMessage(),e.getCause());
        String msg = "权限不足";
        return new Result(CodeEnum.NO_AUTH,msg);
    }

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        log.error("错误 - {}:{}, cause:{}",e.toString(),e.getMessage(),e.getCause());
        e.printStackTrace();
        String msg = "服务器在开小差，去找管理员帮忙";
        return new Result(CodeEnum.FAILURE,msg);
    }
}
