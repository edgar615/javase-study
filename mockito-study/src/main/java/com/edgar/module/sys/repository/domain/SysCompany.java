package com.edgar.module.sys.repository.domain;

import javax.annotation.Generated;

/**
 * SysCompany is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SysCompany {

    private String companyCode;

    private Integer companyId;

    private String companyName;

    private Boolean isDel;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

}

