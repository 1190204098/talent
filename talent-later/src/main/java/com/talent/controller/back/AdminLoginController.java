package com.talent.controller.back;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.Result;
import com.talent.domain.Admin;
import com.talent.realm.AdminRealm;
import com.talent.realm.UserToken;
import com.talent.service.back.IAdminService;
import com.talent.utils.MD5Code;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 管理员登录验证
 * @author: luffy
 * @time: 2021/6/30 下午 02:09
 */
@Slf4j
@RestController
@Api(tags = "管理员登录登出")
@RequestMapping(value = "/back/admin")
public class AdminLoginController{

    @Resource
    private IAdminService adminService;
    @Resource
    private AdminRealm adminRealm;

    @ApiOperation(value = "管理员登录",notes = "传入`管理员名称`、`密码`, 注意:<em>管理员名称使用aname,而不是aName</em>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aName",value = "管理员名称",required = true,paramType = "query",dataTypeClass = String.class),
            @ApiImplicitParam(name = "password",value = "密码",required = true,paramType = "query",dataTypeClass = String.class),
    })
    @RequiresGuest
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        String aName = admin.getAName();
        log.info("login() called with parameters => [user = {}]",aName);
        String md5Password = new MD5Code().getMD5ofStr("{{[[" + admin.getPassword() + "]]}}");
        Subject currentUser = SecurityUtils.getSubject();
        try {
            if (adminService.findAdminByaName(aName) == null) {
                log.info("请求登录账号[{}]不存在",aName);
                return new Result<>(CodeEnum.FAILURE,"账号不存在");
            }
            if(adminService.findAdminByaName(aName).getLocked()!=null&&adminService.findAdminByaName(aName).getLocked()==1){
                log.info("登录失败，[{}]账户被锁定",aName);
                return new Result<>(CodeEnum.FAILURE,"账号被锁定");
            }
            if (!currentUser.isAuthenticated()) {
                log.info("进行账户登录授权认证");
                UserToken token = new UserToken(aName, md5Password, AdminRealm.class.getName());
                token.setRememberMe(true);
                try {
                    currentUser.login(token);
                    Collection<String> permissions = adminRealm.getAuthorizationInfo(SecurityUtils.getSubject().getPrincipals()).getStringPermissions();
                    log.info("登录成功 => 当前用户[{}], 权限为[{}]", aName,permissions);
                    return new Result<>(CodeEnum.SUCCESS,permissions,"登录成功");
                } catch (AuthenticationException ae) {
                    log.error("登录失败: " + ae.getMessage());
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return new Result<>(CodeEnum.ERROR,"服务器异常，请联系管理员");
        }
        return new Result<>(CodeEnum.FAILURE,"登录失败");
    }

    @RequiresRoles("normaladmin")
    @PostMapping("/logout")
    @ApiOperation(value = "管理员登出",notes = "调用接口即可登出")
    public Result logOut() {
        log.info("logOut() called with no parameters");
        Subject subject = SecurityUtils.getSubject();
        String aName = (String) subject.getPrincipal();
        subject.logout();
        log.info("[{}]退出成功",aName);
        return new Result<>(CodeEnum.SUCCESS,"退出成功");
    }
}
