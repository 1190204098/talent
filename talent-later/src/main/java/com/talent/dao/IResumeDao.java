package com.talent.dao;

import com.talent.domain.Resume;
import com.talent.model.ResumeSearchParamModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 简历持久层
 * @author: luffy
 * @time: 2021/7/20 下午 03:00
 */
public interface IResumeDao {

    /**
     * 根据用户id查询简历
     * @author luffy
     * @date 下午 03:01 2021/7/20
     * @param uid 用户id
     * @return com.talent.vo.Resume
     **/
    Resume findResumeByUid(Integer uid);

    /**
     * 增加简历
     * @author luffy
     * @date 下午 03:13 2021/7/20
     * @param resume
     * @param uid
     * @return void
     **/
    void addResume(@Param("resume") Resume resume,@Param("uid") Integer uid);

    /**
     * 更新简历
     * @author luffy
     * @date 下午 03:36 2021/7/20
     * @param resume
     * @param uid
     **/
    void updateResume(@Param("resume") Resume resume,@Param("uid") Integer uid);

    /**
     * 增加附件
     * @author luffy
     * @date 下午 03:44 2021/7/20
     * @param pic
     * @param uid
     **/
    void addPic(@Param("pic") byte[] pic,@Param("uid") Integer uid);

    /**
     * 模糊搜索, 根据姓名、工作地点、职业、特长
     * @author jmj
     * @since 0:06 2021/12/17
     * @param resumeSearchParamModel
     * @param startNo
     * @param pageSize
     * @return java.util.List<com.talent.domain.Resume>
     **/
    List<Resume> fuzzySearch(@Param("searchParam") ResumeSearchParamModel resumeSearchParamModel,@Param("startNo") int startNo,@Param("pageSize") int pageSize);

    /**
     * fuzzySearch()的前置count()
     * @author jmj
     * @since 1:09 2021/12/17
     * @param resumeSearchParamModel
     * @return int
     **/
    int countFuzzySearch(@Param("searchParam") ResumeSearchParamModel resumeSearchParamModel);
}
