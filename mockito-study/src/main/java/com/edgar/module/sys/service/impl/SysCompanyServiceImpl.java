package com.edgar.module.sys.service.impl;

import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.IDUtils;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.SysCompany;
import com.edgar.module.sys.repository.domain.SysUser;
import com.edgar.module.sys.service.SysCompanyService;
import com.edgar.module.sys.service.SysUserService;
import com.edgar.module.sys.vo.SysCompanyVo;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysCompanyServiceImpl implements SysCompanyService {

    @Autowired
    private BaseDao<Integer, SysCompany> sysCompanyDao;

    @Autowired
    private SysUserService sysUserService;

    @Override
    @Transactional
    public void save(SysCompanyVo sysCompanyVo) {
        SysCompany sysCompany = new SysCompany();
        sysCompany.setCompanyId(IDUtils.getNextId());
        sysCompany.setIsDel(false);
        sysCompany.setCompanyCode(sysCompanyVo.getCompanyCode());
        sysCompany.setCompanyName(sysCompanyVo.getCompanyName());
        sysCompanyDao.insert(sysCompany);

        SysUser sysUser = new SysUser();
        sysUser.setUsername(sysCompanyVo.getUsername());
        sysUser.setEmail(sysCompanyVo.getEmail());
        sysUser.setFullName(sysCompanyVo.getFullName());
        sysUser.setPassword(sysCompanyVo.getPassword());

        sysUserService.saveAdminUser(sysUser);
    }

    @Override
    public Pagination<SysCompany> pagination(QueryExample example, int page,
                                             int pageSize) {
        example.equalsTo("isDel", 0);
        return sysCompanyDao.pagination(example, page, pageSize);
    }

    @Override
    public void delete(int companyId) {
        SysCompany company = new SysCompany();
        company.setCompanyId(companyId);
        company.setIsDel(true);
        sysCompanyDao.update(company);
    }

    @Override
    public boolean checkCode(String code) {
        Validate.notNull(code);
        Validate.notBlank(code);
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("companyCode", code);

        List<SysCompany> sysCompanies = sysCompanyDao.query(example);
        return sysCompanies.isEmpty();
    }

}
