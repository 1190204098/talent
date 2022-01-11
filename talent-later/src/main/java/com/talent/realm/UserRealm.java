package com.talent.realm;

import com.talent.domain.User;
import com.talent.service.front.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @description: 用户realm
 * @author: luffy
 * @time: 2021/7/10 下午 04:03
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private IUserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("doGetAuthenticationInfo() called with parameters => [authenticationToken = {}]",authenticationToken);
        UserToken userToken = (UserToken)authenticationToken;
        // 取得用户名
        String username = (String) userToken.getPrincipal();
        try {
            User user = userService.updateTimeAndFindUser(username);
            // 取得登录密码
            String password = (new String((char[]) userToken.getCredentials()));
            if (user.getPassword().equals(password)) {
                AuthenticationInfo auth = new SimpleAuthenticationInfo(username, password, "userRealm");
                log.info("[{}]登录成功",username);
                return auth;
            } else {
                throw new IncorrectCredentialsException("密码错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        return super.getAuthorizationInfo(principals);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("doGetAuthorizationInfo() called with parameters => [principalCollection = {}]",principalCollection);
        // 取得用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        Map<String, Object> map = null;
        try {
            map = this.userService.findAuthByName(username);
            log.info(String.valueOf(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        auth.setRoles((Set<String>) map.get("allRoles"));
        auth.setStringPermissions((Set<String>) map.get("allPermissions"));
        return auth;
    }
}
