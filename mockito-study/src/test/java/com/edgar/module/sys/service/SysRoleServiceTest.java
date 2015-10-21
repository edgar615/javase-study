package com.edgar.module.sys.service;

import com.edgar.core.exception.BusinessCode;
import com.edgar.core.exception.SystemException;
import com.edgar.core.repository.*;
import com.edgar.core.validator.ValidatorBus;
import com.edgar.module.sys.repository.domain.*;
import com.edgar.module.sys.service.impl.SysRoleServiceImpl;
import com.edgar.module.sys.validator.SysRoleUpdateValidator;
import com.edgar.module.sys.validator.SysRoleValidator;
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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IDUtils.class, QueryExample.class})
public class SysRoleServiceTest {

    @Mock
    private BaseDao<Integer, SysRole> sysRoleDao;

    @Mock
    private BaseDao<Integer, SysRoleRoute> sysRoleRouteDao;

    @Mock
    private BaseDao<Integer, SysRoleMenu> sysRoleMenuDao;

    @Mock
    private BaseDao<Integer, SysRoleRes> sysRoleResDao;

    @Mock
    private BaseDao<Integer, SysUserRole> sysUserRoleDao;

    @Mock
    private ValidatorBus validatorBus;

    private SysRoleServiceImpl sysRoleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sysRoleService = new SysRoleServiceImpl();
        sysRoleService.setSysRoleDao(sysRoleDao);
        sysRoleService.setSysRoleMenuDao(sysRoleMenuDao);
        sysRoleService.setSysRoleRouteDao(sysRoleRouteDao);
        sysRoleService.setSysUserRoleDao(sysUserRoleDao);
        sysRoleService.setSysRoleResDao(sysRoleResDao);
        sysRoleService.setValidatorBus(validatorBus);
    }

    @Test
    public void testGet() {
        when(sysRoleDao.get(anyInt())).thenReturn(new SysRole());
        sysRoleService.get(1);
        verify(sysRoleDao, only()).get(1);
    }

    @Test
    public void testDelete() {
        when(sysUserRoleDao.delete(any(QueryExample.class))).thenReturn(1l);
        when(sysRoleDao.deleteByPkAndVersion(anyInt(), anyLong())).thenReturn(1l);
        when(sysRoleMenuDao.delete(any(QueryExample.class))).thenReturn(1l);
        when(sysRoleRouteDao.delete(any(QueryExample.class))).thenReturn(1l);
        when(sysRoleResDao.delete(any(QueryExample.class))).thenReturn(1l);

        final QueryExample example = QueryExample.newInstance();
        mockStatic(QueryExample.class);
        when(QueryExample.newInstance()).thenReturn(example);

        sysRoleService.deleteWithLock(1, 1L);

        verify(sysRoleDao, times(1)).deleteByPkAndVersion(anyInt(), anyLong());
        verify(sysUserRoleDao, only()).delete(any(QueryExample.class));
        verify(sysRoleMenuDao, only()).delete(any(QueryExample.class));
        verify(sysRoleRouteDao, only()).delete(any(QueryExample.class));
        verifyStatic(only());
        QueryExample.newInstance();
        Assert.assertTrue(example.getCriterias().contains(
                new Criteria("roleId", SqlOperator.EQ, 1)));
    }

    @Test
    public void testQuery() {
        final List<SysRole> sysRoles = new ArrayList<SysRole>();
        sysRoles.add(new SysRole());
        sysRoles.add(new SysRole());
        final QueryExample example = QueryExample.newInstance();
        mockStatic(QueryExample.class);
        when(QueryExample.newInstance()).thenReturn(example);
        when(sysRoleDao.query(any(QueryExample.class))).thenReturn(sysRoles);
        List<SysRole> result = sysRoleService.query(example);
        Assert.assertEquals(sysRoles, result);
        Assert.assertTrue(example.containCriteria(new Criteria("isRoot",
                SqlOperator.EQ, 0)));
        verify(sysRoleDao, times(1)).query(any(QueryExample.class));
    }

    @Test
    public void testPaination() {
        final List<SysRole> sysRoles = new ArrayList<SysRole>();
        sysRoles.add(new SysRole());
        sysRoles.add(new SysRole());
        final Pagination<SysRole> pagination = Pagination.newInstance(1, 10, 2, sysRoles);
        final QueryExample example = QueryExample.newInstance();
        when(sysRoleDao.pagination(same(example), anyInt(), anyInt())).thenReturn(
                pagination);
        Pagination<SysRole> result = sysRoleService.pagination(example, 1, 10);
        Assert.assertEquals(pagination, result);
        Assert.assertTrue(example.containCriteria(new Criteria("isRoot",
                SqlOperator.EQ, 0)));
        verify(sysRoleDao, times(1)).pagination(same(example), anyInt(), anyInt());
    }

    @Test(expected = SystemException.class)
    public void testSaveFailed() {
        mockStatic(IDUtils.class);
        when(IDUtils.getNextId()).thenReturn(1);
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(1);
        doThrow(new SystemException(BusinessCode.INVALID)).when(validatorBus).validator(same(sysRole), eq(SysRoleValidator.class));
        sysRoleService.save(sysRole);
        verify(sysRoleDao, never()).insert(same(sysRole));
        verify(validatorBus, times(1)).validator(sysRole, SysRoleValidator.class);
        verifyStatic(never());
    }

    @Test
    public void testSave() {
        mockStatic(IDUtils.class);
        when(IDUtils.getNextId()).thenReturn(1);
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("root");
        sysRole.setRoleName("root1");
        sysRole.setRoleId(1);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysRole), eq(SysRoleValidator.class));
        sysRoleService.save(sysRole);
        verify(sysRoleDao, only()).insert(same(sysRole));
        verify(validatorBus, times(1)).validator(sysRole, SysRoleValidator.class);
        verifyStatic(only());
        IDUtils.getNextId();
        Assert.assertFalse(sysRole.getIsRoot());
    }

    @Test(expected = SystemException.class)
    public void testUpdateNull() {
        SysRole sysRole = new SysRole();
        doThrow(new SystemException(BusinessCode.INVALID)).when(validatorBus).validator(same(sysRole), eq(SysRoleUpdateValidator.class));
        sysRoleService.update(sysRole);
        verify(sysRoleDao, never()).update(same(sysRole));
        verify(validatorBus, times(1)).validator(sysRole, SysRoleUpdateValidator.class);
    }


    @Test
    public void testUpdate() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("root");
        sysRole.setRoleName("root1");
        sysRole.setRoleId(1);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysRole), eq(SysRoleUpdateValidator.class));
        sysRoleService.update(sysRole);
        verify(sysRoleDao, only()).update(same(sysRole));
        verify(validatorBus, times(1)).validator(sysRole, SysRoleUpdateValidator.class);
    }
}
