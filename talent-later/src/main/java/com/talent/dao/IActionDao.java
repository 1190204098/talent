package com.talent.dao;

import java.sql.SQLException;
import java.util.Set;

public interface IActionDao {

    /**
     * 根据aid取得权限信息
     * @author luffy
     * @date 下午 03:47 2021/6/30
     * @param aid
     * @return java.util.Set<java.lang.Integer>
     **/
    Set<String> findActionByAid(Integer aid)throws SQLException;

    /**
     * 根据uid获取权限信息
     * @author jmj
     * @since 21:16 2021/12/8
     * @param uid 用户id
     * @return java.util.Set<java.lang.String>
     **/
    Set<String> findActionByUid(Integer uid);
}
