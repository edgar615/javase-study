package com.edgar.module.sys.validator;

import com.edgar.core.exception.SystemException;
import com.edgar.core.validator.ValidatorStrategy;
import com.edgar.module.sys.repository.domain.SysRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Administrator on 14-9-11.
 */
public class SysRoleUpdateValidatorTest {

    private ValidatorStrategy validator;

    @Before
    public void setUp() {
        validator = new SysRoleUpdateValidator();
    }

    @Test(expected = SystemException.class)
    public void testNull() {
        SysRole sysRole = new SysRole();
        try {
            validator.validator(sysRole);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertFalse(map.containsKey("roleName"));
            Assert.assertFalse(map.containsKey("roleCode"));
            Assert.assertTrue(map.containsKey("roleId"));
            throw e;
        }
    }

    @Test(expected = SystemException.class)
    public void testLong() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(1);
        sysRole.setRoleCode("012345678901234567890123456789123");
        try {
            validator.validator(sysRole);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertFalse(map.containsKey("roleName"));
            Assert.assertTrue(map.containsKey("roleCode"));
            Assert.assertFalse(map.containsKey("roleId"));
            throw e;
        }
    }

    @Test(expected = SystemException.class)
    public void testPattern() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("root");
        sysRole.setRoleName("root");
        sysRole.setRoleId(1);
        try {
            validator.validator(sysRole);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertTrue(map.containsKey("roleName"));
            Assert.assertFalse(map.containsKey("roleCode"));
            Assert.assertFalse(map.containsKey("roleId"));
            throw e;
        }
    }

    @Test
    public void testOK() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("root");
        sysRole.setRoleName("root1");
        sysRole.setRoleId(1);
        validator.validator(sysRole);
    }

}
