package com.edgar.module.sys.repository.domain;

import javax.annotation.Generated;

/**
 * SysUserProfile is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SysUserProfile {

    private String language;

    private Integer profileId;

    private Integer userId;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}

