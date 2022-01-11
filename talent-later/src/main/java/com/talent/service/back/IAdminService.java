package com.talent.service.back;

import com.talent.domain.Admin;
import com.talent.domain.Page;
import com.talent.dto.AdminDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: jmj
 * @time: 2021/6/30 下午 02:23
 */
public interface IAdminService {
    /**
     * @author luffy
     * 登陆时根据账户名称查找账户，若登录成功则更新最后登陆时间
     * @date 下午 02:16 2021/6/30
     * @param aName 账户名称
     * @return com.talent.vo.Admin
     **/
    Admin updateAndFindAdminByaName(String aName)throws SQLException;

    /**
     * @author luffy
     * 通过更新账户信息
     * @date 下午 02:18 2021/6/30
     * @param aName 账户名称
     * @param admin 账户
     * @return void
     **/
    boolean updateAdmin(String aName,Admin admin)throws SQLException;

    /**
     * @author luffy
     * 查询所有管理员用户
     * @date 下午 03:01 2021/6/30
     * @return java.util.List<com.talent.vo.Admin>
     **/
    List<Admin> findAll()throws SQLException;

    /**
     * @author luffy
     * 查询用户权限
     * @date 下午 03:36 2021/6/30
     * @param aName
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> findAuthByAdmin(String aName)throws Exception;

    /**
     * @author luffy
     * 增加管理员及角色
     * @date 下午 03:38 2021/6/30
     * @param admin
     * @param rid
     * @return boolean
     **/
    boolean insert(Admin admin, Set<Integer> rid)throws Exception;

    /**
     * @author luffy
     * 根据账户查找管理员
     * @date 下午 02:00 2021/7/2
     * @param aName
     * @return com.talent.vo.Admin
     **/
    Admin findAdminByaName(String aName)throws SQLException;

    /**
     * @author luffy
     * 根据账户名模糊查询
     * @date 下午 03:54 2021/7/2
     * @param aName
     * @return com.talent.vo.Admin
     **/
    Page<AdminDTO> findAdminLikeaName(String aName,Integer pageNo, Integer pageSize);

    /**
     * @return java.lang.String
     * @author luffy
     * 根据管理员id查询管理员名字
     * @date 下午 07:05 2021/7/4
     **/
    String findaNameByAid(Integer aid);

    /**
     * 管理员分页
     * @param pageNo 第几页
     * @param pageSize 每页大小
     * @return Page<Admin>
     */
    Page<AdminDTO> paginationForAdmin(int pageNo, int pageSize) throws SQLException;
}
