package com.talent.service.front.impl;

import com.talent.dao.IActionDao;
import com.talent.dao.IRoleDao;
import com.talent.dao.IUserDao;
import com.talent.domain.Page;
import com.talent.service.front.IUserService;
import com.talent.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户业务层
 * @author: luffy
 * @time: 2021/7/10 下午 03:13
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;
    @Resource
    private IRoleDao roleDao;
    @Resource
    private IActionDao actionDao;

    @Override
    public Page<User> findAndPaginationAllUsers(Integer pageNo,Integer pageSize) {
        log.info("findAndPaginationAll() called with parameters => [pageNo = {}],[pageSize = {}]", pageNo, pageSize);
        Page<User> page = new Page<>();
        Integer total = userDao.count();
        page.setTotal(total);
        if (total == 0) {
            log.info("数据库中无数据");
            return page;
        }
        if ((pageNo - 1) * pageSize <= total) {
            log.info("分页成功");
            List<User> users = userDao.findAndPaginationAllUsers((pageNo - 1)*pageSize, pageSize);
            page.setRecords(users);
            page.setSize(pageSize);
            page.setCurrent(pageNo);
        } else {
            log.info("超出界限,startNo:{},total:{}",(pageNo - 1) * pageSize,total);
        }
        return page;
    }

    @Override
    public User findUserByName(String uName) {
        log.info("业务层 : findUserByName() called with parameters => [uName = {}]",uName);
        log.info("userDao : findUserByName() called with parameters => [uName = {}]",uName);
        return userDao.findUserByName(uName);
    }

    @Override
    public void updateUser(User user) {
        log.info("业务层 : updateUser() called with parameters => [user = {}]", user);
        log.info("userDao : updateUser() called with parameters => [user = {}]",user);
        userDao.updateUser(user);
    }

    @Override
    public void addUser(User user) {
        log.info("业务层 : addUser() called with parameters => [user = {}]", user);
        log.info("userDao : addUser() called with parameters => [user = {}]",user);
        userDao.addUser(user);
    }

    @Override
    public void createUserRole(Integer uid, Integer rid) {
        log.info("业务层 : createUserRole() called with parameters => [uid = {}],[rid = {}]", uid, rid);
        log.info("userDao : createUserRole() called with parameters => [uid = {},rid = {}]", uid, rid);
        userDao.createUserRole(uid,rid);
    }

    @Override
    public String findRoleByName(String uName) {
        log.info("业务层 : findRoleByName() called with parameters => [uName = {}]", uName);
        log.info("userDao : findRoleByName() called with parameters => [uName = {}]", uName);
        return userDao.findRoleByName(uName);
    }

    @Override
    public List<User> findUserLikeName(String uName) {
        log.info("业务层 : findUserLikeName() called with parameters => [uName = {}]", uName);
        log.info("userDao : findUserLikeName() called with parameters => [uName = {}]", uName);
        return userDao.findUserLikeName(uName);
    }

    @Override
    public User findUserByUid(Integer uid) {
        log.info("业务层 : findUserByUid() called with parameters => [uid = {}]", uid);
        log.info("userDao : findUserByUid() called with parameters => [uid = {}]", uid);
        return userDao.findUserByUid(uid);
    }

    @Override
    public User updateTimeAndFindUser(String uName) {
        log.info("业务层 : updateTimeAndFindUser() called with parameters => [uName = {}]", uName);
        log.info("userDao : findUserByName() called with parameters => [uName = {}]", uName);
        User user = userDao.findUserByName(uName);
        if(user!=null){
            Date date = new Date(System.currentTimeMillis());
            log.info("userDao : updateDate() called with parameters => [uName = {},date = {}]", uName,date);
            userDao.updateDate(uName, date);
        }
        return user;
    }

    @Override
    public Map<String, Object> findAuthByName(String uName) {
        log.info("业务层 : findAuthByName() called with parameters => [uName = {}]", uName);
        Map<String, Object> map = new HashMap<>(2);
        log.info("userDao : findUserByName() called with parameters => [uName = {}]", uName);
        Integer uid = userDao.findUserByName(uName).getUid();
        log.info("roleDao : findRoleByUid() called with parameters => [uid = {}]", uid);
        map.put("allRoles",roleDao.findRoleByUid(uid));
        log.info("actionDao : findActionByUid() called with parameters => [uid = {}]", uid);
        map.put("allPermissions",actionDao.findActionByUid(uid));
        return map;
    }
}
