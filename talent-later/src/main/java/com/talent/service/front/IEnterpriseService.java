package com.talent.service.front;

import com.talent.domain.Enterprise;
import com.talent.domain.Page;
import com.talent.dto.EnterpriseDTO;

import java.util.List;

/**
 * 企业业务类
 * @author: luffy
 * @time: 2021/12/10 下午 08:09
 */
public interface IEnterpriseService {

    /**
     * 根据用户名查询企业
     * @author luffy
     * @date 下午 09:21 2021/12/10
     * @param uName
     * @return java.util.List<com.talent.domain.Enterprise>
     **/
    List<Enterprise> findEnterpriseByUname(String uName);

    /**
     * 根据企业名查找企业
     * @author luffy
     * @date 下午 11:56 2021/12/15
     * @param eName
     * @return com.talent.domain.Enterprise
     **/
    Enterprise findEnterpriseByEname(String eName);

    Boolean updateEnterprise(Enterprise enterprise);

    Boolean addEnterprise(Enterprise enterprise);

    /**
     * 企业认证通过并为用户增加角色
     * @author luffy
     * @date 上午 12:32 2021/12/13
     * @param eName
     * @return java.lang.Boolean
     **/
    Boolean updateAndCertifyEnterprise(String eName);

    /**
     * 分页查询所有未审核的企业
     * @author luffy
     * @date 下午 09:21 2021/12/10
     * @param pageNo
     * @param pageSize
     * @return com.talent.domain.Page<com.talent.dto.EnterpriseDTO>
     **/
    Page<EnterpriseDTO> paginationForUncheckedEnterprise(int pageNo, int pageSize);

    /**
     * 查询所有企业并分页
     * @author luffy
     * @date 下午 09:21 2021/12/10
     * @param pageNo
     * @param pageSize
     * @return com.talent.domain.Page<com.talent.dto.EnterpriseDTO>
     **/
    Page<EnterpriseDTO> findAndPaginationAllEnterprise(int pageNo, int pageSize);

    /**
     * 模糊查询企业并分页
     * @author luffy
     * @date 下午 09:22 2021/12/10
     * @param eName
     * @param pageNo
     * @param pageSize
     * @return com.talent.domain.Page<com.talent.dto.EnterpriseDTO>
     **/
    Page<EnterpriseDTO> findAndPaginationEnterpriseLikeEname(String eName,int pageNo, int pageSize);

}
