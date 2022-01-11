package com.talent.service.back.impl;

import com.talent.dao.IActionDao;
import com.talent.dao.IAdminDao;
import com.talent.dao.IRoleDao;
import com.talent.dto.AdminDTO;
import com.talent.service.back.IAdminService;
import com.talent.utils.MD5Code;
import com.talent.domain.Admin;
import com.talent.domain.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @description:管理员业务层
 * @author: jmj
 * @time: 2021/6/30 下午 02:23
 */
@Service("adminService")
@Slf4j
public class AdminServiceImpl implements IAdminService {

    @Resource
    private IAdminDao adminDao;
    @Resource
    private IRoleDao roleDao;
    @Resource
    private IActionDao actionDao;

    /**
     * 登陆时根据账户名称查找账户，若登录成功则更新最后登陆时间
     * @author jmj
     * @since 19:39 2021/12/5
     * @param aName
     * @return com.talent.vo.Admin
     **/
    @Override
    public Admin updateAndFindAdminByaName(String aName) throws SQLException {
        log.info("业务层 : updateAndFindAdminByaName() called with parameters => [aName = {}]", aName);
        log.info("adminDao : findAdminByaName() called with parameters => [aName = {}]",aName);
        Admin admin = adminDao.findAdminByaName(aName);
        if(admin!=null){
            admin.setDate(new Timestamp(System.currentTimeMillis()));
            log.info("adminDao : updateAdmin() called with parameters => [aName = {},admin]",aName);
            adminDao.updateAdmin(aName, admin);
        }
        return admin;
    }

    /**
     * 通过更新账户信息
     * @author jmj
     * @since 19:39 2021/12/5
     * @param aName
     * @param admin
     * @return boolean
     **/
    @Override
    public boolean updateAdmin(String aName, Admin admin) throws SQLException {
        log.info("业务层 : updateAdmin() called with parameters => [aName = {}], [admin]", aName);
        log.info("adminDao : updateAdmin() called with parameters => [aName = {},admin]",aName);
        return adminDao.updateAdmin(aName,admin);
    }

    /**
     * 查询所有管理员用户
     * @author jmj
     * @since 19:39 2021/12/5
     * @return java.util.List<com.talent.vo.Admin>
     **/
    @Override
    public List<Admin> findAll() throws SQLException {
        log.info("业务层 : findAll() called with parameters");
        log.info("adminDao : findAll() called with no parameters");
        return adminDao.findAll();
    }

