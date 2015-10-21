package com.edgar.module.sys.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * AnagularJS需要的路由对象
 *
 * @author Edgar Zhang
 * @version 1.0
 */
public class AngularRoute implements Comparable<AngularRoute> {
    private String id;
    private String url;
    private String basename;
    private String path;
    private String name;
    private boolean isMenu;
    private int sorted = 1;
    private String parentId;

    /**
     * 设计路由的地址
     *
     * @param url 地址
     */
    public void setUrl(String url) {
        this.url = url;
        String newUrl = StringUtils.substringBefore(url, "/:");
        this.basename = StringUtils.substringAfterLast(newUrl, "/");
        this.path = StringUtils.substringBeforeLast(newUrl, "/");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMenu() {
        return isMenu;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    public int getSorted() {
        return sorted;
    }

    public void setSorted(int sorted) {
        this.sorted = sorted;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public int compareTo(AngularRoute o) {
        if (sorted == o.getSorted()) {
            return 0;
        }
        if (sorted > o.getSorted()) {
            return 1;
        }
        return -1;
    }

}
