package com.edgar.module.sys.repository.domain;

import javax.annotation.Generated;

/**
 * SysMenu is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class SysMenu {

    private java.sql.Timestamp createdTime;

    private String icon;

    private Boolean isRoot;

    private Integer menuId;

    private String menuName;

    private String menuPath;

    private String menuType;

    private Integer parentId;

    private String permission;

    private Integer sorted;

    private java.sql.Timestamp updatedTime;

    public java.sql.Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(java.sql.Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public java.sql.Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(java.sql.Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

}

