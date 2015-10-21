package com.edgar.module.sys.web;

import com.edgar.core.mvc.ToQueryExample;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.shiro.AuthHelper;
import com.edgar.module.sys.repository.domain.SysResource;
import com.edgar.module.sys.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源的rest类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Controller
@RequestMapping("/sys/resource")
public class SysResResource {
    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 根据资源ID查询资源
     *
     * @param resourceId 资源ID
     * @return 资源
     */
    @AuthHelper(value = "View Resource", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{resourceId}")
    @ResponseBody
    public SysResource get(@PathVariable("resourceId") int resourceId) {
        return sysResourceService.get(resourceId);
    }

    /**
     * 分页查询资源
     *
     * @param page     当前页，默认为1
     * @param pageSize 每页显示数量，默认为10
     * @param example  查询条件
     * @return 资源的分页类
     */
    @AuthHelper(value = "Paging Query Resource", isRoot = true)
    @RequestMapping(method = RequestMethod.GET, value = "/pagination")
    @ResponseBody
    public Pagination<SysResource> pagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ToQueryExample QueryExample example) {
        return sysResourceService.pagination(example, page, pageSize);
    }

    /**
     * 查询所有的资源
     *
     * @return 资源的集合
     */
    @AuthHelper(value = "Query Resource", isRoot = true)
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<SysResource> findAll() {
        return sysResourceService.findAll();
    }

}
