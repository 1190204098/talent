package com.talent.test;

import com.talent.dao.IResumeDao;
import com.talent.domain.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: 简历持久层测试
 * @author: luffy
 * @time: 2021/7/20 下午 03:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml" )
public class testResumeDao {

    @Autowired
    private IResumeDao resumeDao;

    /**
     * @author luffy
     * 根据用户id查询简历
     * @date 下午 03:08 2021/7/20
     **/
    @Test
    public void testFindResumeByUid(){
        System.out.println(resumeDao.findResumeByUid(7));
    }

    /**
     * @author luffy
     * 增加简历
     * @date 下午 03:08 2021/7/20
     **/
    @Test
    public void testAddResume(){
        Resume resume = new Resume();
        resume.setName("也是张三");
        resumeDao.addResume(resume,7);
    }

    /**
     * @author luffy
     * 更新简历
     * @date 下午 03:08 2021/7/20
     **/
    @Test
    public void testUpdateResume(){
        Resume resume = resumeDao.findResumeByUid(7);
        resume.setAward("123");
        resumeDao.updateResume(resume,7);
    }
}