    /**
     * 查询用户权限
     * @author jmj
     * @since 19:40 2021/12/5
     * @param aName
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @Override
    public Map<String, Object> findAuthByAdmin(String aName) throws Exception {
        log.info("业务层 : findAuthByAdmin() called with parameters => [aName = {}]", aName);
        Map<String, Object> map = new HashMap<String, Object>();
        log.info("adminDao : findAdminByaName() called with parameters => [aName = {}]",aName);
        Integer aid = adminDao.findAdminByaName(aName).getAid();
        log.info("roleDao : findRoleByAid() called with parameters => [aid = {}]",aid);
        map.put("allRoles",this.roleDao.findRoleByAid(aid));
        log.info("actionDao : findActionByAid() called with parameters => [aid = {}]",aid);
        map.put("allActions",this.actionDao.findActionByAid(aid));
        return map;
    }

    /**
     * 增加管理员及角色
     * @author jmj
     * @since 19:40 2021/12/5
     * @param admin
     * @param rid
     * @return boolean
     **/
    @Override
    public boolean insert(Admin admin, Set<Integer> rid) throws Exception {
        log.info("业务层 : insert() called with parameters => [admin = {}], [rid = {}]", admin, rid);
        log.info("adminDao : findAdminByaName() called with parameters => [aName = {}]",admin.getAName());
        if(this.adminDao.findAdminByaName(admin.getAName()) == null){
            admin.setPrivilege(1);
            admin.setLocked(0);
            admin.setPassword(new MD5Code().getMD5ofStr("{{[[" + admin.getPassword() + "]]}}"));
            log.info("adminDao : addAdmin() called with parameters => [admin]");
            if(this.adminDao.addAdmin(admin)){
                log.info("adminDao : findAdminByaName() called with parameters => [aName = {}]",admin.getAName());
                Integer aid = adminDao.findAdminByaName(admin.getAName()).getAid();
                for (Integer integer : rid) {
                    Map<String, Object> map = new HashMap<>(2);
                    map.put("aid", aid);
                    map.put("rid", integer);
                    log.info("adminDao : createAdminAndRole() called with parameters => [adminAndRole = {}]",map);
                    this.adminDao.createAdminAndRole(map);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 根据账户查找管理员
     * @author jmj
     * @since 19:40 2021/12/5
     * @param aName
     * @return com.talent.vo.Admin
     **/
    @Override
    public Admin findAdminByaName(String aName)throws SQLException{
        log.info("业务层 : findAdminByaName() called with parameters => [aName = {}]", aName);
        log.info("adminDao : findAdminByaName() called with parameters => [aName = {}]",aName);
        return adminDao.findAdminByaName(aName);
    }

    /**
     * 根据账户名模糊查询,并分页
     * @author luffy
     * @date 下午 03:54 2021/7/2
     * @param aName
     * @return java.util.List<com.talent.vo.Admin>
     **/
    @Override
    public Page<AdminDTO> findAdminLikeaName(String aName, Integer pageNo, Integer pageSize){
        log.info("业务层 : findAdminLikeaName() called with parameters => [aName = {}]", aName);
        Page<AdminDTO> page = new Page<>();
        log.info("adminDao : findAdminLikeaNameCountNum() called with parameters => [aName = {}]",aName);
        Integer total = adminDao.findAdminLikeaNameCountNum(aName);
        if (total == 0) {
            return page;
        }
        page.setTotal(total);
        if ((pageNo - 1) * pageSize <= total) {
            log.info("adminDao : findAdminLikeaName() called with parameters => [aName = {},startNo = {},pageSize= {}]",aName,(pageNo-1)*pageSize,pageSize);
            page.setRecords(adminDao.findAdminLikeaName(aName,(pageNo-1)*pageSize,pageSize));
            page.setCurrent(pageNo);
            page.setSize(pageSize);
        }
        return page;
    }

    /**
     * 根据管理员id查询管理员名字
     * @author jmj
     * @since 19:40 2021/12/5
     * @param aid
     * @return java.lang.String
     **/
    @Override
    public String findaNameByAid(Integer aid) {
        log.info("业务层 : findaNameByAid() called with parameters => [aid = {}]", aid);
        log.info("adminDao : findaNameByAid() called with parameters => [aid = {}]",aid);
        return adminDao.findaNameByAid(aid);
    }

    /**
     * 管理员表分页
     * @author luffy
     * @date 下午 07:38 2021/12/5
     * @param pageNo
     * @param pageSize
     **/
    @Override
    public Page<AdminDTO> paginationForAdmin(int pageNo, int pageSize) {
        log.info("业务层 : paginationForAdmin() called with parameters => [pageNo = {}], [pageSize = {}]", pageNo, pageSize);
        Page<AdminDTO> page = new Page<>();
        log.info("adminDao : count() called with no parameters");
        Integer total = adminDao.count();
        if (total == 0) {
            log.info("数据库无数据");
            return page;
        }
        page.setTotal(total);
        if ((pageNo - 1) * pageSize <= total) {
            log.info("adminDao : findAndPage() called with parameters => [startNo = {},pageSize= {}]",(pageNo-1)*pageSize,pageSize);
            page.setRecords(adminDao.findAndPage((pageNo - 1) * pageSize, pageSize));
            page.setCurrent(pageNo);
            page.setSize(pageSize);
        } else {
            log.info("查询超出范围 - startNo:{},total:{}",(pageNo - 1) * pageSize,total);
        }
        return page;
    }
}
