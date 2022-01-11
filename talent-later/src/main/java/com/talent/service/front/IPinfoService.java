package com.talent.service.front;

import com.talent.domain.PersonalInfo;

import java.util.List;

/**
 * @description:  用户个人信息业务层接口
 * @author: luffy
 * @time: 2021/7/12 下午 03:46
 */
public interface IPinfoService {

    /**
     * @author luffy
     * 查询所有信息
     * @date 下午 02:13 2021/7/12
     * @return java.util.List<com.talent.vo.PersonalInfo>
     **/
    List<PersonalInfo> findAll();

    /**
     * @author luffy
     * 根据用户名查询个人信息
     * @date 下午 02:14 2021/7/12
     * @param uName 用户名
     * @return com.talent.vo.PersonalInfo
     **/
    PersonalInfo findInfoByName(String uName);

    /**
     * @author luffy
     * 增加个人信息
     * @date 下午 03:49 2021/7/12
     * @param info 用户提交的表单
     **/
    void addInfo(PersonalInfo info);

    /**
     * @author luffy
     * 为用户增加头像
     * @date 下午 02:16 2021/7/12
     * @param pic 图片二进制码
     * @param uName 用户名
     **/
    void addPic(byte[] pic,String uName);

    /**
     * @author luffy
     * 更新用户信息
     * @date 下午 02:18 2021/7/12
     * @param info 个人信息表单
     * @param uName 用户名
     **/
    void updateInfoByName(PersonalInfo info, String uName);
}
