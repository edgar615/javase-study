package com.edgar.module.sys.service;

import com.edgar.core.exception.BusinessCode;
import com.edgar.core.exception.SystemException;
import com.edgar.core.repository.*;
import com.edgar.core.validator.ValidatorBus;
import com.edgar.module.sys.repository.domain.SysMenuRoute;
import com.edgar.module.sys.repository.domain.SysRoleRoute;
import com.edgar.module.sys.repository.domain.SysRoute;
import com.edgar.module.sys.service.impl.SysRouteServiceImpl;
import com.edgar.module.sys.validator.SysRouteUpdateValidator;
import com.edgar.module.sys.validator.SysRouteValidator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IDUtils.class, QueryExample.class})
public class SysRouteServiceTest {
    @Mock
    private BaseDao<Integer, SysRoute> sysRouteDao;

    @Mock
    private BaseDao<Integer, SysRoleRoute> sysRoleRouteDao;

    @Mock
    private BaseDao<Integer, SysMenuRoute> sysMenuRouteDao;

    private SysRouteServiceImpl sysRouteService;

    @Mock
    private ValidatorBus validatorBus;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sysRouteService = new SysRouteServiceImpl();
        sysRouteService.setSysRouteDao(sysRouteDao);
        sysRouteService.setSysRoleRouteDao(sysRoleRouteDao);
        sysRouteService.setSysMenuRouteDao(sysMenuRouteDao);
        sysRouteService.setValidatorBus(validatorBus);
    }

    @Test
    public void testGet() {
        SysRoute sysRoute = new SysRoute();
        sysRoute.setName("测试路由");
        when(sysRouteDao.get(anyInt())).thenReturn(sysRoute);
        SysRoute result = sysRouteService.get(1);
        Assert.assertEquals(ToStringBuilder.reflectionToString(sysRoute,
                ToStringStyle.SHORT_PREFIX_STYLE), ToStringBuilder
                .reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE));
        verify(sysRouteDao, only()).get(anyInt());
    }

    @Test
    public void testDelete() {
        when(sysRouteDao.deleteByPkAndVersion(anyInt(), anyLong())).thenReturn(
                1l);
        when(sysRoleRouteDao.delete(any(QueryExample.class))).thenReturn(1l);
        when(sysMenuRouteDao.delete(any(QueryExample.class))).thenReturn(1l);

        final QueryExample example = QueryExample.newInstance();
        mockStatic(QueryExample.class);
        when(QueryExample.newInstance()).thenReturn(example);

        sysRouteService.deleteWithLock(1, 1L);
        verify(sysRouteDao, only()).deleteByPkAndVersion(anyInt(), anyLong());
        verify(sysRoleRouteDao, only()).delete(any(QueryExample.class));
        verify(sysMenuRouteDao, only()).delete(any(QueryExample.class));
        verifyStatic(only());
        QueryExample.newInstance();
        Assert.assertTrue(example.getCriterias().contains(
                new Criteria("routeId", SqlOperator.EQ, 1)));
    }

    @Test
    public void testPaination() {
        final List<SysRoute> sysRoutes = new ArrayList<SysRoute>();
        sysRoutes.add(new SysRoute());
        sysRoutes.add(new SysRoute());
        final Pagination<SysRoute> pagination = Pagination.newInstance(1, 10,
                2, sysRoutes);
        final QueryExample example = QueryExample.newInstance();
        when(sysRouteDao.pagination(same(example), anyInt(), anyInt()))
                .thenReturn(pagination);
        Pagination<SysRoute> result = sysRouteService
                .pagination(example, 1, 10);
        Assert.assertEquals(pagination, result);
        Assert.assertTrue(example.containCriteria(new Criteria("isRoot",
                SqlOperator.EQ, 0)));
        verify(sysRouteDao, times(1)).pagination(same(example), anyInt(),
                anyInt());
    }

    @Test
    public void testFindAll() {
        final List<SysRoute> sysRoutes = new ArrayList<SysRoute>();
        sysRoutes.add(new SysRoute());
        sysRoutes.add(new SysRoute());
        final QueryExample example = QueryExample.newInstance();
        mockStatic(QueryExample.class);
        when(QueryExample.newInstance()).thenReturn(example);
        when(sysRouteDao.query(any(QueryExample.class))).thenReturn(sysRoutes);
        List<SysRoute> result = sysRouteService.findAll();
        Assert.assertEquals(sysRoutes, result);
        Assert.assertTrue(example.containCriteria(new Criteria("isRoot",
                SqlOperator.EQ, 0)));
        verify(sysRouteDao, times(1)).query(any(QueryExample.class));
    }

    @Test(expected = SystemException.class)
    public void testSaveFailed() {
        SysRoute sysRoute = new SysRoute();
        doThrow(new SystemException(BusinessCode.INVALID)).when(validatorBus).validator(same(sysRoute), eq(SysRouteValidator.class));
        sysRouteService.save(sysRoute);

    }

    @Test
    public void testSave() {
        mockStatic(IDUtils.class);
        when(IDUtils.getNextId()).thenReturn(1);
        SysRoute sysRoute = new SysRoute();
        sysRoute.setUrl("/123");
        sysRoute.setName("1233");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysRoute), eq(SysRouteValidator.class));
        sysRouteService.save(sysRoute);
        verify(sysRouteDao, only()).insert(same(sysRoute));
        verifyStatic(only());
        IDUtils.getNextId();
    }

    @Test(expected = SystemException.class)
    public void testUpdateFailed() {
        SysRoute sysRoute = new SysRoute();
        doThrow(new SystemException(BusinessCode.INVALID)).when(validatorBus).validator(same(sysRoute), eq(SysRouteUpdateValidator.class));
        sysRouteService.update(sysRoute);
        verify(sysRouteDao, never()).update(same(sysRoute));

    }

    @Test
    public void testUpdate() {
        SysRoute sysRoute = new SysRoute();
        sysRoute.setRouteId(1);
        sysRoute.setUrl("/123");
        sysRoute.setName("1233");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysRoute), eq(SysRouteUpdateValidator.class));
        sysRouteService.update(sysRoute);
        verify(sysRouteDao, only()).update(same(sysRoute));
    }

}
