package com.talent.dao;

import com.talent.domain.Admin;
import com.talent.dto.AdminDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IAdminDao {

    /**
     *
     * 根据账户名称查找
     * @author luffy
     * @date 下午 02:16 2021/6/30
     * @param aName 账户名称
     * @return com.talent.vo.Admin
     **/
    Admin findAdminByaName(String aName)throws SQLException;

    /**
     * @author luffy
     * 通过名称修改信息
     * @date 下午 02:18 2021/6/30
     * @param aName 账户名称
     * @param admin 账户
     * @return void
     **/
    boolean updateAdmin(@Param("name") String aName,@Param("admin") Admin admin)throws SQLException;

    /**
     * @author luffy
     * 查询所有管理员用户
     * @date 下午 03:01 2021/6/30
     * @return java.util.List<com.talent.vo.Admin>
     **/
    List<Admin> findAll()throws SQLException;

    /**
     * @author luffy
     * 增加管理员
     * @date 下午 02:20 2021/6/30
     * @param admin
     * @return void
     **/
    boolean addAdmin(@Param("admin") Admin admin)throws SQLException;

    /**
     * @author luffy
     * 创建管理员角色
     * @date 下午 03:03 2021/6/30
     * @param params
     * @return boolean
     **/
    boolean createAdminAndRole(Map<String,Object> params)throws SQLException;

    /**
     * @author luffy
     * 根据账户名称查询权限最高的角色
     * @date 下午 08:46 2021/7/1
     * @param aName
     * @return java.lang.String
     **/
    String findRoleByaName(String aName);

    /**
     * @author luffy
     * 根据账户名模糊查询
     * @date 下午 03:54 2021/7/2
     * @param aName
     * @return com.talent.vo.Admin
     **/
    Integer findAdminLikeaNameCountNum(String aName);

    /**
     * 分页迷糊查询
     * @author luffy
     * @date 下午 08:03 2021/12/5
     * @param aName
     * @param
     * @param pageSize
     **/
    List<AdminDTO> findAdminLikeaName(@Param("aName")String aName, @Param("startNo")Integer startNo,@Param("pageSize") Integer pageSize);

    /**
     * @return java.lang.String
     * @author luffy
     * 根据管理员id查询管理员名字
     * @date 下午 07:05 2021/7/4
     **/
    String findaNameByAid(Integer aid);

    /**
     * 获取管理员页记录总数
     * @return
     */
    Integer count();

    /**
     * 分页查找
     * @param startPage 开始条数
     * @param pageSize 每页大小
     * @return Admin集合
     */
    List<AdminDTO> findAndPage(@Param("startPage") int startPage, @Param("pageSize") int pageSize);
}
