package com.talent.controller.front;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.Result;
import com.talent.controller.utils.WebUtils;
import com.talent.domain.Page;
import com.talent.domain.Resume;
import com.talent.model.ResumeSearchParamModel;
import com.talent.service.front.IResumeService;
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

/**
 * 简历操作控制层
 * @author: luffy
 * @time: 2021/8/7 下午 01:32
 */
@Slf4j
@RestController
@RequestMapping("/resume")
@Api(tags = "简历相关接口")
public class ResumeController {

    @Resource
    private IResumeService resumeService;

    /**
     * 增加个人简历头像
     * @param file 图片
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 11:02 2021/12/7
     **/
    @ApiOperation(value = "添加个人简历头像",notes = "参数: `图片文件`")
    @ApiImplicitParam(name = "file",value = "图片文件",dataType = "MultipartFile",dataTypeClass = MultipartFile.class)
    @RequiresRoles("user")
    @PostMapping("/addAppendix")
    public Result addAppendix(@RequestBody MultipartFile file) throws IOException {
        log.info("addAppendix() called with parameters => [file = {}]", file.getOriginalFilename());
        if (file.isEmpty()) {
            log.info("上传失败，文件为空！");
        }
        byte[] pic = file.getBytes();
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        if (resumeService.findResumeByuName(uName) == null) {
            log.info("用户[{}]个人简历为空，创建新的个人简历", uName);
            resumeService.addResume(new Resume(), uName);
        }
        resumeService.addPic(pic, uName);
        log.info("文件[{}]上传成功！",file.getOriginalFilename());
        return new Result<>(CodeEnum.SUCCESS,"上传成功");
    }

    /**
     * 获取用户简历的头像
     * @author luffy
     * @date 下午 11:18 2021/12/7
     * @param response response
     * @return com.talent.controller.utils.Result
     **/
    @ApiOperation(value = "获取用户简历头像",notes = "无参")
    @RequiresRoles("user")
    @GetMapping("/getAppendix")
    public Result getAppendix(HttpServletResponse response) throws IOException {
        log.info("getAppendix() called with parameters => [response]");
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputStream = response.getOutputStream();
        InputStream in;
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        Resume resume = resumeService.findResumeByuName(uName);
        if (resume == null) {
            String filePath = WebUtils.getSession().getServletContext().getRealPath("/images/HP.jpg");
            File file = new File(filePath);
            in = new FileInputStream(file);
        } else {
            byte[] pic = resume.getPic();
            if (pic != null) {
                in = new ByteArrayInputStream(pic);
            } else {
                String filePath = WebUtils.getSession().getServletContext().getRealPath("/images/HP.jpg");
                File file = new File(filePath);
                in = new FileInputStream(file);
            }
        }
        int len = 0;
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
     * 获取用户的简历
     * @author luffy
     * @date 下午 11:25 2021/12/7
     * @return com.talent.controller.utils.Result
     **/
    @ApiOperation(value = "获取用户简历信息",notes = "参数: 无")
    @RequiresRoles("user")
    @GetMapping("/getResume")
    public Result<Resume> getResume() {
        log.info("getResume() called with no parameters");
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        Resume resume = resumeService.findResumeByuName(uName);
        if (resume == null) {
            log.info("用户[{}]的个人简历为空",uName);
            return new Result<>(CodeEnum.SUCCESS,null,"用户简历为空");
        } else {
            log.info("获取用户[{}]的个人简历",uName);
            return new Result<>(CodeEnum.SUCCESS,resume,"获取用户简历成功");
        }
    }

    /**
     * 增加或修改用户简历
     * @author luffy
     * @date 下午 11:29 2021/12/7
     * @param resume 简历
     * @return com.talent.controller.utils.Result
     **/
    @ApiOperation(value = "添加或修改用户简历",notes = "参数: Resume简历类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resume",dataType = "Resume",dataTypeClass = Resume.class)
    })
    @RequiresRoles("user")
    @PostMapping("/changeResume")
    public Result changeResume(@RequestBody Resume resume) throws IOException {
        log.info("changeResume() called with parameters => [resume = {}]",resume);
        String uName = (String) SecurityUtils.getSubject().getPrincipal();
        if (resumeService.findResumeByuName(uName) == null) {
            resumeService.addResume(resume, uName);
            log.info("用户[{}]的简历为空，已创建新的简历",uName);
            return new Result<>(CodeEnum.SUCCESS,"创建新简历成功");
        } else {
            resumeService.updateResume(resume, uName);
            log.info("用户[{}]的简历已更新",uName);
            return new Result<>(CodeEnum.SUCCESS,"简历更新成功");
        }
    }

    /**
     * 搜索简历, 可以根据姓名、求职地点、职业、工作年限搜索简历
     * @author jmj
     * @since 16:50 2022/1/6
     * @param resumeSearchParamModel
     * @param pageNo
     * @param pageSize
     * @return com.talent.controller.utils.Result<com.talent.domain.Page < com.talent.domain.Resume>>
     **/
    @ApiOperation(value = "搜索简历", notes = "参数: `姓名`、`求职地点`、`职业`、`工作年限`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "第几页", dataTypeClass = int.class, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataTypeClass = int.class, paramType = "query")
    })
    @GetMapping("/search")
    public Result<Page<Resume>> searchResume(ResumeSearchParamModel resumeSearchParamModel, int pageNo, int pageSize) {
        log.info("searchResume() called with parameters => [resumeSearchParamModel = {}],[pageNo = {}],[pageSize = {}]", resumeSearchParamModel, pageNo, pageSize);
        Page<Resume> page = resumeService.fuzzySearch(resumeSearchParamModel, pageNo, pageSize);
        if (!page.getRecords().isEmpty()) {
            log.info("查询成功");
            return new Result<>(page);
        }
        log.info("数据库无数据");
        return new Result<>(CodeEnum.SUCCESS, "无简历数据");
    }
}
