package com.talent.service.front.impl;

import com.talent.dao.IArticleDao;
import com.talent.domain.Page;
import com.talent.service.front.IArticleService;
import com.talent.domain.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 文章业务层
 * @author: luffy
 * @time: 2021/7/4 下午 05:34
 */
@Service("articleService")
@Slf4j
public class ArticleServiceImpl implements IArticleService {

    @Resource
    private IArticleDao articleDao;

    /**
     * @author luffy
     * 增加文章
     * @date 下午 05:36 2021/7/4
     * @param article
     * @return void
     **/
    @Override
    public void addNews(Article article) {
        log.info("业务层 : addNews() called with parameters => [article]");
        log.info("articleDao : addNews() called with parameters => [article = {}]",article);
        articleDao.addNews(article);
    }

    /**
     * @author luffy
     * 获取所有文章
     * @date 下午 05:36 2021/7/4
     * @return java.util.List<com.talent.vo.Article>
     **/
    @Override
    public List<Article> findAllArticle() {
        log.info("业务层 : findAllArticle() called with no parameters");
        log.info("articleDao : findAllArticle() called with no parameters");
        return articleDao.findAllArticle();
    }

    /**
     * @author luffy
     * 根据artid查询文章
     * @date 下午 07:35 2021/7/4
     * @param artid
     * @return com.talent.vo.Article
     **/
    @Override
    public Article findArticleByArtid(Integer artid){
        log.info("业务层 : findArticleByArtid() called with parameters => [artid = {}]", artid);
        log.info("articleDao : findArticleByArtid() called with parameters => [artid = {}]",artid);
        return articleDao.findArticleByArtid(artid);
    }

    @Override
    public Page<Article> findAndPaginationArticles(Integer pageNo,Integer pageSize) {
        log.info("findAndPaginationArticles() called with parameters => [pageNo = {}], [pageSize = {}]",pageNo, pageSize);
        Page<Article> page = new Page<>();
        Integer total = articleDao.count();
        if (total == 0) {
            log.info("文章表中无数据");
            return page;
        }
        page.setTotal(total);
        if ((pageNo - 1) * pageSize <= total) {
            List<Article> articles = articleDao.findAndPaginationArticles((pageNo - 1) * pageSize, pageSize);
            page.setRecords(articles);
            page.setCurrent(pageNo);
            page.setSize(pageSize);
            log.info("文章分页查询成功");
        } else {
            log.info("文章分页查询超出界限, startNo:{}, total:{}", (pageNo-1)*pageSize, pageSize);
        }
        return page;
    }
}
