package com.edgar.module.sys.web;

import com.edgar.core.mvc.ToQueryExample;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.shiro.AuthType;
import com.edgar.core.view.ResponseMessage;
import com.edgar.module.sys.repository.domain.SysRole;
import com.edgar.module.sys.repository.domain.SysUser;
import com.edgar.module.sys.repository.domain.SysUserProfile;
import com.edgar.module.sys.repository.domain.SysUserRole;
import com.edgar.module.sys.service.SysRoleService;
import com.edgar.module.sys.service.SysUserService;
import com.edgar.module.sys.vo.ChangePasswordVo;
import com.edgar.module.sys.vo.SysUserRoleVo;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 用户的rest接口
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserResource {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 保存用户
     *
     * @param sysUser 用户
     * @return 保存成功返回1，失败返回0
     */
    @AuthHelper("Create User")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView save(@RequestBody SysUserRoleVo sysUser) {
        sysUserService.save(sysUser);
        return ResponseMessage.success();
    }

    /**
     * 更新用户
     *
     * @param userId  用户ID
     * @param sysUser 用户
     * @return 保存成功，返回1，保存失败，返回0
     */
    @AuthHelper("Update User")
    @RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
    @ResponseBody
    public ModelAndView update(@PathVariable("userId") int userId,
                               @RequestBody SysUserRoleVo sysUser) {
        sysUser.setUserId(userId);
        sysUserService.update(sysUser);
        return ResponseMessage.success();
    }

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    @AuthHelper("View User")
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    @ResponseBody
    public SysUser get(@PathVariable("userId") int userId) {
        return sysUserService.get(userId);
    }

    /**
     * 分页查询用户
     *
     * @param page     当前页，默认为1
     * @param pageSize 每页显示数量，默认为10
     * @param example  查询条件
     * @return 用户的分页类
     */
    @AuthHelper("Paging Query User")
    @RequestMapping(method = RequestMethod.GET, value = "/pagination")
    @ResponseBody
    public Pagination<SysUser> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ToQueryExample(maxNumOfRecords = 5000) QueryExample example) {
        return sysUserService.pagination(example, page, pageSize);
    }

    /**
     * 根据用户ID和时间戳删除用户
     *
     * @param userId      用户ID
     * @param updatedTime 时间戳
     * @return 如果删除成功，返回1
     */
    @AuthHelper("Delete User")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    @ResponseBody
    public ModelAndView delete(@PathVariable("userId") int userId,
                               @RequestParam("updatedTime") long updatedTime) {
        sysUserService.deleteWithLock(userId, updatedTime);
        return ResponseMessage.success();
    }

    /**
     * 校验用户名是否唯一
     *
     * @param username 用户名
     * @return 如果存在，返回false
     */
    @AuthHelper(value = "Check Unique Username")
    @RequestMapping(method = RequestMethod.GET, value = "/check/username")
    @ResponseBody
    public ModelAndView checkUsername(@RequestParam("field") String username) {
        Validate.notBlank(username);
        boolean result = sysUserService.checkUsername(username);
        return ResponseMessage.asModelAndView(result);
    }

    /**
     * 查询角色列表
     *
     * @param example 查询条件
     * @return 角色的分页类
     */
    @AuthHelper(value = "Query Role")
    @RequestMapping(method = RequestMethod.GET, value = "/roles")
    @ResponseBody
    public List<SysRole> query(@ToQueryExample QueryExample example) {
        return sysRoleService.query(example);
    }

    /**
     * 根据用户，查询用户角色
     *
     * @param userId 用户ID
     * @return 用户角色列表
     */
    @AuthHelper(value = "Query the roles of user")
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/role/{userId}")
    public List<SysUserRole> getRoles(@PathVariable("userId") int userId) {
        return sysUserService.getRoles(userId);
    }

    @AuthHelper(value = "Query Profile", type = AuthType.AUTHC)
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/profile/{userId}")
    public SysUserProfile getProfile(@PathVariable("userId") int userId) {
        return sysUserService.getProfile(userId);
    }

    @AuthHelper(value = "Update Profile", type = AuthType.AUTHC)
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/profile/{userId}")
    public ModelAndView updateProfile(@PathVariable("userId") int userId,
                                      @RequestBody SysUserProfile profile) {
        profile.setUserId(userId);
        sysUserService.updateProfile(profile);
        return ResponseMessage.success();
    }

    @AuthHelper(value = "Update Password", type = AuthType.AUTHC)
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/password/{userId}")
    public ModelAndView updatePassword(@PathVariable("userId") int userId,
                                       @RequestBody ChangePasswordVo changePasswordVo) {
        changePasswordVo.setUserId(userId);
        sysUserService.updatePassword(changePasswordVo);
        return ResponseMessage.success();
    }

}
