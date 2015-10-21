package com.edgar.module.sys.vo;

import com.edgar.module.sys.repository.domain.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class SysMenuVo extends SysMenu {

    private final List<SysMenuVo> children = new ArrayList<SysMenuVo>();

    private List<Integer> routeIds = new ArrayList<Integer>();

    private List<Integer> resourceIds = new ArrayList<Integer>();

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void addChild(SysMenuVo sysMenu) {
        children.add(sysMenu);
    }

    public List<SysMenuVo> getChildren() {
        return children;
    }

    public List<Integer> getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(List<Integer> routeIds) {
        this.routeIds = routeIds;
    }

    public List<Integer> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Integer> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
