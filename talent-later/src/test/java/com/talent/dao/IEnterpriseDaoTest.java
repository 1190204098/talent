package com.talent.dao;

import com.talent.domain.Enterprise;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class IEnterpriseDaoTest {
    @Autowired
    IEnterpriseDao enterpriseDao;

    @Test
    public void findAndPaginationAll() {
        System.out.println(enterpriseDao.findAndPaginationAll(0,5));
    }

    @Test
    public void count() {
        System.out.println(enterpriseDao.count());
    }

    @Test
    public void countLikeEname() {
        System.out.println(enterpriseDao.countLikeEname("jmj"));
    }

    @Test
    public void countWait4Review() {
        System.out.println(enterpriseDao.countWait4Review());
    }

    @Test
    public void findEnterpriseByUid() {
        System.out.println(enterpriseDao.findEnterpriseByUid(1));
    }

    @Test
    public void findAndPaginationEnterpriseLikeEname() {
        System.out.println(enterpriseDao.findAndPaginationEnterpriseLikeEname("jmj",0,5));
    }

    @Test
    public void addEnterprise() {
        Enterprise enterprise = new Enterprise();
        enterprise.setUid(1);
        enterprise.setEname("jmjj");
        enterprise.setAddress("jj");
        enterprise.setTel("123");
        enterprise.setEmail("333");
        enterprise.setIntro("33");
        enterprise.setStatus(0);

        System.out.println(enterpriseDao.addEnterprise(enterprise));
    }

    @Test
    public void updateEnterprise() {
        Enterprise enterprise = new Enterprise();
        enterprise.setUid(1);
        enterprise.setEname("jmjjjj");

        System.out.println(enterpriseDao.updateEnterpriseByUid(enterprise));
    }

    @Test
    public void findAndPageAllWait4ReviewEnterprises() {
        System.out.println(enterpriseDao.findAndPageAllWait4ReviewEnterprises(0, 5));
    }

    @Test
    public void findEnterpriseByEname() {
        System.out.println(enterpriseDao.findEnterpriseByEname("jmj"));
    }
}