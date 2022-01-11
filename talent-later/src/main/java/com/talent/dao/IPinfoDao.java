package com.talent.dao;

import com.talent.domain.PersonalInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 用户信息持久层
 * @author: luffy
 * @time: 2021/7/12 下午 02:12
 */
public interface IPinfoDao {

    /**
     * @author luffy
     * 查询所有信息
     * @date 下午 02:13 2021/7/12
     * @return java.util.List<com.talent.vo.PersonalInfo>
     **/
    List<PersonalInfo> findAll();

    /**
     * @author luffy
     * 根据用户id查询个人信息
     * @date 下午 02:14 2021/7/12
     * @param uid 用户id
     * @return com.talent.vo.PersonalInfo
     **/
    PersonalInfo findInfoByUid(Integer uid);

    /**
     * @author luffy
     * 增加个人信息记录
     * @date 下午 02:15 2021/7/12
     * @param info 个人信息表单
     **/
    void addInfo(@Param("info") PersonalInfo info);

    /**
     * @author luffy
     * 为用户增加头像
     * @date 下午 02:16 2021/7/12
     * @param pic 图片二进制码
     * @param uid 用户id
     **/
    void addPic(@Param("pic") byte[] pic,@Param("uid") Integer uid);

    /**
     * @author luffy
     * 更新用户信息
     * @date 下午 02:18 2021/7/12
     * @param info 个人信息表单
     * @param uid 用户id
     **/
    void updateInfoByUid(@Param("info") PersonalInfo info,@Param("uid") Integer uid);
}
