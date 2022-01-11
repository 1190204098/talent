package com.talent.realm;

import com.talent.service.back.IAdminService;
import com.talent.domain.Admin;
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
 * @author luffy
 * @date 下午 09:14 2021/6/30
 **/
@Slf4j
@Component
public class AdminRealm extends AuthorizingRealm {

    @Resource
    private IAdminService adminService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("doGetAuthenticationInfo() called with parameters => [authenticationToken = {}]",authenticationToken);
        UserToken userToken = (UserToken)authenticationToken;
        // 取得用户名
        String username = (String) userToken.getPrincipal();
        try {
            Admin admin = this.adminService.updateAndFindAdminByaName(username);
            // 取得登录密码
            String password = (new String((char[]) userToken.getCredentials()));
            if (admin.getPassword().equals(password)) {
                AuthenticationInfo auth = new SimpleAuthenticationInfo(username, password, "adminRealm");
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
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        Map<String, Object> map = null;
        try {
            map = this.adminService.findAuthByAdmin(username);
            log.info(String.valueOf(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        auth.setRoles((Set<String>) map.get("allRoles"));
        auth.setStringPermissions((Set<String>) map.get("allActions"));
        return auth;
    }
}
