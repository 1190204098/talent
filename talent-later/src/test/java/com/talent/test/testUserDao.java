package com.talent.test;

import com.talent.dao.IRoleDao;
import com.talent.dao.IUserDao;
import com.talent.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: luffy
 * @time: 2021/7/10 下午 01:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml" )
public class testUserDao {

    @Autowired
    IUserDao userDao;
    @Autowired
    IRoleDao roleDao;

    /**
     * @author luffy
     * 测试查询所有
     * @date 下午 01:45 2021/7/10
     **/
    @Test
    public void testFindAll(){
        List<User> userList = userDao.findAndPaginationAllUsers(1,5);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * @author luffy
     * 根据用户名称查询用户
     * @date 下午 01:49 2021/7/10
     **/
    @Test
    public void testFindUserByName(){
        User user = userDao.findUserByName("test");
        System.out.println(user);
    }

    /**
     * @author luffy
     * 更新账户最后登录日期
     * @date 下午 01:58 2021/7/10
     **/
    @Test
    public void testUpdateDate(){
        userDao.updateDate("test",new Date(System.currentTimeMillis()));
        User user = userDao.findUserByName("test");
        System.out.println(user);
    }

    /**
     * @author luffy
     * 更新用户
     * @date 下午 02:07 2021/7/10
     **/
    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setUName("test");
        user.setLocked(0);
        user.setPassword("123");
        userDao.updateUser(user);
        User user2 = userDao.findUserByName("test");
        System.out.println(user2);
    }

    /**
     * @author luffy
     * 增加用户
     * @date 下午 02:32 2021/7/10
     * @return void
     **/
    @Test
    public void testAddUser(){
        User user = new User();
        user.setUName("test2");
        user.setLocked(0);
        user.setPassword("123");
        user.setStartDate(new Date(System.currentTimeMillis()));
        user.setTel("12345");
        userDao.addUser(user);
        User user2 = userDao.findUserByName("test2");
        System.out.println(user2);
    }

    /**
     * @author luffy
     * 创建用户角色
     * @date 下午 02:51 2021/7/10
     * @return void
     **/
    @Test
    public void testCreateUserRole(){
//        userDao.createUserRole(2,3);
        //尝试给普通用户赋予管理员角色
//        userDao.createUserRole(2,1);
    }

    /**
     * @author luffy
     * 测试roleDao层方法
     * @date 下午 02:18 2021/7/10
     * @return void
     **/
    @Test
    public void testFindRoleByUid(){
        Set<String> roles = roleDao.findRoleByUid(1);
        for (String role : roles) {
            System.out.println(role);
        }
    }

    /**
     * @author luffy
     * 根据用户名查询用户角色(UserDao层方法)
     * @date 下午 02:22 2021/7/10
     * @return void
     **/
    @Test
    public void testFindRoleByUid2(){
        String roleName = userDao.findRoleByName("test");
        System.out.println(roleName);
    }

    /**
     * @author luffy
     * 模糊查询用户
     * @date 下午 02:56 2021/7/10
     * @return void
     **/
    @Test
    public void testFindUserLikeName(){
        List<User> userList = userDao.findUserLikeName("t");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * @author luffy
     * 根据用户id查询用户
     * @date 下午 02:59 2021/7/10
     * @return void
     **/
    @Test
    public void testFindUserByUid(){
        User user = userDao.findUserByUid(1);
        System.out.println(user);
    }
}
