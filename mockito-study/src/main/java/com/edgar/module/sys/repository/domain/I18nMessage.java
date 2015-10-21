package com.edgar.module.sys.repository.domain;

import javax.annotation.Generated;

/**
 * I18nMessage is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class I18nMessage {

    private java.sql.Timestamp createdTime;

    private Integer i18nId;

    private String i18nKey;

    private String i18nValueEn;

    private String i18nValueZhCn;

    private String i18nValueZhTw;

    private Boolean isRoot;

    private java.sql.Timestamp updatedTime;

    public java.sql.Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(java.sql.Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getI18nId() {
        return i18nId;
    }

    public void setI18nId(Integer i18nId) {
        this.i18nId = i18nId;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public String getI18nValueEn() {
        return i18nValueEn;
    }

    public void setI18nValueEn(String i18nValueEn) {
        this.i18nValueEn = i18nValueEn;
    }

    public String getI18nValueZhCn() {
        return i18nValueZhCn;
    }

    public void setI18nValueZhCn(String i18nValueZhCn) {
        this.i18nValueZhCn = i18nValueZhCn;
    }

    public String getI18nValueZhTw() {
        return i18nValueZhTw;
    }

    public void setI18nValueZhTw(String i18nValueZhTw) {
        this.i18nValueZhTw = i18nValueZhTw;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }

    public java.sql.Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(java.sql.Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

}

