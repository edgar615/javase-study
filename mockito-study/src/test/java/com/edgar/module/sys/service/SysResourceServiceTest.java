package com.edgar.module.sys.service;

import com.edgar.core.repository.*;
import com.edgar.core.util.Constants;
import com.edgar.module.sys.repository.domain.SysResource;
import com.edgar.module.sys.service.impl.SysResourceServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(QueryExample.class)
public class SysResourceServiceTest {

    @Mock
    private BaseDao<Integer, SysResource> sysResourceDao;

    private SysResourceServiceImpl sysResourceService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sysResourceService = new SysResourceServiceImpl();
        sysResourceService.setSysResourceDao(sysResourceDao);
    }

    @Test
    public void testGet() {
        when(sysResourceDao.get(anyInt())).thenReturn(new SysResource());
        sysResourceService.get(1);
        verify(sysResourceDao, only()).get(anyInt());
    }

    @Test
    public void testPagination() {
        final List<SysResource> sysResources = new ArrayList<SysResource>();
        sysResources.add(new SysResource());
        sysResources.add(new SysResource());
        final Pagination<SysResource> pagination = Pagination.newInstance(1, 10, 2,
                sysResources);
        final QueryExample example = QueryExample.newInstance();
        when(sysResourceDao.pagination(same(example), anyInt(), anyInt())).thenReturn(
                pagination);
        Pagination<SysResource> result = sysResourceService.pagination(example, 1, 10);
        Assert.assertEquals(pagination, result);
        Assert.assertTrue(example.containCriteria(new Criteria("isRoot",
                SqlOperator.EQ, 0)));
        Assert.assertTrue(example.containCriteria(new Criteria("authType",
                SqlOperator.EQ, Constants.AUTH_TYPE_REST)));
        verify(sysResourceDao, times(1)).pagination(same(example), anyInt(), anyInt());
    }

    @Test
    public void testFindAll() {
        QueryExample example = QueryExample.newInstance();
        mockStatic(QueryExample.class);
        when(QueryExample.newInstance()).thenReturn(example);
        final List<SysResource> sysResources = new ArrayList<SysResource>();
        sysResources.add(new SysResource());
        sysResources.add(new SysResource());
        when(sysResourceDao.query(any(QueryExample.class))).thenReturn(sysResources);
        List<SysResource> result = sysResourceService.findAll();
        Assert.assertEquals(sysResources, result);
        Assert.assertTrue(example.containCriteria(new Criteria("isRoot",
                SqlOperator.EQ, 0)));
        Assert.assertTrue(example.containCriteria(new Criteria("authType",
                SqlOperator.EQ, Constants.AUTH_TYPE_REST)));
        verify(sysResourceDao, times(1)).query(any(QueryExample.class));
        verifyStatic(only());
        QueryExample.newInstance();
    }

}
