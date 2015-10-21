package com.edgar.module.sys.service;//package com.edgar.module.sys.service;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.only;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//import static org.powermock.api.mockito.PowerMockito.verifyStatic;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import com.edgar.core.command.CommandBus;
//import com.edgar.core.command.CommandResult;
//import com.edgar.core.exception.BusinessCode;
//import com.edgar.core.exception.SystemException;
//import com.edgar.core.repository.Criteria;
//import com.edgar.core.repository.BaseDao;
//import com.edgar.core.repository.IDUtils;
//import com.edgar.core.repository.QueryExample;
//import com.edgar.core.repository.SqlOperator;
//import com.edgar.module.sys.repository.domain.SysRole;
//import com.edgar.module.sys.repository.domain.SysRoleMenu;
//import com.edgar.module.sys.repository.domain.SysRoleResource;
//import com.edgar.module.sys.repository.domain.SysRoleRoute;
//import com.edgar.module.sys.service.MenuPermission;
//import com.edgar.module.sys.service.impl.PermissionServiceImpl;
//import com.edgar.module.sys.service.ResourcePermission;
//import com.edgar.module.sys.service.RoutePermission;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ QueryExample.class, IDUtils.class })
//public class PermissionServiceTest {
//        @Mock
//        private BaseDao<Integer, SysRole> sysRoleDao;
//
//        @Mock
//        private BaseDao<Integer, SysRoleRoute> sysRoleRouteDao;
//
//        @Mock
//        private BaseDao<Integer, SysRoleMenu> sysRoleMenuDao;
//
//        @Mock
//        private BaseDao<Integer, SysRoleResource> sysRoleResourceDao;
//
//        private PermissionServiceImpl permissionService;
//
//        @Mock
//        private CommandBus commandBus;
//
//        @Before
//        public void setUp() {
//                MockitoAnnotations.initMocks(this);
//                permissionService = new PermissionServiceImpl();
//                permissionService.setSysRoleDao(sysRoleDao);
//                permissionService.setSysRoleMenuDao(sysRoleMenuDao);
//                permissionService.setSysRoleResourceDao(sysRoleResourceDao);
//                permissionService.setSysRoleRouteDao(sysRoleRouteDao);
//                permissionService.setCommandBus(commandBus);
//        }
//
//        @Test
//        public void testGetMenu() {
//                QueryExample example = QueryExample.newInstance();
//                mockStatic(QueryExample.class);
//                when(QueryExample.newInstance()).thenReturn(example);
//                when(sysRoleMenuDao.query(any(QueryExample.class))).thenReturn(
//                                new ArrayList<SysRoleMenu>());
//                permissionService.getMenu(1);
//                verify(sysRoleMenuDao, only()).query(any(QueryExample.class));
//                verifyStatic();
//                QueryExample.newInstance();
//                Assert.assertTrue(example.containCriteria(new Criteria("roleId",
//                                SqlOperator.EQ, 1)));
//        }
//
//        @Test
//        public void testGetRoute() {
//                QueryExample example = QueryExample.newInstance();
//                mockStatic(QueryExample.class);
//                when(QueryExample.newInstance()).thenReturn(example);
//                when(sysRoleRouteDao.query(any(QueryExample.class))).thenReturn(
//                                new ArrayList<SysRoleRoute>());
//                permissionService.getRoute(1);
//                verify(sysRoleRouteDao, only()).query(any(QueryExample.class));
//                verifyStatic();
//                QueryExample.newInstance();
//                Assert.assertTrue(example.containCriteria(new Criteria("roleId",
//                                SqlOperator.EQ, 1)));
//        }
//
//        @Test
//        public void testGetResource() {
//                QueryExample example = QueryExample.newInstance();
//                mockStatic(QueryExample.class);
//                when(QueryExample.newInstance()).thenReturn(example);
//                when(sysRoleResourceDao.query(any(QueryExample.class))).thenReturn(
//                                new ArrayList<SysRoleResource>());
//                permissionService.getResource(1);
//                verify(sysRoleResourceDao, only()).query(any(QueryExample.class));
//                verifyStatic();
//                QueryExample.newInstance();
//                Assert.assertTrue(example.containCriteria(new Criteria("roleId",
//                                SqlOperator.EQ, 1)));
//        }
//
//        @Test
//        public void testSaveMenuPermission() {
//                MenuPermission menuPermission = new MenuPermission();
//                menuPermission.setRoleId(1);
//
//                doAnswer(new Answer<CommandResult<Integer>>() {
//
//                        @Override
//                        public CommandResult<Integer> answer(InvocationOnMock invocation)
//                                        throws Throwable {
//                                // Object[] args = invocation.getArguments();
//                                return CommandResult.newInstance(1);
//                        }
//                }).when(commandBus).executeCommand(eq(menuPermission));
//
//                when(sysRoleDao.get(anyInt())).thenReturn(new SysRole());
//
//                permissionService.saveMenuPermission(menuPermission);
//
//                verify(commandBus, only()).executeCommand(eq(menuPermission));
//                verify(sysRoleDao, only()).get(anyInt());
//        }
//
//        @Test
//        public void testSaveMenuPermissionNoRole() {
//
//                MenuPermission menuPermission = new MenuPermission();
//                menuPermission.setRoleId(1);
//
//                doAnswer(new Answer<CommandResult<Integer>>() {
//
//                        @Override
//                        public CommandResult<Integer> answer(InvocationOnMock invocation)
//                                        throws Throwable {
//                                return CommandResult.newInstance(1);
//                        }
//                }).when(commandBus).executeCommand(eq(menuPermission));
//
//                when(sysRoleDao.get(anyInt())).thenReturn(null);
//
//                try {
//                        permissionService.saveMenuPermission(menuPermission);
//                } catch (SystemException e) {
//                        Assert.assertEquals(BusinessCode.EXPIRED, e.getErrorCode());
//                }
//                finally {
//                        verify(commandBus, only()).executeCommand(eq(menuPermission));
//                        verify(sysRoleDao, only()).get(anyInt());
//                }
//        }
//
//        @Test
//        public void testSaveRoutePermission() {
//
//                RoutePermission command = new RoutePermission();
//                command.setRoleId(1);
//
//                doAnswer(new Answer<CommandResult<Integer>>() {
//
//                        @Override
//                        public CommandResult<Integer> answer(InvocationOnMock invocation)
//                                        throws Throwable {
//                                // Object[] args = invocation.getArguments();
//                                return CommandResult.newInstance(1);
//                        }
//                }).when(commandBus).executeCommand(eq(command));
//
//                when(sysRoleDao.get(anyInt())).thenReturn(new SysRole());
//
//                permissionService.saveRoutePermission(command);
//
//                verify(commandBus, only()).executeCommand(eq(command));
//                verify(sysRoleDao, only()).get(anyInt());
//        }
//
//        @Test
//        public void testSaveRoutePermissionNoRole() {
//                RoutePermission command = new RoutePermission();
//                command.setRoleId(1);
//
//                doAnswer(new Answer<CommandResult<Integer>>() {
//
//                        @Override
//                        public CommandResult<Integer> answer(InvocationOnMock invocation)
//                                        throws Throwable {
//                                // Object[] args = invocation.getArguments();
//                                return CommandResult.newInstance(1);
//                        }
//                }).when(commandBus).executeCommand(eq(command));
//
//                when(sysRoleDao.get(anyInt())).thenReturn(null);
//                try {
//                        permissionService.saveRoutePermission(command);
//                } catch (SystemException e) {
//                        Assert.assertEquals(BusinessCode.EXPIRED, e.getErrorCode());
//                }
//
//                verify(commandBus, only()).executeCommand(eq(command));
//                verify(sysRoleDao, only()).get(anyInt());
//
//        }
//
//        @Test
//        public void testSaveRouteOnePermission() {
//
//                RoutePermission command = new RoutePermission();
//                command.setRoleId(1);
//                Set<Integer> permissionIds = new HashSet<Integer>();
//                permissionIds.add(1);
//                command.setRouteIds(permissionIds);
//
//                doAnswer(new Answer<CommandResult<Integer>>() {
//
//                        @Override
//                        public CommandResult<Integer> answer(InvocationOnMock invocation)
//                                        throws Throwable {
//                                // Object[] args = invocation.getArguments();
//                                return CommandResult.newInstance(1);
//                        }
//                }).when(commandBus).executeCommand(eq(command));
//
//                when(sysRoleDao.get(anyInt())).thenReturn(new SysRole());
//                try {
//                        permissionService.saveRoutePermission(command);
//                } catch (SystemException e) {
//                        Assert.assertEquals(BusinessCode.EXPIRED, e.getErrorCode());
//                }
//
//                verify(commandBus, only()).executeCommand(eq(command));
//                verify(sysRoleDao, only()).get(anyInt());
//        }
//
//        @Test
//        public void testSaveResPermission() {
//                ResourcePermission command = new ResourcePermission();
//                command.setRoleId(1);
//
//                doAnswer(new Answer<CommandResult<Integer>>() {
//
//                        @Override
//                        public CommandResult<Integer> answer(InvocationOnMock invocation)
//                                        throws Throwable {
//                                // Object[] args = invocation.getArguments();
//                                return CommandResult.newInstance(1);
//                        }
//                }).when(commandBus).executeCommand(eq(command));
//
//                when(sysRoleDao.get(anyInt())).thenReturn(new SysRole());
//
//                permissionService.saveResourcePermission(command);
//
//                verify(commandBus, only()).executeCommand(eq(command));
//                verify(sysRoleDao, only()).get(anyInt());
//
//                QueryExample example = QueryExample.newInstance();
//                mockStatic(QueryExample.class);
//                when(QueryExample.newInstance()).thenReturn(example);
//                verify(sysRoleDao, only()).get(anyInt());
//        }
//
//        @Test
//        public void testSaveResPermissionNoRole() {
//
//                ResourcePermission command = new ResourcePermission();
//                command.setRoleId(1);
//
//                doAnswer(new Answer<CommandResult<Integer>>() {
//
//                        @Override
//                        public CommandResult<Integer> answer(InvocationOnMock invocation)
//                                        throws Throwable {
//                                // Object[] args = invocation.getArguments();
//                                return CommandResult.newInstance(1);
//                        }
//                }).when(commandBus).executeCommand(eq(command));
//
//                when(sysRoleDao.get(anyInt())).thenReturn(null);
//                try {
//                        permissionService.saveResourcePermission(command);
//                } catch (SystemException e) {
//                        Assert.assertEquals(BusinessCode.EXPIRED, e.getErrorCode());
//                }
//                finally {
//
//                        verify(commandBus, only()).executeCommand(eq(command));
//                        verify(sysRoleDao, only()).get(anyInt());
//                }
//
//        }
//
//        @Test
//        public void testSaveResOnePermission() {
//                ResourcePermission command = new ResourcePermission();
//                command.setRoleId(1);
//                Set<Integer> permissionIds = new HashSet<Integer>();
//                permissionIds.add(1);
//                command.setResourceIds(permissionIds);
//
//                doAnswer(new Answer<CommandResult<Integer>>() {
//
//                        @Override
//                        public CommandResult<Integer> answer(InvocationOnMock invocation)
//                                        throws Throwable {
//                                // Object[] args = invocation.getArguments();
//                                return CommandResult.newInstance(1);
//                        }
//                }).when(commandBus).executeCommand(eq(command));
//
//                when(sysRoleDao.get(anyInt())).thenReturn(new SysRole());
//
//                permissionService.saveResourcePermission(command);
//
//                verify(commandBus, only()).executeCommand(eq(command));
//                verify(sysRoleDao, only()).get(anyInt());
//
//        }
//
//}
