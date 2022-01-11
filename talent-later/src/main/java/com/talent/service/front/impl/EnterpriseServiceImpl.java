package com.talent.service.front.impl;

import com.talent.dao.IEnterpriseDao;
import com.talent.dao.IUserDao;
import com.talent.domain.Enterprise;
import com.talent.domain.Message;
import com.talent.domain.Page;
import com.talent.domain.User;
import com.talent.dto.EnterpriseDTO;
import com.talent.service.front.IEnterpriseService;
import com.talent.service.front.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 企业服务层
 * @author: luffy
 * @time: 2021/12/10 下午 09:10
 */
@Slf4j
@Service
public class EnterpriseServiceImpl implements IEnterpriseService {

    @Resource
    private IEnterpriseDao enterpriseDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private IMessageService messageService;

    @Override
    public List<Enterprise> findEnterpriseByUname(String uName) {
        log.info("业务层 : findEnterpriseByUname() called with parameters => [uName = {}]",uName);
        if (uName==null){
            log.info("用户名为空");
            return null;
        }
        User user = userDao.findUserByName(uName);
        if (user != null) {
            log.info("根据用户名[{}]查询企业",uName);
            return enterpriseDao.findEnterpriseByUid(user.getUid());
        }else  {
            log.info("用户[{}]不存在",uName);
            return null;
        }
    }

    @Override
    public Enterprise findEnterpriseByEname(String eName) {
        log.info("findEnterpriseByEname() called with parameters => [eName = {}]",eName);
        if (eName == null) {
            log.info("企业名为空");
            return null;
        }
        return enterpriseDao.findEnterpriseByEname(eName);
    }

    @Override
    public Boolean updateEnterprise(Enterprise enterprise) {
        log.info("业务层 : updateEnterprise() called with parameters => [enterprise = {}]",enterprise);
        if (enterprise==null){
            log.info("对象为空");
            return null;
        }
        if(enterprise.getUid()!=null) {
            enterpriseDao.updateEnterpriseByUid(enterprise);
            log.info("企业信息修改成功");
        }else {
            log.info("用户id为空，企业信息修改失败");
            return false;
        }
        return true;
    }

    @Override
    public Boolean addEnterprise(Enterprise enterprise) {
        log.info("业务层 : addEnterprise() called with parameters => [enterprise = {}]",enterprise);
        if (enterprise==null){
            log.info("对象为空");
            return null;
        }
        if (enterprise.getUid() != null) {
            enterpriseDao.addEnterprise(enterprise);
            log.info("企业增加成功");
        }else {
            log.info("用户id为空，企业增加失败");
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateAndCertifyEnterprise(String eName){
        log.info("业务层 : checkEnterprise() called with parameters => [eName = {}]",eName);
        //设置企业已通过认证
        Enterprise enterprise = enterpriseDao.findEnterpriseByEname(eName);
        enterprise.setStatus(1);
        enterpriseDao.updateEnterpriseByUid(enterprise);
        //标记用户为企业用户
        User user = userDao.findUserByUid(enterprise.getUid());
        user.setFlag(1);
        userDao.updateUser(user);
        //发送认证通过消息
        Message message = new Message();
        message.setUid(user.getUid());
        message.setMname("审核通知");
        message.setContext("企业用户审核通过！");
        message.setTime(new Date());
        messageService.addMessage(message);
        log.info("发送 ename:[{}]认证通过消息",enterprise.getEname());
        //为用户添加企业角色，3为企业用户
        userDao.createUserRole(user.getUid(),3);
        log.info("企业[{}]认证成功",eName);
        return true;
    }

    @Override
    public Page<EnterpriseDTO> paginationForUncheckedEnterprise(int pageNo, int pageSize) {
        log.info("业务层 : paginationForUncheckedEnterprise() called with parameters => [pageNo = {}], [pageSize = {}]",pageNo, pageSize);
        Page<EnterpriseDTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Integer totalPage = enterpriseDao.countWait4Review();
        if (totalPage == 0){
            log.info("没有未审核的企业");
            return page;
        }
        if ((pageNo - 1) * pageSize <= totalPage) {
            log.info("查询分页成功");
            page.setRecords(enterpriseDao.findAndPageAllWait4ReviewEnterprises((pageNo - 1) * pageSize, pageSize));
        } else {
            log.info("查询超出范围 - startNo:{},total:{}",(pageNo - 1) * pageSize,totalPage);
        }
        return page;
    }

    @Override
    public Page<EnterpriseDTO> findAndPaginationAllEnterprise(int pageNo, int pageSize) {
        log.info("业务层 : findAndPaginationAllEnterprise() called with parameters => [pageNo = {}], [pageSize = {}]",pageNo, pageSize);
        Page<EnterpriseDTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Integer totalPage = enterpriseDao.count();
        if (totalPage == 0){
            log.info("没有查到企业");
            return null;
        }
        if ((pageNo - 1) * pageSize <= totalPage) {
            log.info("查询分页成功");
            page.setRecords(enterpriseDao.findAndPaginationAll((pageNo - 1) * pageSize, pageSize));
        } else {
            log.info("查询超出范围 - startNo:{},total:{}",(pageNo - 1) * pageSize,totalPage);
        }
        return page;
    }

    @Override
    public Page<EnterpriseDTO> findAndPaginationEnterpriseLikeEname(String eName, int pageNo, int pageSize) {
        log.info("业务层 : findAndPaginationEnterpriseLikeEname() called with parameters => [eName = {}], [pageNo = {}], [pageSize = {}]",eName, pageNo, pageSize);
        Page<EnterpriseDTO> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        Integer totalPage = enterpriseDao.countLikeEname(eName);
        if (totalPage == 0){
            log.info("没有查到企业");
            return null;
        }
        if ((pageNo - 1) * pageSize <= totalPage) {
            log.info("查询分页成功");
            page.setRecords(enterpriseDao.findAndPaginationEnterpriseLikeEname(eName,(pageNo - 1) * pageSize, pageSize));
        } else {
            log.info("查询超出范围 - startNo:{},total:{}",(pageNo - 1) * pageSize,totalPage);
        }
        return page;
    }
}
