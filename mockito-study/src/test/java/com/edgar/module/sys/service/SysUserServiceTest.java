package com.edgar.module.sys.service;

import com.edgar.core.exception.BusinessCode;
import com.edgar.core.exception.SystemException;
import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.IDUtils;
import com.edgar.core.repository.Pagination;
import com.edgar.core.repository.QueryExample;
import com.edgar.core.validator.ValidatorBus;
import com.edgar.module.sys.repository.domain.SysUser;
import com.edgar.module.sys.repository.domain.SysUserProfile;
import com.edgar.module.sys.repository.domain.SysUserRole;
import com.edgar.module.sys.service.impl.SysUserServiceImpl;
import com.edgar.module.sys.validator.SysUserUpdateValidator;
import com.edgar.module.sys.validator.SysUserValidator;
import com.edgar.module.sys.vo.SysUserRoleVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.eq;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IDUtils.class)
public class SysUserServiceTest {
    @Mock
    private BaseDao<Integer, SysUser> sysUserDao;

    @Mock
    private BaseDao<Integer, SysUserRole> sysUserRoleDao;
    @Mock
    private BaseDao<Integer, SysUserProfile> sysUserProfileDao;

    private SysUserServiceImpl sysUserService;

    @Mock
    private ValidatorBus validatorBus;

    @Mock
    private PasswordService passwordService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sysUserService = new SysUserServiceImpl();
        sysUserService.setSysUserDao(sysUserDao);
        sysUserService.setSysUserRoleDao(sysUserRoleDao);
        sysUserService.setValidatorBus(validatorBus);
        sysUserService.setSysUserProfileDao(sysUserProfileDao);
        sysUserService.setPasswordService(passwordService);
    }

    @Test
    public void testGet() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("Edgar");
        sysUser.setPassword("Edgar");
        when(sysUserDao.get(anyInt())).thenReturn(sysUser);
        SysUser result = sysUserService.get(1);
        verify(sysUserDao, only()).get(1);
        Assert.assertNull(result.getPassword());
    }

    @Test
    public void testPaination() {
        final List<SysUser> sysUsers = new ArrayList<SysUser>();
        sysUsers.add(new SysUser());
        sysUsers.add(new SysUser());
        final Pagination<SysUser> pagination = Pagination.newInstance(1, 10, 2, sysUsers);
        final QueryExample example = QueryExample.newInstance();
        when(sysUserDao.pagination(same(example), anyInt(), anyInt())).thenReturn(
                pagination);
        Pagination<SysUser> result = sysUserService.pagination(example, 1, 10);
        Assert.assertEquals(pagination, result);
        verify(sysUserDao, times(1)).pagination(same(example), anyInt(), anyInt());
    }

    @Test
    public void testCheckUsername() {
        final List<SysUser> sysUsers = new ArrayList<SysUser>();
        when(sysUserDao.query(any(QueryExample.class))).thenReturn(sysUsers);
        boolean result = sysUserService.checkUsername("Edgar");
        Assert.assertTrue(result);
        verify(sysUserDao, only()).query(any(QueryExample.class));
        sysUsers.add(new SysUser());
        result = sysUserService.checkUsername("Edgar");
        Assert.assertFalse(result);
    }

    @Test
    public void testGetRoles() {
        final List<SysUserRole> sysUserRoles = new ArrayList<SysUserRole>();
        sysUserRoles.add(new SysUserRole());
        when(sysUserRoleDao.query(any(QueryExample.class))).thenReturn(sysUserRoles);
        List<SysUserRole> results = sysUserService.getRoles(1);
        Assert.assertEquals(sysUserRoles, results);
        verify(sysUserRoleDao, only()).query(any(QueryExample.class));
    }

    @Test
    public void tesetDeleteByVersion() {
        when(sysUserDao.deleteByPkAndVersion(anyInt(), anyLong())).thenReturn(1l);
        when(sysUserRoleDao.delete(any(QueryExample.class))).thenReturn(1l);
        sysUserService.deleteWithLock(1, 1L);

        InOrder inOrder = inOrder(sysUserRoleDao, sysUserDao);
        inOrder.verify(sysUserRoleDao, times(1)).delete(any(QueryExample.class));
        inOrder.verify(sysUserDao, times(1)).deleteByPkAndVersion(1, 1L);
    }

    @Test(expected = SystemException.class)
    public void testSaveUserFailed() {
        SysUserRoleVo sysUser = new SysUserRoleVo();
        doThrow(new SystemException(BusinessCode.INVALID)).when(validatorBus).validator(same(sysUser), eq(SysUserValidator.class));
        try {
            sysUserService.save(sysUser);
        } catch (SystemException e) {
            throw e;
        } finally {
            verify(sysUserDao, never()).insert(any(SysUser.class));
            verify(sysUserRoleDao, never()).insert(any(SysUserRole.class));
        }

    }

    @Test
    public void testSaveUserNoRole() {
        PowerMockito.mockStatic(IDUtils.class);
        when(IDUtils.getNextId()).thenReturn(1);
        when(sysUserDao.insert(any(SysUser.class))).thenReturn(1L);
        when(sysUserProfileDao.insert(any(SysUserProfile.class))).thenReturn(1L);
        SysUserRoleVo sysUser = new SysUserRoleVo();
        sysUser.setUsername("z123");
        sysUser.setPassword("2322434");
        sysUser.setFullName("#$#$#$#$");
        sysUser.setEmail("$#$#@ddd");
        String password = sysUser.getPassword();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysUser), eq(SysUserValidator.class));
        sysUserService.save(sysUser);
