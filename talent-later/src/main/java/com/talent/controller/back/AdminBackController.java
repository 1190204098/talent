package com.talent.controller.back;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.PicUtils;
import com.talent.controller.utils.Result;
import com.talent.controller.utils.WebUtils;
import com.talent.domain.Admin;
import com.talent.domain.Article;
import com.talent.domain.Page;
import com.talent.domain.User;
import com.talent.dto.AdminDTO;
import com.talent.dto.EnterpriseDTO;
import com.talent.service.back.IAdminService;
import com.talent.service.front.IArticleService;
import com.talent.service.front.IEnterpriseService;
import com.talent.service.front.IUserService;
import com.talent.utils.MD5Code;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 后台控制
 * @author: luffy
 * @time: 2021/7/1 下午 04:38
 */
@Slf4j
@RestController
@RequestMapping(value = "/back/controller")
@Api(tags = "管理员后台页面相关功能")
public class AdminBackController {

    @Resource
    private IAdminService adminService;

    @Resource
    private IArticleService articleService;

    @Resource
    private IUserService userService;

    @Resource
    private IEnterpriseService enterpriseService;

    /**
     * 管理员修改密码
     * @param password
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 10:47 2021/12/5
     **/
    @ApiOperation(value = "管理员修改密码", notes = "传入`新密码`即可, 旧密码前端验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "新密码", required = true, paramType = "body", dataTypeClass = String.class)
    })
    @RequiresRoles("normaladmin")
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody String password) {
        log.info("changePassword() called with parameters => [password]");
        String aName = (String) SecurityUtils.getSubject().getPrincipal();
        try {
            Admin admin = adminService.findAdminByaName(aName);
            String md5Password = new MD5Code().getMD5ofStr("{{[[" + password + "]]}}");
            admin.setPassword(md5Password);
            adminService.updateAdmin(aName, admin);
            log.info("[{}]修改密码成功", aName);
            return new Result<>(CodeEnum.SUCCESS, "修改成功");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        log.info("[{}]修改密码失败", aName);
        return new Result<>(CodeEnum.FAILURE, "修改失败");
    }

    /**
     * 超级管理员增加管理员
     * @param admin
     * @return com.talent.controller.utils.Result
     * @author jmj
     * @since 0:28 2021/12/14
     **/
    @ApiOperation(value = "添加管理员", notes = "传入`管理员名称`、`密码`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aname", value = "管理员名称", required = true, paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "body", dataTypeClass = String.class)
    })
    @RequiresRoles("admin")
    @PostMapping("/addAdmin")
    public Result addAdmin(@RequestBody Admin admin) {
        log.info("addAdmin() called with parameters => [admin]");
        try {
            if (adminService.findAdminByaName(admin.getAName()) != null) {
                return new Result<>(CodeEnum.FAILURE, "管理员账号名已存在");
            } else {
                Set<Integer> roles = new HashSet<>(3);
                roles.add(2);
                roles.add(3);
                roles.add(4);
                adminService.insert(admin, roles);
                log.info("增加管理员[{}]成功", admin.getAName());
                return new Result<>(CodeEnum.SUCCESS, "增加管理员成功");
            }
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
        log.info("增加管理员[{}]失败", admin.getAName());
        return new Result<>(CodeEnum.FAILURE, "增加管理员失败");
    }

    /**
     * 根据账户查找管理员，支持模糊查询
     * @param aName
     * @return com.talent.controller.utils.Result
     * @author jmj
     * @since 18:42 2021/12/5
     **/
    @ApiOperation(value = "模糊搜索管理员", notes = "传入`管理员名称`, 模糊搜索, 传参为`空字符串`则会搜索全部管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aName", value = "管理员名称", required = true, paramType = "query", dataTypeClass = String.class)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK, 如果data为null则表示查询无结果", response = Admin.class)
    })
    @RequiresRoles("admin")
    @GetMapping("/searchAdmin/{aName}/{pageNo}/{pageSize}")
    public Result<Page<AdminDTO>> searchAdmin(@PathVariable String aName, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        log.info("searchAdmin() called with parameters => [aName = {}], [pageNo = {}], [pageSize = {}]", aName, pageNo, pageSize);
        Page<AdminDTO> admins = adminService.findAdminLikeaName(aName, pageNo, pageSize);
        if (admins != null && !admins.getRecords().isEmpty()) {
            return new Result<>(admins);
        } else {
            log.info("没有搜索到管理员");
            return new Result<>(CodeEnum.SUCCESS, "没有查到结果");
        }
    }

    /**
     * 锁定账户或解锁账户
     * @param aName
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 03:46 2021/7/2
     **/
    @ApiOperation(value = "锁定、解锁管理员", notes = "传入`管理员名称`即可锁定、解锁")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aName", value = "管理员名称", required = true, paramType = "query", dataTypeClass = String.class)
    })
    @RequiresRoles("admin")
    @PostMapping("/lockOrUnlockAdmin/{aName}")
    public Result lockOrUnlockAdmin(@PathVariable String aName) {
        log.info("lockOrUnlockAdmin() called with parameters => [aName = {}]", aName);
        try {
            Admin admin = adminService.findAdminByaName(aName);
            if (admin == null) {
                log.info("用户[{}]不存在", aName);
                return new Result<>(CodeEnum.FAILURE, "用户不存在");
            }
            if (admin.getLocked() == 1) {
                admin.setLocked(0);
                adminService.updateAdmin(aName, admin);
                log.info("[{}]被解锁", aName);
                return new Result<>(CodeEnum.SUCCESS, "解锁成功");
            } else if (admin.getLocked() == 0) {
                admin.setLocked(1);
                adminService.updateAdmin(aName, admin);
                log.info("[{}]被锁定", aName);
                return new Result<>(CodeEnum.SUCCESS, "锁定成功");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        log.info("未知错误");
        return new Result<>(CodeEnum.ERROR, "未知错误");
    }

    /**
     * 发布新闻
     * @param article
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 03:11 2021/12/12
     **/
    @ApiOperation(value = "添加首页文章", notes = "传入`文章内容`及`标题`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "文章内容", required = true, paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "title", value = "文章标题", required = true, paramType = "query", dataTypeClass = String.class),
    })
    @RequiresRoles("normaladmin")
    @PostMapping("/addNews")
    public Result addNews(@RequestBody Article article) throws Exception {
        log.info("addNews() called with parameters => [article]");
        if (article != null) {
            String aName = (String) SecurityUtils.getSubject().getPrincipal();
            article.setAid(adminService.findAdminByaName(aName).getAid());
            article.setDate(new Date(System.currentTimeMillis()));
            articleService.addNews(article);
        }
        log.info("[{}]增加文章成功", SecurityUtils.getSubject().getPrincipal());
        return new Result<>(CodeEnum.SUCCESS, "增加文章成功");
    }

    /**
     * 管理员记录分页
     * @param pageNo   第几页
     * @param pageSize 每页大小
     * @return Result
     * @author jmj
     */
    @ApiOperation(value = "管理员分页接口", notes = "路径传参, 传入`第几页`、`每页大小`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "第几页", required = true, paramType = "path", dataTypeClass = int.class, example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, paramType = "path", dataTypeClass = int.class, example = "1"),
    })
    @RequiresRoles("admin")
    @GetMapping("/pageControl/{pageNo}/{pageSize}")
    public Result<Page<AdminDTO>> pageControl(@PathVariable int pageNo, @PathVariable int pageSize) throws SQLException {
        log.info("pageControl() called with parameters => [pageNo = {}],[pageSize = {}]", pageNo, pageSize);
        Page<AdminDTO> page = adminService.paginationForAdmin(pageNo, pageSize);
        if (page.getRecords() != null && !page.getRecords().isEmpty()) {
            log.info("[{}]分页调用成功,pageNo:{},pageSize:{}", SecurityUtils.getSubject().getPrincipal(), pageNo, pageSize);
            return new Result<>(page);
        }
        log.info("[{}]分页调用失败!,pageNo:{},pageSize:{}", SecurityUtils.getSubject().getPrincipal(), pageNo, pageSize);
        return new Result<>(CodeEnum.FAILURE, "未知错误");
    }

    /**
     * 用户分页功能
     * @return com.talent.controller.utils.Result<java.util.List < com.talent.domain.User>>
     * @author jmj
     * @date 下午 03:18 2021/12/12
     **/
    @ApiOperation(value = "普通用户分页接口", notes = "路径传参, 参数:`第几页`、`每页大小`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "第几页", defaultValue = "1", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", defaultValue = "5", required = true, dataTypeClass = Integer.class)
    })
    @RequiresRoles("normaladmin")
    @GetMapping("/listAllUsers/{pageNo}/{pageSize}")
    public Result<Page<User>> listAllUsers(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        log.info("listUsers() called with no parameters");
        Page<User> users = userService.findAndPaginationAllUsers(pageNo, pageSize);
        if (users.getRecords() != null && !users.getRecords().isEmpty()) {
            log.info("[{}]查询所有普通用户成功", SecurityUtils.getSubject().getPrincipal());
            return new Result<>(users);
        }
        log.info("[{}]查询所有普通用户失败", SecurityUtils.getSubject().getPrincipal());
        return new Result<>(CodeEnum.FAILURE, "查询普通用户错误");
    }

    /**
     * 用户账号的锁定与解锁
     * @param uName
     * @return com.talent.controller.utils.Result
     * @author jmj
     * @date 下午 03:18 2021/12/12
     **/
    @ApiOperation(value = "用户账号的锁定或解锁", notes = "参数: uname")
    @RequiresRoles("normaladmin")
    @PostMapping("/lockOrUnlockUserByUname/{uName}")
    public Result lockOrUnlockUserByUname(@ApiParam(name = "uName", value = "用户名", required = true, type = "path") @PathVariable String uName) {
        log.info("lockOrUnlockUserByUname() called with parameters => [uName = {}]", uName);
        User user = userService.findUserByName(uName);
        if (user != null) {
            user.setLocked(user.getLocked() == 0 ? 1 : 0);
            userService.updateUser(user);
            log.info("[{}]成功被锁定/解锁", uName);
            return user.getLocked() == 1 ? new Result<>(CodeEnum.SUCCESS, "锁定成功") : new Result<>(CodeEnum.SUCCESS, "解锁成功");
        } else {
            log.info("[{}]锁定/解锁失败!!!", uName);
            return new Result<>(CodeEnum.FAILURE, "用户不存在");
        }
    }

    /**
     * 未审核的企业分页查询
     * @param pageNo
     * @param pageSize
     * @return com.talent.controller.utils.Result<com.talent.domain.Page < com.talent.dto.EnterpriseDTO>>
     * @author jmj
     * @since 0:26 2021/12/14
     **/
    @ApiOperation(value = "未审核企业分页查询", notes = "路径传参, 参数: `第几页`、`每页大小`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "第几页", defaultValue = "1", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", defaultValue = "5", required = true, dataTypeClass = Integer.class)
    })
    @RequiresRoles("normaladmin")
    @GetMapping("/listAndPaginationUncheckedEnterprises/{pageNo}/{pageSize}")
    public Result<Page<EnterpriseDTO>> listUncheckedEnterprises(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        log.info("listUncheckedEnterprises() called with parameters => [pageNo = {}],[pageSize = {}]", pageNo, pageSize);
        Page<EnterpriseDTO> page = enterpriseService.paginationForUncheckedEnterprise(pageNo, pageSize);
        if (page.getRecords() != null && !page.getRecords().isEmpty()) {
            log.info("成功: 未审核企业分页查询");
            return new Result<>(page);
        } else {
            log.info("无未审核企业");
            return new Result<>(CodeEnum.SUCCESS, "无未审核企业");
        }
    }

    /**
     * 企业审核
     * @param ename 企业名称
     * @return com.talent.controller.utils.Result
     * @author jmj
     * @since 0:26 2021/12/14
     **/
    @ApiOperation(value = "企业用户审核", notes = "参数: `企业名称`")
    @RequiresRoles("normaladmin")
    @PostMapping("/checkEnterpriseByEname/{ename}")
    public Result checkEnterpriseByEname(@ApiParam(name = "ename", value = "企业名", required = true, type = "path") @PathVariable String ename) {
        log.info("checkEnterpriseByEname() called with parameters => [ename = {}]", ename);
        Boolean flag = enterpriseService.updateAndCertifyEnterprise(ename);
        if (flag) {
            log.info("成功: 企业用户审核成功");
            return new Result<>(CodeEnum.SUCCESS, "审核成功");
        }
        log.info("失败: 企业用户审核失败");
        return new Result<>(CodeEnum.FAILURE, "企业用户审核失败");
    }

    /**
     * 上传首页图片
     * @param file
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 01:05 2021/7/3
     **/
    @ApiOperation(value = "上传首页图片", notes = "传入`图片文件`即可")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = true, paramType = "query", dataType = "MultipartFile", dataTypeClass = MultipartFile.class),
    })
    @RequiresRoles("normaladmin")
    @PostMapping(path = "/imgUpload")
    public Result imgUpload(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("imgUpload() called with parameters => [file]");
        String realPath = WebUtils.getSession().getServletContext().getRealPath("/uploads/index/");
        log.info("路径: [{}]", realPath);
        File path = new File(realPath);
        if (!path.exists()) {
            path.mkdirs();
        }
        //获取上传名称
        String fileName = file.getOriginalFilename();
        log.info("文件名为[{}]", fileName);
        //把文件名称设置唯一值
        String uuid = UUID.randomUUID().toString().replace("-", "");
        fileName = uuid + "_" + fileName;
        file.transferTo(new File(realPath, fileName));
        log.info("[{}]上传成功", fileName);
        return new Result<>(CodeEnum.SUCCESS, "上传成功");
    }

    /**
     * 根据图片名删除首页图片
     * @param fileName
     * @return com.talent.controller.utils.Result
     * @author jmj
     * @since 16:21 2021/12/16
     **/
    @ApiOperation(value = "删除首页图片", notes = "参数: `图片名`, 注意: 带上图片扩展名(如.jpg)")
    @RequiresRoles("normaladmin")
    @DeleteMapping("/delHomePicByName/{fileName}")
    public Result delHomePicByName(@ApiParam(name = "fileName", value = "图片名", required = true, type = "path") @PathVariable String fileName) {
        log.info("delHomePicByName() called with parameters => [fileName = {}]", fileName);
        if (PicUtils.deletePic(PicUtils.DEFAULT_URL, fileName)) {
            log.info("图片删除成功");
            return new Result<>(CodeEnum.SUCCESS, "成功: 首页图片删除成功");
        } else {
            log.info("图片删除失败");
            return new Result<>(CodeEnum.FAILURE, "错误: 首页图片删除失败");
        }
    }
}
