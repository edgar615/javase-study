package com.edgar.module.sys.web;

import com.edgar.core.mvc.ToQueryExample;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.view.ResponseMessage;
import com.edgar.module.sys.repository.domain.I18nMessage;
import com.edgar.module.sys.service.I18nMessageService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 国际化的rest类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/sys/i18n")
public class I18nMessageResource {

    @Autowired
    private I18nMessageService i18nMessageService;

    /**
     * 保存i18n
     *
     * @param sysJob i18n
     * @return 保存成功返回1，失败返回0
     */
    @AuthHelper(value = "Create i18n", isRoot = true)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView save(@RequestBody I18nMessage sysJob) {
        i18nMessageService.save(sysJob);
        return ResponseMessage.success();
    }

    /**
     * 更新i18n
     *
     * @param i18nId i18nID
     * @param sysJob i18n
     * @return 保存成功，返回1，保存失败，返回0
     */
    @AuthHelper(value = "Update i18n", isRoot = true)
    @RequestMapping(method = RequestMethod.PUT, value = "/{i18nId}")
    @ResponseBody
    public ModelAndView update(@PathVariable("i18nId") int i18nId, @RequestBody I18nMessage sysJob) {
        sysJob.setI18nId(i18nId);
        i18nMessageService.update(sysJob);
        return ResponseMessage.success();
    }

    /**
     * 根据i18nID查询i18n
     *
     * @param i18nId i18nID
     * @return i18n
     */
    @AuthHelper(value = "View i18n", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{i18nId}")
    @ResponseBody
    public I18nMessage get(@PathVariable("i18nId") int i18nId) {
        return i18nMessageService.get(i18nId);
    }

    /**
     * 分页查询i18n
     *
     * @param page     当前页，默认为1
     * @param pageSize 每页显示数量，默认为10
     * @param example  查询条件
     * @return i18n的分页类
     */
    @AuthHelper(value = "Paging Query i18n", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/pagination")
    @ResponseBody
    public Pagination<I18nMessage> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ToQueryExample QueryExample example) {
        return i18nMessageService.pagination(example, page, pageSize);
    }

    /**
     * 根据i18nID和时间戳查询i18n
     *
     * @param i18nId      i18nID
     * @param updatedTime 时间戳
     * @return 如果删除成功，返回1
     */
    @AuthHelper(value = "Delete i18n", isRoot = true)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{i18nId}")
    @ResponseBody
    public ModelAndView delete(@PathVariable("i18nId") int i18nId,
                               @RequestParam("updatedTime") long updatedTime) {
        i18nMessageService.deleteWithLock(i18nId, updatedTime);
        return ResponseMessage.success();
    }

    /**
     * 校验i18n类是否唯一
     *
     * @param key i18n类
     * @return 如果存在，返回false
     */
    @AuthHelper(value = "Check Unique i18n", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/check/key")
    @ResponseBody
    public ModelAndView checkClazzName(@RequestParam("field") String key) {
        Validate.notBlank(key);
        boolean result = i18nMessageService.checkKey(key);
        return ResponseMessage.asModelAndView(result);
    }

    /**
     * 生成i18n的JSON
     *
     * @return 视图
     * @throws java.io.IOException
     */
    @AuthHelper(value = "Generate i18n JSON", isRoot = true)
    @RequestMapping(method = RequestMethod.POST, value = "/tojson")
    @ResponseBody
    public ModelAndView toJson() throws IOException {
        i18nMessageService.saveToJsonFile();
        return ResponseMessage.asModelAndView(true);
    }
}
