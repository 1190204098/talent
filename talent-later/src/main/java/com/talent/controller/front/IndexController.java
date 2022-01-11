package com.talent.controller.front;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.PicUtils;
import com.talent.controller.utils.Result;
import com.talent.domain.Article;
import com.talent.domain.Page;
import com.talent.service.front.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章操作控制层
 * @author: luffy
 * @time: 2021/7/4 下午 07:26
 */
@Slf4j
@Api(tags = "首页相关接口")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IArticleService articleService;

    /**
     * 根据文章id查询文章
     * @author luffy
     * @date 下午 07:29 2021/7/4
     * @return java.lang.String
     **/
    @ApiOperation(value = "根据id查询文章",notes = "参数: `文章id`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "artid",value = "文章id",required = true,dataType = "integer",dataTypeClass = Integer.class,type = "path")
    })
    @GetMapping ("/getArticlesByUid/{artid}")
    public Result<Article> showArticle(@PathVariable Integer artid){
        log.info("showArticle() called with parameters => [artid]");
        Article article = articleService.findArticleByArtid(artid);
        if (article == null){
            log.info("id为[{}]的文章不存在",artid);
            return new Result<>(CodeEnum.FAILURE,"文章不存在");
        }
        log.info("id为[{}]的文章查询成功",artid);
        return new Result<>(CodeEnum.SUCCESS,article,"查询成功");
    }

    /**
     * 文章分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "文章分页查询接口",notes = "路径传参, 参数:`第几页`、`每页大小`")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "第几页",required = true,dataTypeClass = Integer.class,type = "path"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",required = true,dataTypeClass = Integer.class,type = "path")
    })
    @GetMapping("/getAllArticles/{pageNo}/{pageSize}")
    public Result<Page<Article>> showAndPaginationArticles(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        log.info("showAndPaginationArticles() called with parameters => [pageNo = {}],[pageSize = {}]", pageNo, pageSize);
        Page<Article> articles = articleService.findAndPaginationArticles(pageNo,pageSize);
        if (articles.getRecords() != null && !articles.getRecords().isEmpty()) {
            log.info("文章分页查询成功");
            return new Result<>(articles);
        }
        log.info("[{}],文章分页查询失败", SecurityUtils.getSubject().getPrincipal());
        return new Result<>(CodeEnum.FAILURE,"文章查询失败!");
    }

    /**
     * 首页图片分页获取
     * @param pageNo
     * @param pageSize
     * @return com.talent.controller.utils.Result
     */
    @ApiOperation(value = "获取首页图片链接列表，普通用户也能调用",notes = "参数: `第几页`、`每页几张`, 返回图片的url地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "第几页", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "每页几张", required = true, dataTypeClass = Integer.class)
    })
    @GetMapping("/getAllHomePics/{pageNo}/{pageSize}")
    public Result<Page<String>> getAndPageAllHomePics(@PathVariable int pageNo, @PathVariable int pageSize) {
        log.info("getAndPageAllHomePics() called with parameters => [pageNo = {}],[pageSize = {}]", pageNo, pageSize);
        Page<String> pics = PicUtils.getPicsFromLocal(PicUtils.DEFAULT_URL, pageNo, pageSize);
        if (!pics.getRecords().isEmpty()) {
            log.info("图片返回成功");
            return new Result<>(pics);
        } else {
            log.info("无图片");
            return new Result<>(CodeEnum.SUCCESS, "无图片");
        }
    }

}
