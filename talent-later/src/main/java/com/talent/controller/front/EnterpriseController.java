package com.talent.controller.front;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.Result;
import com.talent.domain.Enterprise;
import com.talent.service.front.IEnterpriseService;
import com.talent.service.front.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 企业相关操作
 * @author: luffy
 * @time: 2021/12/13 下午 11:34
 */
@Slf4j
@RestController
@Api(tags = "企业相关接口")
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Resource
    private IEnterpriseService enterpriseService;
    @Resource
    private IUserService userService;

    /**
     * 新增企业,不需要用户id
     * @param enterprise
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 11:53 2021/12/13
     **/
    @ApiOperation(value = "企业认证申请",notes = "参数: `企业名`、`企业地址`、`企业电话`、`企业邮箱`、`企业介绍`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ename",value = "企业名",required = true,paramType = "body",dataTypeClass = String.class),
            @ApiImplicitParam(name = "address",value = "企业地址",required = true,paramType = "body",dataTypeClass = String.class),
            @ApiImplicitParam(name = "tel",value = "企业电话",required = true,paramType = "body",dataTypeClass = String.class),
            @ApiImplicitParam(name = "email",value = "企业邮箱",required = true,paramType = "body",dataTypeClass = String.class),
            @ApiImplicitParam(name = "intro",value = "企业介绍",required = true,paramType = "body",dataTypeClass = String.class)
    })
    @RequiresRoles("user")
    @PostMapping("/addEnterprise")
    public Result addEnterprise(@RequestBody Enterprise enterprise) {
        log.info("addEnterprise() called with parameters => [enterprise = {}]", enterprise);
        if (enterpriseService.findEnterpriseByEname(enterprise.getEname())!=null) {
            log.info("企业名[{}]已存在", enterprise.getEname());
            return new Result(CodeEnum.FAILURE, "企业名已存在");
        }
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        enterprise.setUid(userService.findUserByName(uName).getUid());
        enterpriseService.addEnterprise(enterprise);
        log.info("企业[{}]增加成功", enterprise.getEname());
        return new Result(CodeEnum.SUCCESS, "新增企业成功");
    }

    /**
     * 获取企业信息
     * @author luffy
     * @date 上午 12:26 2021/12/14
     * @return com.talent.controller.utils.Result<java.util.List<com.talent.domain.Enterprise>>
     **/
    @ApiOperation(value = "企业列表",notes = "无参")
    @RequiresRoles("enterprise")
    @GetMapping("/getEnterprise")
    public Result<List<Enterprise>> getEnterprise() {
        log.info("getEnterprise() called with no parameters");
        String uName = ((String) SecurityUtils.getSubject().getPrincipal());
        List<Enterprise> enterprises = enterpriseService.findEnterpriseByUname(uName);
        if (enterprises.size() == 0) {
            log.info("用户[{}]没有企业", uName);
            return new Result(CodeEnum.FAILURE, "用户没有企业");
        }
        log.info("查询成功");
        return new Result<>(enterprises);
    }

    /**
     * 更新企业，需要企业角色
     * @param enterprise
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 上午 12:10 2021/12/14
     **/
    @ApiOperation(value = "企业信息修改",notes = "参数: `企业名`、`企业地址`、`企业电话`、`企业邮箱`、`企业介绍`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ename",value = "企业名",required = true,paramType = "body",dataTypeClass = String.class),
            @ApiImplicitParam(name = "address",value = "企业地址",required = true,paramType = "body",dataTypeClass = String.class),
            @ApiImplicitParam(name = "tel",value = "企业电话",required = true,paramType = "body",dataTypeClass = String.class),
            @ApiImplicitParam(name = "email",value = "企业邮箱",required = true,paramType = "body",dataTypeClass = String.class),
            @ApiImplicitParam(name = "intro",value = "企业介绍",required = true,paramType = "body",dataTypeClass = String.class)
    })
    @RequiresRoles("enterprise")
    @PostMapping("/updateEnterprise")
    public Result updateEnterprise(@RequestBody Enterprise enterprise) {
        log.info("updateEnterprise() called with parameters => [enterprise = {}]", enterprise);
        String uName = ((String) SecurityUtils.getSubject().getPrincipal());
        enterprise.setUid(userService.findUserByName(uName).getUid());
        enterpriseService.updateEnterprise(enterprise);
        log.info("企业[{}]信息更新成功", enterprise.getEname());
        return new Result(CodeEnum.SUCCESS, "企业信息更新成功");
    }
}
