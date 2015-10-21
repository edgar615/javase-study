package com.edgar.module.sys.web;

import com.edgar.core.mvc.ToQueryExample;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.core.view.ResponseMessage;
import com.edgar.module.sys.repository.domain.SysJob;
import com.edgar.module.sys.service.SysJobService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys/job")
public class SysJobResource {

    @Autowired
    private SysJobService sysJobService;

    /**
     * 保存作业
     *
     * @param sysJob 作业
     * @return 保存成功返回1，失败返回0
     */
    @AuthHelper(value = "Create Job", isRoot = true)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView save(@RequestBody SysJob sysJob) {
        sysJobService.save(sysJob);
        return ResponseMessage.success();
    }

    /**
     * 更新作业
     *
     * @param jobId  作业ID
     * @param sysJob 作业
     * @return 保存成功，返回1，保存失败，返回0
     */
    @AuthHelper(value = "Update Job", isRoot = true)
    @RequestMapping(method = RequestMethod.PUT, value = "/{jobId}")
    @ResponseBody
    public ModelAndView update(@PathVariable("jobId") int jobId, @RequestBody SysJob sysJob) {
        sysJob.setJobId(jobId);
        sysJobService.update(sysJob);
        return ResponseMessage.success();
    }

    /**
     * 根据作业ID查询作业
     *
     * @param jobId 作业ID
     * @return 作业
     */
    @AuthHelper(value = "View Job", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{jobId}")
    @ResponseBody
    public SysJob get(@PathVariable("jobId") int jobId) {
        return sysJobService.get(jobId);
    }

    /**
     * 分页查询作业
     *
     * @param page     当前页，默认为1
     * @param pageSize 每页显示数量，默认为10
     * @param example  查询条件
     * @return 作业的分页类
     */
    @AuthHelper(value = "Paging Query Job", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/pagination")
    @ResponseBody
    public Pagination<SysJob> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ToQueryExample QueryExample example) {
        return sysJobService.pagination(example, page, pageSize);
    }

    /**
     * 根据作业ID和时间戳查询作业
     *
     * @param jobId       作业ID
     * @param updatedTime 时间戳
     * @return 如果删除成功，返回1
     */
    @AuthHelper(value = "Delete Job", isRoot = true)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{jobId}")
    @ResponseBody
    public ModelAndView delete(@PathVariable("jobId") int jobId,
                               @RequestParam("updatedTime") long updatedTime) {
        sysJobService.deleteWithLock(jobId, updatedTime);
        return ResponseMessage.success();
    }

    /**
     * 校验作业类是否唯一
     *
     * @param clazzName 作业类
     * @return 如果存在，返回false
     */
    @AuthHelper(value = "Check Unique Job", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/check/clazzname")
    @ResponseBody
    public ModelAndView checkClazzName(@RequestParam("field") String clazzName) {
        Validate.notBlank(clazzName);
        boolean result = sysJobService.checkClazzName(clazzName);
        return ResponseMessage.asModelAndView(result);
    }
}
