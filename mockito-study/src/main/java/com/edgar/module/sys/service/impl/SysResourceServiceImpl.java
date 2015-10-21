package com.edgar.module.sys.service.impl;

import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.util.Constants;
import com.edgar.module.sys.repository.domain.SysResource;
import com.edgar.module.sys.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源的业务逻辑实现类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Autowired
    private BaseDao<Integer, SysResource> sysResourceDao;

    @Override
    public List<SysResource> findAll() {
        QueryExample example = QueryExample.newInstance();
        example.equalsTo("isRoot", 0);
        example.equalsTo("authType", Constants.AUTH_TYPE_REST);
        example.asc("url");
        return sysResourceDao.query(example);
    }


    @Override
    public Pagination<SysResource> pagination(QueryExample example, int page, int pageSize) {
        example.asc("url");
        example.equalsTo("isRoot", 0);
        example.equalsTo("authType", Constants.AUTH_TYPE_REST);
        return sysResourceDao.pagination(example, page, pageSize);
    }

    @Override
    public SysResource get(int resourceId) {
        return sysResourceDao.get(resourceId);
    }

    public void setSysResourceDao(BaseDao<Integer, SysResource> sysResourceDao) {
        this.sysResourceDao = sysResourceDao;
    }
}
