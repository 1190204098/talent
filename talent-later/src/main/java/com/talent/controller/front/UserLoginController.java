package com.talent.controller.front;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.Result;
import com.talent.domain.User;
import com.talent.realm.UserRealm;
import com.talent.realm.UserToken;
import com.talent.service.back.IAdminService;
import com.talent.service.front.IUserService;
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
import java.util.Collection;
import java.util.Date;

/**
 * @author: luffy
 * @time: 2021/7/10 下午 03:53
 */
@Slf4j
@Api(tags = "用户登录、登出、注册接口")
@RestController
@RequestMapping("/front/user")
public class UserLoginController {

    @Resource
    private IUserService userService;

    /**
     * 用于判定注册时用户账号不能与管理员账号重复
     * @date 下午 04:35 2021/7/11
     **/
    @Resource
    private IAdminService adminService;

    @Resource
    private UserRealm userRealm;

    @ApiOperation(value = "用户登录",notes = "传参: `用户名`、`密码`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uName",value = "用户名",required = true,paramType = "query",dataTypeClass = String.class),
            @ApiImplicitParam(name = "password",value = "密码",required = true,paramType = "query",dataTypeClass = String.class)
    })
    @RequiresGuest
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("login() called with parameters => [user = {}]]",user.getUName());
        String uName = user.getUName();
        String md5Password = new MD5Code().getMD5ofStr("{{[[" + user.getPassword() + "]]}}");
        Subject currentUser = SecurityUtils.getSubject();
        User user1 = userService.findUserByName(uName);
        if (user1 == null) {
            log.info("[{}]账号不存在",uName);
            return new Result<>(CodeEnum.FAILURE,"账号不存在");
        }
        if(user1.getLocked()!=null&& user1.getLocked()==1){
            log.info("登录失败，[{}]账号被锁定",uName);
            return new Result<>(CodeEnum.FAILURE,"账号被锁定");
        }
        if (!currentUser.isAuthenticated()) {
            log.info("进行账号登录授权认证");
            UserToken token = new UserToken(uName, md5Password, UserRealm.class.getName());
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                Collection<String> permissions = userRealm.getAuthorizationInfo(SecurityUtils.getSubject().getPrincipals()).getStringPermissions();
                log.info("登录成功 => 当前用户[{}], 权限为[{}]", uName,permissions);
                return new Result<>(CodeEnum.SUCCESS,permissions,"登录成功");
            } catch (AuthenticationException ae) {
                log.info("登录失败: " + ae.getMessage());
            }
        }
        return new Result<>(CodeEnum.FAILURE,"密码错误");
    }

    @ApiOperation(value = "用户登出",notes = "无参")
    @RequiresRoles("user")
    @PostMapping("/logout")
    public Result logOut(){
        log.info("logOut() called with no parameters");
        System.out.println(userRealm.getAuthorizationInfo(SecurityUtils.getSubject().getPrincipals()).getStringPermissions());
        Subject subject = SecurityUtils.getSubject();
        String uName = (String) subject.getPrincipal();
        subject.logout();
        log.info("[{}]退出成功",uName);
        return new Result<>(CodeEnum.SUCCESS,"退出成功");
    }

    /**
     * 注册账号
     * @author luffy
     * @date 下午 10:48 2021/12/5
     * @param user
     * @return com.talent.controller.utils.Result
     **/
    @ApiOperation(value = "用户注册",notes = "传参: `用户名`、`密码`、`手机号`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uName",value = "用户名",required = true,paramType = "query",dataTypeClass = String.class),
            @ApiImplicitParam(name = "password",value = "密码",required = true,paramType = "query",dataTypeClass = String.class),
            @ApiImplicitParam(name = "tel",value = "手机号",paramType = "query",dataType = "string",dataTypeClass = String.class)
    })
    @RequiresGuest
    @PostMapping("/signUp")
    public Result signUp(@RequestBody User user)throws Exception{
        log.info("signUp() called with parameters => [user]");
        if(userService.findUserByName(user.getUName())!=null||adminService.findAdminByaName(user.getUName())!=null){
            log.info("[{}]用户已存在",user.getUName());
            return new Result<>(CodeEnum.FAILURE,"用户已存在");
        }
        else{
            user.setPassword(new MD5Code().getMD5ofStr("{{[[" + user.getPassword() + "]]}}"));
            user.setStartDate(new Date(System.currentTimeMillis()));
            user.setLocked(0);
            userService.addUser(user);
            user = userService.findUserByName(user.getUName());
            //4表示普通用户
            userService.createUserRole(user.getUid(),4);
            log.info("[{}]注册成功",user.getUName());
            return new Result<>(CodeEnum.SUCCESS,"注册成功");
        }
    }
}