//        Assert.assertNotEquals(password, sysUser.getPassword());
        verify(sysUserDao, only()).insert(any(SysUser.class));
        verify(sysUserProfileDao, only()).insert(any(SysUserProfile.class));
        verify(sysUserRoleDao, never()).insert(any(SysUserRole.class));
        PowerMockito.verifyStatic(times(2));
        IDUtils.getNextId();
    }

    @Test
    public void testSaveUserOneRole() {
        PowerMockito.mockStatic(IDUtils.class);
        when(IDUtils.getNextId()).thenReturn(1);
        when(sysUserDao.insert(any(SysUser.class))).thenReturn(1L);
        when(sysUserRoleDao.insert(anyListOf(SysUserRole.class))).thenReturn(1L);
        when(sysUserProfileDao.insert(any(SysUserProfile.class))).thenReturn(1L);
        SysUserRoleVo sysUser = new SysUserRoleVo();
        sysUser.setRoleIds("1");
        sysUser.setUsername("z123");
        sysUser.setPassword("2322434");
        sysUser.setFullName("#$#$#$#$");
        sysUser.setEmail("$#$#@ddd");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysUser), eq(SysUserValidator.class));
        sysUserService.save(sysUser);
        verify(sysUserDao, only()).insert(any(SysUser.class));
        verify(sysUserRoleDao, only()).insert(anyListOf(SysUserRole.class));
        PowerMockito.verifyStatic(times(3));
        IDUtils.getNextId();
    }

    @Test
    public void testSaveUserTwoRole() {
        PowerMockito.mockStatic(IDUtils.class);
        when(IDUtils.getNextId()).thenReturn(1);
        when(sysUserDao.insert(any(SysUser.class))).thenReturn(1l);
        when(sysUserRoleDao.insert(anyListOf(SysUserRole.class))).thenReturn(2L);
        when(sysUserProfileDao.insert(any(SysUserProfile.class))).thenReturn(1L);
        SysUserRoleVo sysUser = new SysUserRoleVo();
        sysUser.setRoleIds("1,2");
        sysUser.setUsername("z123");
        sysUser.setPassword("2322434");
        sysUser.setFullName("#$#$#$#$");
        sysUser.setEmail("$#$#@ddd");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysUser), eq(SysUserValidator.class));
        sysUserService.save(sysUser);
        verify(sysUserDao, only()).insert(any(SysUser.class));
        verify(sysUserRoleDao, only()).insert(anyListOf(SysUserRole.class));
        PowerMockito.verifyStatic(times(4));
        IDUtils.getNextId();
    }

    @Test(expected = SystemException.class)
    public void testUpdateUserNull() {
        SysUserRoleVo sysUser = new SysUserRoleVo();
        doThrow(new SystemException(BusinessCode.INVALID)).when(validatorBus).validator(same(sysUser), eq(SysUserUpdateValidator.class));
        sysUserService.update(sysUser);

    }

    @Test
    public void testUpdateUser() {
        when(sysUserDao.update(any(SysUser.class))).thenReturn(1L);
        SysUserRoleVo sysUser = new SysUserRoleVo();
        sysUser.setUserId(1);
        sysUser.setUsername("z123");
        sysUser.setPassword("2322434");
        sysUser.setFullName("#$#$#$#$");
        sysUser.setEmail("$#$#@ddd");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysUser), eq(SysUserUpdateValidator.class));
        sysUserService.update(sysUser);
        verify(sysUserDao, only()).update(any(SysUser.class));
        verify(sysUserRoleDao, only()).delete(any(QueryExample.class));
        verify(sysUserRoleDao, never()).insert(anyListOf(SysUserRole.class));
    }

    @Test
    public void testUpdateUserOneRole() {
        PowerMockito.mockStatic(IDUtils.class);
        when(IDUtils.getNextId()).thenReturn(1);
        when(sysUserDao.update(any(SysUser.class))).thenReturn(1L);
        when(sysUserRoleDao.delete(any(QueryExample.class))).thenReturn(1l);
        when(sysUserRoleDao.insert(anyListOf(SysUserRole.class))).thenReturn(1L);
        SysUserRoleVo sysUser = new SysUserRoleVo();
        sysUser.setUserId(1);
        sysUser.setUsername("z123");
//                sysUser.setPassword("2322434");
        sysUser.setFullName("#$#$#$#$");
        sysUser.setEmail("$#$#@ddd");
        sysUser.setRoleIds("1");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return "called with arguments: " + args;
            }
        }).when(validatorBus).validator(same(sysUser), eq(SysUserUpdateValidator.class));
        sysUserService.update(sysUser);
        InOrder inOrder = inOrder(sysUserDao, sysUserRoleDao);
        inOrder.verify(sysUserDao, times(1)).update(any(SysUser.class));
        inOrder.verify(sysUserRoleDao, times(1)).delete(any(QueryExample.class));
        inOrder.verify(sysUserRoleDao, times(1)).insert(anyListOf(SysUserRole.class));
        PowerMockito.verifyStatic(times(1));
        IDUtils.getNextId();
    }
}
