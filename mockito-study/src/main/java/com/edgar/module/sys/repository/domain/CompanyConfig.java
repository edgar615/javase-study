package com.edgar.module.sys.repository.domain;

import javax.annotation.Generated;

/**
 * CompanyConfig is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class CompanyConfig {

    private Integer companyId;

    private Integer configId;

    private String configKey;

    private String configValue;

    private java.sql.Timestamp createdTime;

    private String description;

    private java.sql.Timestamp updatedTime;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public java.sql.Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(java.sql.Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(java.sql.Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

}

