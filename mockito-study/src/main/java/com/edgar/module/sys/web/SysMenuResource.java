package com.edgar.module.sys.web;

import com.edgar.core.mvc.ToQueryExample;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.view.ResponseMessage;
import com.edgar.module.sys.repository.domain.SysMenu;
import com.edgar.module.sys.repository.domain.SysMenuRes;
import com.edgar.module.sys.repository.domain.SysMenuRoute;
import com.edgar.module.sys.service.SysMenuService;
import com.edgar.module.sys.vo.SysMenuVo;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 菜单的rest
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/sys/menu")
public class SysMenuResource {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 保存菜单
     *
     * @param sysMenu 菜单
     * @return 保存成功返回1，失败返回0
     */
    @AuthHelper(value = "Create Menu", isRoot = true)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView save(@RequestBody SysMenuVo sysMenu) {
        sysMenuService.save(sysMenu);
        return ResponseMessage.success();
    }

    /**
     * 更新菜单
     *
     * @param menuId  菜单ID
     * @param sysMenu 菜单
     * @return 保存成功，返回1，保存失败，返回0
     */
    @AuthHelper(value = "Update Menu", isRoot = true)
    @RequestMapping(method = RequestMethod.PUT, value = "/{menuId}")
    @ResponseBody
    public ModelAndView update(@PathVariable("menuId") int menuId,
                               @RequestBody SysMenuVo sysMenu) {
        sysMenu.setMenuId(menuId);
        sysMenuService.update(sysMenu);
        return ResponseMessage.success();
    }

    /**
     * 根据菜单ID查询菜单
     *
     * @param menuId 菜单ID
     * @return 菜单
     */
    @AuthHelper(value = "View Menu", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{menuId}")
    @ResponseBody
    public SysMenu get(@PathVariable("menuId") int menuId) {
        return sysMenuService.get(menuId);
    }

    /**
     * 查询菜单
     *
     * @param example 查询条件
     * @return 菜单的分页类
     */
    @AuthHelper(value = "Query Menu")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<SysMenuVo> query(
            @ToQueryExample(maxNumOfRecords = 1000) QueryExample example) {
        return sysMenuService.query(example);
    }

    /**
     * 根据菜单ID和时间戳删除菜单
     *
     * @param menuId      菜单ID
     * @param updatedTime 时间戳
     * @return 如果删除成功，返回1
     */
    @AuthHelper(value = "Delete Menu", isRoot = true)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{menuId}")
    @ResponseBody
    public ModelAndView delete(@PathVariable("menuId") int menuId,
                               @RequestParam("updatedTime") long updatedTime) {
        sysMenuService.deleteWithLock(menuId, updatedTime);
        return ResponseMessage.success();
    }

    /**
     * 查询与菜单关联的路由
     *
     * @return 路由的集合
     */
    @AuthHelper(value = "Query relation of menu and route", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/route/{menuId}")
    @ResponseBody
    public List<SysMenuRoute> getRoute(@PathVariable("menuId") int menuId) {
        return sysMenuService.getRoute(menuId);
    }

    /**
     * 查询与菜单关联的
     *
     * @return 资源的集合
     */
    @AuthHelper(value = "Query relation of menu and resource", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/resource/{menuId}")
    @ResponseBody
    public List<SysMenuRes> getResource(@PathVariable("menuId") int menuId) {
        return sysMenuService.getResource(menuId);
    }

    /**
     * 校验权限字符串是否唯一
     *
     * @param permission 权限字符串
     * @return 如果存在，返回false
     */
    @AuthHelper(value = "Check Unique Permission", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/check/permisson")
    @ResponseBody
    public ModelAndView checkPermission(@RequestParam("field") String permission) {
        Validate.notBlank(permission);
        boolean result = sysMenuService.checkPermisson(permission);
        return ResponseMessage.asModelAndView(result);
    }

}
