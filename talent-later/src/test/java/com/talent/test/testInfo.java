package com.talent.test;

import com.talent.dao.IPinfoDao;
import com.talent.domain.PersonalInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 测试个人信息相关方法
 * @author: luffy
 * @time: 2021/7/12 下午 02:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml" )
public class testInfo {

    @Resource
    private IPinfoDao pinfoDao;

    /**
     * @author luffy
     * 测试查询所有
     * @date 下午 02:23 2021/7/12
     **/
    @Test
    public void testFindAll(){
        List<PersonalInfo> pList = pinfoDao.findAll();
        for (PersonalInfo info : pList) {
            System.out.println(info);
        }
    }

    /**
     * @author luffy
     * 根据用户id查询个人信息
     * @date 下午 02:23 2021/7/12
     **/
    @Test
    public void testFindInfoByUid(){
        PersonalInfo info = pinfoDao.findInfoByUid(7);
        System.out.println(info);
    }

    /**
     * @author luffy
     * 增加个人信息记录
     * @date 下午 02:23 2021/7/12
     **/
    @Test
    public void testAddInfo(){
        PersonalInfo info = new PersonalInfo();
        info.setUid(7);
        info.setAddress("浙江杭州");
        pinfoDao.addInfo(info);
    }

    /**
     * @author luffy
     * 更新用户信息
     * @date 下午 02:23 2021/7/12
     **/
    @Test
    public void testUpdateInfoByUid(){
        PersonalInfo info = new PersonalInfo();
        info.setUid(7);
        info.setAddress("浙江杭州");
        pinfoDao.updateInfoByUid(info,7);
    }
}
