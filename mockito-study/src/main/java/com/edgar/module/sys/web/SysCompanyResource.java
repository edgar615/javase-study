package com.edgar.module.sys.web;

import com.edgar.core.mvc.ToQueryExample;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.view.ResponseMessage;
import com.edgar.module.sys.repository.domain.SysCompany;
import com.edgar.module.sys.service.SysCompanyService;
import com.edgar.module.sys.vo.SysCompanyVo;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys/company")
public class SysCompanyResource {
    @Autowired
    private SysCompanyService sysCompanyService;

    /**
     * 保存公司
     *
     * @param sysCompanyVo 公司
     * @return 保存成功返回1，失败返回0
     */
    @AuthHelper(value = "Create Company", isRoot = true)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView save(@RequestBody SysCompanyVo sysCompanyVo) {
        sysCompanyService.save(sysCompanyVo);
        return ResponseMessage.success();
    }

    /**
     * 分页查询公司
     *
     * @param page     当前页，默认为1
     * @param pageSize 每页显示数量，默认为10
     * @param example  查询条件
     * @return 公司的分页类
     */
    @AuthHelper(value = "Query Company", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/pagination")
    @ResponseBody
    public Pagination<SysCompany> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ToQueryExample QueryExample example) {
        return sysCompanyService.pagination(example, page, pageSize);
    }

    /**
     * 根据公司ID和时间戳查询公司
     *
     * @param companyId 公司ID
     * @return 如果删除成功，返回1
     */
    @AuthHelper(value = "Delete Company", isRoot = true)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{companyId}")
    @ResponseBody
    public ModelAndView delete(@PathVariable("companyId") int companyId) {
        sysCompanyService.delete(companyId);
        return ResponseMessage.success();
    }

    /**
     * 校验公司编码是否唯一
     *
     * @param code 公司类
     * @return 如果存在，返回false
     */
    @AuthHelper(value = "Check Unique Job", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/check/code")
    @ResponseBody
    public ModelAndView checkClazzName(@RequestParam("field") String code) {
        Validate.notBlank(code);
        boolean result = sysCompanyService.checkCode(code);
        return ResponseMessage.asModelAndView(result);
    }
}
