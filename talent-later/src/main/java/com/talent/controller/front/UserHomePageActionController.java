package com.talent.controller.front;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.Result;
import com.talent.controller.utils.WebUtils;
import com.talent.domain.PersonalInfo;
import com.talent.domain.User;
import com.talent.model.PersonalInfoModel;
import com.talent.service.front.IPinfoService;
import com.talent.service.front.IUserService;
import com.talent.utils.MD5Code;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户个人主页操作controller
 * @author: luffy
 * @time: 2021/7/12 下午 04:08
 */
@Slf4j
@RestController
@Api(tags = "用户主页相关操作")
@RequestMapping("/homePageAction")
public class UserHomePageActionController {

    @Resource
    private IPinfoService pinfoService;

    @Resource
    private IUserService userService;

    /**
     * 获取用户信息
     * @author luffy
     * @date 下午 10:46 2021/12/5
     * @return com.talent.controller.utils.Result
     **/
    @ApiOperation(value = "获取用户信息接口",notes = "无参")
    @RequiresRoles("user")
    @GetMapping("/getPersonalInfo")
    public Result getPersonalInfo(){
        log.info("getPersonalInfo() called with no parameters");
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> map = new HashMap<>(8);
        PersonalInfo info = pinfoService.findInfoByName(uName);
        if (info == null) {
            log.info("用户[{}]的信息为空",uName);
        } else {
            log.info("获取[{}]的个人信息",uName);
            map.put("sex", info.getSex());
            map.put("address", info.getAddress());
            map.put("email", info.getEmail());
            map.put("birthday", info.getBirthday());
            map.put("specialty", info.getSpecialty());
            map.put("wages", info.getWages());
            map.put("college", info.getCollege());
        }
        map.put("tel", userService.findUserByName(uName).getTel());
        return new Result<>(CodeEnum.SUCCESS,map,"获取成功");
    }

    /**
     * 为用户增加或修改头像
     * @author luffy
     * @date 下午 11:02 2021/12/5
     * @param file MultipartFile
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/
    @ApiOperation(value = "添加或修改用户头像",notes = "参数: 图片文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file",value = "图片文件",dataTypeClass = MultipartFile.class)
    })
    @RequiresRoles("user")
    @PostMapping("/changePic")
    public Result changePic(@RequestBody MultipartFile file) throws IOException {
        log.info("changePic() called with parameters => [file]");
        if (file.isEmpty()) {
            log.info("上传失败，文件为空！");
        }
        byte[] pic = file.getBytes();
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        if (pinfoService.findInfoByName(uName) == null) {
            log.info("用户[{}]的个人信息为空，创建新的个人信息",uName);
            PersonalInfo info = new PersonalInfo();
            info.setUid(userService.findUserByName(uName).getUid());
            pinfoService.addInfo(info);
        }
        pinfoService.addPic(pic, uName);
        log.info("头像上传成功！");
        return new Result<>(CodeEnum.SUCCESS,"头像上传成功");
    }

    /**
     * 获取用户头像
     * @author luffy
     * @date 下午 11:14 2021/12/5
     * @param response HttpServletResponse
     * @return com.talent.controller.utils.Result
     **/
    @ApiOperation(value = "获取用户头像",notes = "无参")
    @RequiresRoles("user")
    @GetMapping("/getPic")
    public Result getPic(HttpServletResponse response) throws IOException {
        log.info("getPic() called with parameters => [response]");
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputStream = response.getOutputStream();
        InputStream in;
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        PersonalInfo info = pinfoService.findInfoByName(uName);
        if (info == null) {
            String filePath = WebUtils.getSession().getServletContext().getRealPath("/images/HP.jpg");
            File file = new File(filePath);
            in = new FileInputStream(file);
        } else {
            byte[] pic = info.getPic();
            if (pic != null) {
                in = new ByteArrayInputStream(pic);
            } else {
                String filePath = WebUtils.getSession().getServletContext().getRealPath("/images/HP.jpg");
                File file = new File(filePath);
                in = new FileInputStream(file);
            }
        }
        int len;
        // 定义字节流缓冲数组
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.close();
        log.info("用户[{}]获取头像成功",uName);
        return new Result<>(CodeEnum.SUCCESS);
    }

    /**
     * 修改用户信息
     * @author luffy
     * @date 下午 11:17 2021/12/5
     * @param info 个人信息
     * @return com.talent.controller.utils.Result
     **/
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "info",value = "用户信息",dataType = "PersonalInfoModel",dataTypeClass = PersonalInfoModel.class),
    })
    @RequiresRoles("user")
    @PostMapping("/changePersonalInfo")
    public Result changePersonalInfo(@RequestBody PersonalInfoModel info) {
        log.info("changePersonalInfo() called with parameters =>[info = {}]", info);
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findUserByName(uName);
        if (user.getTel()==null||!user.getTel().equals(info.getTel())) {
            user.setTel(info.getTel());
            userService.updateUser(user);
            log.info("用户[{}]手机号已更新",uName);
        }
        if (pinfoService.findInfoByName(uName) == null) {
            info.getInfo().setUid(user.getUid());
            pinfoService.addInfo(info.getInfo());
            log.info("已新增用户[{}]个人信息",uName);
            return new Result<>(CodeEnum.SUCCESS,"用户信息新增成功");
        } else {
            pinfoService.updateInfoByName(info.getInfo(), uName);
            log.info("已修改用户[{}]个人信息",uName);
            return new Result<>(CodeEnum.SUCCESS,"用户信息修改成功");
        }
    }

    /**
     * 修改密码
     * @author luffy
     * @date 下午 11:25 2021/12/5
     * @param oldPassword 旧密码
     * @param password 新密码
     * @return com.talent.controller.utils.Result
     **/
    @ApiOperation(value = "用户修改密码",notes = "参数: `旧密码`、`新密码`<br>`使用x-www-form-urlencoded类型提交数据`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword",value = "旧密码",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "password",value = "新密码",dataType = "String",dataTypeClass = String.class)
    })
    @RequiresRoles("user")
    @PostMapping("/changeUserPassword")
    public Result changeUserPassword(@RequestParam String oldPassword,@RequestParam String password){
        log.info("changeUserPassword() called with parameters => [oldPassword = {}],[password = {}]", oldPassword, password);
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        User oldUser = userService.findUserByName(uName);
        String oldPwdMd5 = new MD5Code().getMD5ofStr("{{[[" + oldPassword + "]]}}");
        if (!oldUser.getPassword().equals(oldPwdMd5)) {
            log.info("用户[{}]修改密码失败：密码错误",uName);
            return new Result<>(CodeEnum.FAILURE,"密码错误");
        }
        oldUser.setPassword(new MD5Code().getMD5ofStr("{{[[" + password + "]]}}"));
        userService.updateUser(oldUser);
        log.info("更改[{}]的密码成功",uName);
        return new Result<>(CodeEnum.SUCCESS,"修改密码成功");
    }
}