package com.edgar.module.sys.service;

import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.SysCompany;
import com.edgar.module.sys.vo.SysCompanyVo;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SysCompanyService {

    /**
     * 新增公司
     *
     * @param sysCompanyVo 公司
     */
    void save(SysCompanyVo sysCompanyVo);

    /**
     * 分页查询公司
     *
     * @param example  查询条件
     * @param page     页码
     * @param pageSize 每页显示数量
     * @return 公司的分页类
     */
    Pagination<SysCompany> pagination(QueryExample example, int page,
                                      int pageSize);

    /**
     * 根据公司ID和时间戳查询公司
     *
     * @param companyId 公司ID
     */
    void delete(int companyId);

    /**
     * 检查公司编码是否存在
     *
     * @param code 编码
     * @return 如果存在，返回false
     */
    boolean checkCode(String code);

}
