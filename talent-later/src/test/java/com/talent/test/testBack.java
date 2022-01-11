package com.talent.test;

import com.talent.dao.IActionDao;
import com.talent.dao.IAdminDao;
import com.talent.domain.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @description:
 * @author: luffy
 * @time: 2021/6/30 下午 05:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml" )
public class testBack {

    @Autowired
    private IAdminDao adminDao;

    @Autowired
    private IActionDao actionDao;

    /**
     * @author luffy
     * 测试查询账户
     * @date 下午 05:34 2021/6/30
     * @return void
     **/
    @Test
    public void testFindAdminByaName(){
        Admin admin = null;
        try {
            admin = adminDao.findAdminByaName("test");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(admin);
    }

    /**
     * @author luffy
     * 测试action方法
     * @date 下午 09:56 2021/6/30
     * @return void
     **/
    @Test
    public void testAddAdmin(){
        try {
            Admin admin = new Admin();
            admin.setAName("1111111111111111111111111111");
            admin.setPassword("ooo");
            admin.setDate(new Timestamp(System.currentTimeMillis()));
            admin.setPrivilege(1);
            admin.setLocked(0);
            adminDao.addAdmin(admin);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @author luffy
     * 测试创建角色和管理员
     * @date 下午 08:55 2021/7/1
     * @return void
     **/
    @Test
    public void testCreateAdminAndRole(){
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("aid",1);
            map.put("rid",2);
            adminDao.createAdminAndRole(map);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @author luffy
     * 测试通过aName查找权限最高的角色
     * @date 下午 08:56 2021/7/1
     * @return void
     **/
    @Test
    public void testFindRoleByaName(){
        String role = adminDao.findRoleByaName("admin");
        System.out.println(role);
    }

}
