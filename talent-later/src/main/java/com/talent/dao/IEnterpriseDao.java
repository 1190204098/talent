package com.talent.dao;

import com.talent.domain.Enterprise;
import com.talent.dto.EnterpriseDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jmj
 */
public interface IEnterpriseDao {
    Integer count();

    Integer countLikeEname(String ename);

    Integer countWait4Review();

    /**
     * 查询所有并分页
     * @author jmj
     * @since 20:18 2021/12/10
     * @param startNo
     * @param pageSize
     * @return java.util.List<com.talent.domain.Enterprise>
     **/
    List<EnterpriseDTO> findAndPaginationAll(@Param("startNo") Integer startNo,@Param("pageSize") Integer pageSize);

    /**
     * 根据uid查询
     * @author jmj
     * @since 20:01 2021/12/10
     * @param uid
     * @return java.util.List<com.talent.domain.Enterprise>
     **/
    List<Enterprise> findEnterpriseByUid(Integer uid);

    /**
     * 根据企业名称模糊查询并分页
     * @author jmj
     * @since 20:18 2021/12/10
     * @param ename
     * @param startNo
     * @param pageSize
     * @return java.util.List<com.talent.domain.Enterprise>
     **/
    List<EnterpriseDTO> findAndPaginationEnterpriseLikeEname(@Param("ename") String ename,@Param("startNo") Integer startNo,@Param("pageSize") Integer pageSize);

    /**
     * 添加企业信息
     * @author jmj
     * @since 20:01 2021/12/10
     * @param enterprise
     * @return boolean
     **/
    boolean addEnterprise(Enterprise enterprise);

    /**
     * 更新企业用户信息
     * @author jmj
     * @since 20:09 2021/12/10
     * @param enterprise
     * @return boolean
     **/
    boolean updateEnterpriseByUid(Enterprise enterprise);

    /**
     * 查询所有待审核的企业信息
     * @author jmj
     * @since 20:44 2021/12/10
     * @return java.util.List<com.talent.domain.Enterprise>
     **/
    List<EnterpriseDTO> findAndPageAllWait4ReviewEnterprises(@Param("startNo")Integer startNo,@Param("pageSize")Integer pageSize);

    /**
     * 根据企业名称查询企业信息
     * @author jmj
     * @since 21:00 2021/12/10
     * @return com.talent.domain.Enterprise
     **/
    Enterprise findEnterpriseByEname(String ename);

}
