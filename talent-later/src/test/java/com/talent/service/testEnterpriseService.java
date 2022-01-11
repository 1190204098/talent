package com.talent.service;

import com.talent.domain.Enterprise;
import com.talent.domain.Page;
import com.talent.dto.EnterpriseDTO;
import com.talent.service.front.IEnterpriseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author: luffy
 * @time: 2021/12/12 下午 11:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class testEnterpriseService {
    
    @Autowired
    private IEnterpriseService enterpriseService;
    
    @Test
    public void testFindEnterpriseByUname() throws Exception {
        List<Enterprise> enterpriseByUname = enterpriseService.findEnterpriseByUname("4");
        for (Enterprise enterprise : enterpriseByUname) {
            System.out.println("enterprise = " + enterprise);
        }
    }
    
    @Test
    public void testUpdateEnterprise() throws Exception {
        Enterprise enterprise = new Enterprise();
        enterprise.setStatus(1);
        enterpriseService.updateEnterprise(enterprise);
    }
    
    @Test
    public void testAddEnterprise() throws Exception {
        Enterprise enterprise = new Enterprise();
        enterprise.setUid(10);
        enterprise.setStatus(0);
        for (int i = 0; i < 3; i++) {
            enterpriseService.addEnterprise(enterprise);
        }
    }

    @Test
    public void testUpdateAndCertifyEnterprise() throws Exception {
        enterpriseService.updateAndCertifyEnterprise("419打印店");
    }

    @Test
    public void testPaginationForUncheckedEnterprise() throws Exception {
        Page<EnterpriseDTO> enterpriseDTOPage = enterpriseService.paginationForUncheckedEnterprise(1, 3);
        List<EnterpriseDTO> records = enterpriseDTOPage.getRecords();
        for (EnterpriseDTO record : records) {
            System.out.println("record = " + record);
        }
    }

    @Test
    public void testFindAndPaginationAllEnterprise() throws Exception {
        Page<EnterpriseDTO> enterpriseDTOPage = enterpriseService.findAndPaginationAllEnterprise(1, 4);
        List<EnterpriseDTO> records = enterpriseDTOPage.getRecords();
        for (EnterpriseDTO record : records) {
            System.out.println("record = " + record);
        }
    }

    @Test
    public void testFindAndPaginationEnterpriseLikeEname() throws Exception {
        Page<EnterpriseDTO> enterpriseDTOPage = enterpriseService.findAndPaginationEnterpriseLikeEname("3",1, 4);
        List<EnterpriseDTO> records = enterpriseDTOPage.getRecords();
        for (EnterpriseDTO record : records) {
            System.out.println("record = " + record);
        }
    }

}
