package com.talent.service.front.impl;

import com.talent.dao.IResumeDao;
import com.talent.dao.IUserDao;
import com.talent.domain.Page;
import com.talent.model.ResumeSearchParamModel;
import com.talent.service.front.IResumeService;
import com.talent.domain.Resume;
import com.talent.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 简历编辑业务层实现类
 * @author: luffy
 * @time: 2021/8/7 下午 01:20
 */
@Service("resumeService")
@Slf4j
public class ResumeServiceImpl implements IResumeService {

    @Resource
    private IResumeDao resumeDao;

    @Resource
    private IUserDao userDao;

    @Override
    public Resume findResumeByuName(String uName) {
        log.info("业务层 : findResumeByuName() called with parameters => [uName = {}]", uName);
        log.info("Dao : findUserByName() called with parameters => [uName = {}]",uName);
        User user = userDao.findUserByName(uName);
        log.info("Dao : findResumeByUid() called with parameters => [uid = {}]",user.getUid());
        return resumeDao.findResumeByUid(user.getUid());
    }

    @Override
    public void addResume(Resume resume, String uName) {
        log.info("业务层 : addResume() called with parameters => [resume], [uName = {}]", uName);
        log.info("Dao : findUserByName() called with parameters => [uName = {}]",uName);
        User user = userDao.findUserByName(uName);
        log.info("Dao : addResume() called with parameters => [resume = {},uid = {}]",resume,user.getUid());
        resumeDao.addResume(resume,user.getUid());
    }

    @Override
    public void updateResume(Resume resume, String uName) {
        log.info("业务层 : updateResume() called with parameters => [resume], [uName = {}]", uName);
        log.info("Dao : findUserByName() called with parameters => [uName = {}]",uName);
        User user = userDao.findUserByName(uName);
        log.info("Dao : updateResume() called with parameters => [resume = {},uid = {}]",resume,user.getUid());
        resumeDao.updateResume(resume, user.getUid());
    }

    @Override
    public void addPic(byte[] pic, String uName) {
        log.info("业务层 : addPic() called with parameters => [pic], [uName = {}]", uName);
        log.info("Dao : findUserByName() called with parameters => [uName = {}]",uName);
        User user = userDao.findUserByName(uName);
        log.info("Dao : updateResume() called with parameters => [pic!=null : {},uid = {}]",pic!=null,user.getUid());
        resumeDao.addPic(pic, user.getUid());
    }

    @Override
    public Page<Resume> fuzzySearch(ResumeSearchParamModel resumeSearchParamModel, int pageNo, int pageSize) {
        log.info("fuzzySearch() called with parameters => [resumeSearchParamModel = {}],[pageNo = {}],[pageSize = {}]", resumeSearchParamModel, pageNo, pageSize);
        Page<Resume> page = new Page<>();
        int total = resumeDao.countFuzzySearch(resumeSearchParamModel);
        if (total == 0) {
            log.info("数据库无数据");
        }
        if (total < (pageNo - 1) * pageSize) {
            log.info("超出界限,startNo:{},total:{}",(pageNo - 1) * pageSize,total);
            return page;
        }
        List<Resume> resumes = resumeDao.fuzzySearch(resumeSearchParamModel, (pageNo - 1) * pageSize, pageSize);
        page.setRecords(resumes);
        page.setSize(pageSize);
        page.setCurrent(pageNo);
        page.setTotal(total);
        return page;
    }
}
