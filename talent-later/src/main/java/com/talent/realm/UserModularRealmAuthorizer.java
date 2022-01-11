package com.talent.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * 自定义角色授权使用的realm
 * @author: luffy
 * @time: 2021/7/11 下午 02:35
 */
@Slf4j
public class UserModularRealmAuthorizer extends ModularRealmAuthorizer {

    /**
     * @author luffy
     * 暂未使用
     * @date 下午 04:25 2021/7/11
     * @param principals
     * @param permission
     * @return boolean
     **/
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)){ continue;}
            //  todo 授权配置
            if("adminRealm".equals(realmName)){
                if (realm instanceof AdminRealm) {
                    return ((AdminRealm) realm).isPermitted(principals, permission);
                }
            }
            if("userRealm".equals(realmName)) {
                if (realm instanceof UserRealm) {
                    return ((UserRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    /**
     * @author luffy
     * 暂未使用
     * @date 下午 04:25 2021/7/11
     * @param principals
     * @param permission
     * @return boolean
     **/
    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission){
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)){ continue;}
            //  todo 授权配置
            if("adminRealm".equals(realmName)) {
                if (realm instanceof AdminRealm) {
                    return ((AdminRealm) realm).isPermitted(principals, permission);
                }
            }
            if("userRealm".equals(realmName)) {
                if (realm instanceof UserRealm) {
                    return ((UserRealm) realm).isPermitted(principals, permission);
                }
            }

        }
        return false;
    }

    /**
     * 重写角色授权访问判断
     * @author luffy
     * @date 下午 04:25 2021/7/11
     * @param principals
     * @param roleIdentifier
     * @return boolean
     **/
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        log.info("hasRole() called with parameters => [principals = {}], [roleIdentifier = {}]",principals, roleIdentifier);
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) { continue; }
            if("adminRealm".equals(realmName)) {
                if (realm instanceof AdminRealm) {
                    return ((AdminRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
            if("userRealm".equals(realmName)) {
                if (realm instanceof UserRealm) {
                    return ((UserRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
        }
        return false;
    }
}
