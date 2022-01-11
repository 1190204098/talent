package com.talent.realm;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @description: 自定义Token
 * @author: luffy
 * @time: 2021/7/10 下午 05:22
 */
@Getter
@Setter
public class UserToken extends UsernamePasswordToken {

    private String loginType;

    public UserToken(final String username, final String password,String loginType) {
        super(username,password);
        this.loginType = loginType;
    }
}
