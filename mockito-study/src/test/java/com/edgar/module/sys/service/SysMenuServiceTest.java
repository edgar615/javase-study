package com.edgar.module.sys.service;

import com.edgar.core.repository.BaseDao;
import com.edgar.core.repository.IDUtils;
import com.edgar.core.repository.QueryExample;
import com.edgar.module.sys.repository.domain.SysMenu;
import com.edgar.module.sys.repository.domain.SysMenuRoute;
import com.edgar.module.sys.repository.domain.SysRoleMenu;
import com.edgar.module.sys.service.impl.SysMenuServiceImpl;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IDUtils.class, QueryExample.class})
public class SysMenuServiceTest {
    @Mock
    private BaseDao<Integer, SysMenu> sysMenuDao;

    @Mock
    private BaseDao<Integer, SysMenuRoute> sysMenuRouteDao;

    @Mock
    private BaseDao<Integer, SysRoleMenu> sysRoleMenuDao;

    private SysMenuServiceImpl sysMenuService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sysMenuService = new SysMenuServiceImpl();
        sysMenuService.setSysMenuDao(sysMenuDao);
        sysMenuService.setSysMenuRouteDao(sysMenuRouteDao);
        sysMenuService.setSysRoleMenuDao(sysRoleMenuDao);
    }

    @Test
    public void testGet() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuName("测试地址");
        when(sysMenuDao.get(anyInt())).thenReturn(sysMenu);
        SysMenu result = sysMenuService.get(1);
        Assert.assertEquals(ToStringBuilder.reflectionToString(sysMenu,
                ToStringStyle.SHORT_PREFIX_STYLE), ToStringBuilder
                .reflectionToString(result, ToStringStyle.SHORT_PREFIX_STYLE));
        verify(sysMenuDao, only()).get(anyInt());
    }

}
