package com.talent.test;

import com.talent.dao.IArticleDao;
import com.talent.domain.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author: luffy
 * @time: 2021/12/6 上午 12:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml" )
public class testDao {

    @Autowired
    private IArticleDao articleDao;

    @Test
    public void testFindAllArticle(){
        List<Article> allArticle = articleDao.findAllArticle();
        for (Article article : allArticle) {
            System.out.println(article);
        }
    }
}
