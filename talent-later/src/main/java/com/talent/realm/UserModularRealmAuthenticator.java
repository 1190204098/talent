package com.talent.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @description: 自定义ModularRealmAuthenticator
 * @author: luffy
 * @time: 2021/7/10 下午 05:24
 */
@Slf4j
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 自定义realm跳转
     * @author luffy
     * @date 下午 02:31 2021/7/11
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)throws AuthenticationException {
        log.info("doAuthenticate() called with parameters => [authenticationToken = {}]",authenticationToken);
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的Token
        UserToken userToken = (UserToken) authenticationToken;
        // 登录类型
        String loginType = userToken.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            // System.out.println(realm.getName());
            if (realm.getName().contains(loginType)){
                typeRealms.add(realm);
            }
        }
        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            log.info("doSingleRealmAuthentication() execute ");
            return doSingleRealmAuthentication(((ArrayList<Realm>) typeRealms).get(0), userToken);
        }
        else{
            log.info("doMultiRealmAuthentication() execute ");
            return doMultiRealmAuthentication(typeRealms, userToken);
        }
    }
}
