package com.talent.service.front;

import com.talent.domain.Page;
import com.talent.domain.Resume;
import com.talent.model.ResumeSearchParamModel;

/**
 * @description: 简历编辑业务层接口
 * @author: luffy
 * @time: 2021/8/7 下午 01:20
 */
public interface IResumeService {

    /**
     * 根据用户名查询简历
     * @author luffy
     * @date 下午 01:23 2021/8/7
     * @param uName
     * @return com.talent.vo.Resume
     **/
    Resume findResumeByuName(String uName);

    /**
     * 增加简历
     * @author luffy
     * @date 下午 01:24 2021/8/7
     * @param resume
     * @param uName
     * @return void
     **/
    void addResume(Resume resume,String uName);

    /**
     * 更新简历
     * @author luffy
     * @date 下午 01:25 2021/8/7
     * @param resume
     * @param uName
     * @return void
     **/
    void updateResume(Resume resume,String uName);

    /**
     * 增加附件
     * @author luffy
     * @date 下午 01:25 2021/8/7
     * @param pic
     * @param uName
     * @return void
     **/
    void addPic(byte[] pic,String uName);

    /**
     * 模糊搜索简历
     * @author jmj
     * @since 0:41 2021/12/17
     * @param resumeSearchParamModel
     * @param pageNo
     * @param pageSize
     * @return com.talent.domain.Page<com.talent.domain.Resume>
     **/
    Page<Resume> fuzzySearch(ResumeSearchParamModel resumeSearchParamModel,int pageNo,int pageSize);
}
