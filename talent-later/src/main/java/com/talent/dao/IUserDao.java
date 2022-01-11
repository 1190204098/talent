package com.talent.dao;

import com.talent.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description: 账户持久层
 * @author: luffy
 * @time: 2021/7/10 下午 01:15
 */
public interface IUserDao {

    /**
     * @author luffy
     * 查询所有账户
     * @date 下午 01:16 2021/7/10
     * @return java.util.List<com.talent.vo.User>
     **/
    List<User> findAndPaginationAllUsers(@Param("startNo") Integer startNo,@Param("pageSize") Integer pageSize);

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
     * 更新账户最后登录日期
     * @date 下午 01:19 2021/7/10
     * @param uName 账户名称
     * @param date 最后登录时间
     **/
    void updateDate(@Param("uName") String uName,@Param("date") Date date);

    /**
     * @author luffy
     * 更新用户
     * @date 下午 01:20 2021/7/10
     * @param user 用户
     **/
    void updateUser(@Param("user") User user);

    /**
     * @author luffy
     * 增加用户
     * @date 下午 01:27 2021/7/10
     * @param user 用户对象
     **/
    void addUser(@Param("user")User user);

    /**
     * 创建用户角色
     * @author luffy
     * @date 下午 01:30 2021/7/10
     * @param uid 用户编号
     * @param rid 角色编号
     **/
    void createUserRole(@Param("uid")Integer uid,@Param("rid")Integer rid);

    /**
     * 查询用户最高角色
     * @author luffy
     * @date 下午 01:32 2021/7/10
     * @param uName 用户名
     * @return java.lang.String
     **/
    String findRoleByName(String uName);

    /**
     * 模糊查询用户
     * @author luffy
     * @date 下午 02:55 2021/7/10
     * @param uName 用户名
     * @return java.util.List<com.talent.vo.User>
     **/
    List<User> findUserLikeName(String uName);

    /**
     * 根据用户id查询用户
     * @author luffy
     * @date 下午 01:34 2021/7/10
     * @param uid
     * @return com.talent.vo.User
     **/
    User findUserByUid(Integer uid);

    /**
     * 计数所有用户
     * @return
     */
    Integer count();

    /**
     * 计数所有普通用户
     * @return
     */
    Integer countNormalUser();

}
