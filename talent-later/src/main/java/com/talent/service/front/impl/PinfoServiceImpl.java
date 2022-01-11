package com.talent.service.front.impl;

import com.talent.dao.IPinfoDao;
import com.talent.dao.IUserDao;
import com.talent.service.front.IPinfoService;
import com.talent.domain.PersonalInfo;
import com.talent.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 用户个人信息业务层实现类
 * @author: luffy
 * @time: 2021/7/12 下午 03:52
 */
@Service("pInfoService")
@Slf4j
public class PinfoServiceImpl implements IPinfoService {

    @Resource
    private IPinfoDao pinfoDao;

    @Resource
    private IUserDao userDao;

    /**
     * @author luffy
     * 查询所有信息
     * @date 下午 02:13 2021/7/12
     * @return java.util.List<com.talent.vo.PersonalInfo>
     **/
    @Override
    public List<PersonalInfo> findAll() {
        log.info("业务层 : findAll() called with no parameters");
        log.info("pinfoDao : findAll() called with no parameters");
        return pinfoDao.findAll();
    }

    /**
     * @author luffy
     * 根据用户名查询个人信息
     * @date 下午 02:14 2021/7/12
     * @param uName 用户名
     * @return com.talent.vo.PersonalInfo
     **/
    @Override
    public PersonalInfo findInfoByName(String uName) {
        log.info("业务层 : findInfoByName() called with parameters => [uName = {}]", uName);
        log.info("userDao : findUserByName() called with parameters => [uName = {}]",uName);
        User user = userDao.findUserByName(uName);
        log.info("pinfoDao : findInfoByUid() called with parameters => [uid = {}]",user.getUid());
        return pinfoDao.findInfoByUid(user.getUid());
    }

    /**
     * @author luffy
     * 增加个人信息
     * @date 下午 03:49 2021/7/12
     * @param info 用户提交的表单
     **/
    @Override
    public void addInfo(PersonalInfo info) {
        log.info("业务层 : addInfo() called with parameters => [info]");
        log.info("pinfoDao : addInfo() called with parameters => [info = {}]",info);
        pinfoDao.addInfo(info);
    }

    /**
     * @author luffy
     * 为用户增加头像
     * @date 下午 02:16 2021/7/12
     * @param pic 图片二进制码
     * @param uName 用户名
     **/
    @Override
    public void addPic(byte[] pic, String uName) {
        log.info("业务层 : addPic() called with parameters => [pic], [uName = {}]", uName);
        log.info("userDao : findUserByName() called with parameters => [uName = {}]",uName);
        User user = userDao.findUserByName(uName);
        log.info("pinfoDao : addPic() called with parameters => [pic,uid = {}]", user.getUid());
        pinfoDao.addPic(pic,user.getUid());
    }

    /**
     * @author luffy
     * 更新用户信息
     * @date 下午 02:18 2021/7/12
     * @param info 个人信息表单
     * @param uName 用户名
     **/
    @Override
    public void updateInfoByName(PersonalInfo info, String uName) {
        log.info("业务层 : updateInfoByName() called with parameters => [info], [uName = {}]", uName);
        log.info("userDao : findUserByName() called with parameters => [uName = {}]",uName);
        User user = userDao.findUserByName(uName);
        log.info("pinfoDao : updateInfoByUid() called with parameters => [info = {},uid = {}]", info, user.getUid());
        pinfoDao.updateInfoByUid(info,user.getUid());
    }
}
