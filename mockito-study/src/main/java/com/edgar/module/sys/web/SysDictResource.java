package com.edgar.module.sys.web;

import com.edgar.core.exception.BusinessCode;
import com.edgar.core.exception.SystemException;
import com.edgar.core.mvc.ToQueryExample;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.shiro.AuthType;
import com.edgar.core.view.ResponseMessage;
import com.edgar.module.sys.init.DictoryLoader;
import com.edgar.module.sys.repository.domain.SysDict;
import com.edgar.module.sys.service.SysDictService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

/**
 * 系统字典的rest类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/sys/dict")
public class SysDictResource {

    @Autowired
    private SysDictService sysDictService;

    /**
     * 新增字典
     *
     * @param sysDict 字典
     * @return 如果保存成功，返回1，保存失败，返回0
     */
    @AuthHelper(value = "Create Dictory", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView save(@RequestBody SysDict sysDict) {
        sysDictService.save(sysDict);
        return ResponseMessage.success();
    }

    /**
     * 修改字典
     *
     * @param dictCode 字典编码
     * @param sysDict  字典
     * @return 如果保存成功，返回1，保存失败，返回0
     */
    @AuthHelper(value = "Update Dictory", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.PUT, value = "/{dictCode}")
    @ResponseBody
    public ModelAndView update(@PathVariable("dictCode") String dictCode, @RequestBody SysDict sysDict) {
        sysDict.setDictCode(dictCode);
        sysDictService.update(sysDict);
        return ResponseMessage.success();
    }

    /**
     * 根据字典编码查看字典
     *
     * @param dictCode 字典编码
     * @return 系统字典
     */
    @AuthHelper(value = "View Dictory", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.GET, value = "/{dictCode}")
    @ResponseBody
    public SysDict get(@PathVariable("dictCode") String dictCode) {
        SysDict sysDict = sysDictService.get(dictCode);
        if (sysDict == null) {
            throw new SystemException(BusinessCode.NULL, "字典" + dictCode + "不存在");
        }
        return sysDict;
    }

    /**
     * 分页查询系统字典
     *
     * @param page     页码
     * @param pageSize 每页显示大小
     * @param example  查询条件
     * @return 系统字典的分页类
     */
    @AuthHelper(value = "PagingQuery Dictory", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.GET, value = "/pagination")
    @ResponseBody
    public Pagination<SysDict> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ToQueryExample QueryExample example) {
        return sysDictService.pagination(example, page, pageSize);
    }

    /**
     * 查询字典列表
     *
     * @param example 查询条件
     * @return 系统字典的集合
     */
    @AuthHelper(value = "Query Dictory", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<SysDict> items(@ToQueryExample QueryExample example) {
        return sysDictService.query(example);
    }

    /**
     * 根据字典编码删除字典
     *
     * @param dictCode    字典编码
     * @param updatedTime 版本号
     * @return 如果删除成功，返回1，删除失败，返回0
     */
    @AuthHelper(value = "Delete Dictory", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{dictCode}")
    @ResponseBody
    public ModelAndView delete(@PathVariable("dictCode") String dictCode,
                               @RequestParam("updatedTime") long updatedTime) {
        sysDictService.deleteWithLock(dictCode, updatedTime);
        return ResponseMessage.success();
    }

    /**
     * 查询字典列表
     *
     * @param dictCode 字典编码
     * @return 系统字典的集合
     */
    @AuthHelper(value = "Query the items Of Dictory ", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.GET, value = "/items/{dictCode}")
    @ResponseBody
    public Collection<SysDict> items(@PathVariable("dictCode") String dictCode) {
        return DictoryLoader.getDictMap().get(dictCode).values();
    }

    /**
     * 校验字典码是否唯一
     *
     * @param dictCode 字典编码
     * @return 如果存在，返回false
     */
    @AuthHelper(value = "Check Unique Dictory", isRoot = true, type = AuthType.AUTHC)
    @RequestMapping(method = RequestMethod.GET, value = "/check/dictCode")
    @ResponseBody
    public ModelAndView checkDictCode(@RequestParam("field") String dictCode) {
        Validate.notBlank(dictCode);
        boolean result = sysDictService.checkDictCode(dictCode);
        return ResponseMessage.asModelAndView(result);
    }

    public void setSysDictService(SysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }
}
