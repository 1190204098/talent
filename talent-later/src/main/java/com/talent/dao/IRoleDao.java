package com.talent.dao;

import com.talent.domain.Role;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IRoleDao {

    /**
     * @author luffy
     * 根据aid查角色
     * @date 下午 03:32 2021/6/30
     * @param aid
     * @return java.util.Set<java.lang.Integer>
     **/
    Set<String> findRoleByAid(Integer aid)throws SQLException;

    /**
     * @author luffy
     * 查询所有角色
     * @date 下午 03:33 2021/6/30
     * @return java.util.List<com.talent.vo.Role>
     **/
    List<Role> findAll()throws SQLException;

    /**
     * @author luffy
     * 根据uid查角色
     * @date 下午 03:32 2021/6/30
     * @param uid
     * @return java.util.Set<java.lang.Integer>
     **/
    Set<String> findRoleByUid(Integer uid);
}
