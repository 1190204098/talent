package com.talent.service.front;

import com.talent.domain.Article;
import com.talent.domain.Page;

import java.util.List;

/**
 * @description:
 * @author: luffy
 * @time: 2021/7/4 下午 05:33
 */
public interface IArticleService {


    /**
     * @author jmj
     * 保存文章到数据库
     * @date 15:51 2021/7/4
     * @param article 文章
     * @return void
     **/
    void addNews(Article article);

    /**
     * @author luffy
     * 查询所有文章
     * @date 下午 05:26 2021/7/4
     * @return java.util.List<com.talent.vo.Article>
     **/
    List<Article> findAllArticle();

    /**
     * @author luffy
     * 根据artid查询文章
     * @date 下午 07:35 2021/7/4
     * @param artid
     * @return com.talent.vo.Article
     **/
    Article findArticleByArtid(Integer artid);

    Page<Article> findAndPaginationArticles(Integer pageNo, Integer pageSize);
}
