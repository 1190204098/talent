package com.talent.service.front;

import com.talent.domain.Page;
import com.talent.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @description: 用户业务层
 * @author: luffy
 * @time: 2021/7/10 下午 03:05
 */
public interface IUserService {

    /**
     * 查询所有账户
     * @author jmj
     * @since 23:00 2021/12/13
     * @param pageNo
     * @param pageSize
     * @return com.talent.domain.Page<com.talent.domain.User>
     **/
    Page<User> findAndPaginationAllUsers(Integer pageNo, Integer pageSize);

    /**
     * @author luffy
     * 根据用户名称查询用户
     * @date 下午 01:17 2021/7/10
     * @param uName 用户名称
     * @return com.talent.vo.User
     **/
    User findUserByName(String uName);

    /**
     * @author luffy
     * 更新用户
     * @date 下午 01:20 2021/7/10
     * @param user 用户
     **/
    void updateUser(User user);

    /**
     * @author luffy
     * 增加用户
     * @date 下午 01:27 2021/7/10
     * @param user 用户对象
     **/
    void addUser(User user);

    /**
     * @author luffy
     * 创建用户角色
     * @date 下午 01:30 2021/7/10
     * @param uid 用户编号
     * @param rid 角色编号
     **/
    void createUserRole(Integer uid, Integer rid);

    /**
     * @author luffy
     * 查询用户最高角色
     * @date 下午 01:32 2021/7/10
     * @param uName 用户名
     * @return java.lang.String
     **/
    String findRoleByName(String uName);

    /**
     * @author luffy
     * 模糊查询用户
     * @date 下午 02:55 2021/7/10
     * @param uName 用户名
     * @return java.util.List<com.talent.vo.User>
     **/
    List<User> findUserLikeName(String uName);

    /**
     * @author luffy
     * 根据用户id查询用户
     * @date 下午 01:34 2021/7/10
     * @param uid
     * @return com.talent.vo.User
     **/
    User findUserByUid(Integer uid);

    /**
     * @author luffy
     * 查询用户，若存在则更新最后登录时间
     * @date 下午 03:46 2021/7/10
     * @param uName
     * @return com.talent.vo.User
     **/
    User updateTimeAndFindUser(String uName);

    /**
     * @author luffy
     * 查询用户角色
     * @date 下午 03:17 2021/7/10
     * @param uName
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> findAuthByName(String uName);
}
