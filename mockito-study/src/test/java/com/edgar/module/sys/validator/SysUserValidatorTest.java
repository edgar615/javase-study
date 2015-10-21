package com.edgar.module.sys.validator;

import com.edgar.core.exception.SystemException;
import com.edgar.core.validator.ValidatorStrategy;
import com.edgar.module.sys.vo.SysUserRoleVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Administrator on 14-9-11.
 */
public class SysUserValidatorTest {

    private ValidatorStrategy validator;

    @Before
    public void setUp() {
        validator = new SysUserValidator();
    }

    @Test(expected = SystemException.class)
    public void testNull() {
        SysUserRoleVo sysUser = new SysUserRoleVo();
        try {
            validator.validator(sysUser);
        } catch (SystemException e) {
            Map<String, Object> propertyMap = e.getPropertyMap();
            Assert.assertTrue(propertyMap.containsKey("username"));
            Assert.assertTrue(propertyMap.containsKey("fullName"));
            Assert.assertTrue(propertyMap.containsKey("password"));
            throw e;
        }
    }

    @Test(expected = SystemException.class)
    public void testLong() {
        SysUserRoleVo sysUser = new SysUserRoleVo();
        sysUser.setUsername("z012345678901234567890123456789123012345678901234567890123456789123");
        sysUser.setPassword("012345678901234567890123456789123");
        sysUser.setFullName("012345678901234567890123456789123");
        sysUser.setEmail("012345678901234567890123456789123012345678901234567890123456789123");
        try {
            validator.validator(sysUser);
        } catch (SystemException e) {
            Map<String, Object> propertyMap = e.getPropertyMap();
            Assert.assertTrue(propertyMap.containsKey("username"));
            Assert.assertTrue(propertyMap.containsKey("fullName"));
            Assert.assertTrue(propertyMap.containsKey("password"));
            Assert.assertTrue(propertyMap.containsKey("email"));
            throw e;
        }
    }

    @Test(expected = SystemException.class)
    public void testPattern() {
        SysUserRoleVo sysUser = new SysUserRoleVo();
        sysUser.setUsername("123");
        sysUser.setPassword("2322434");
        sysUser.setFullName("#$#$#$#$");
        sysUser.setEmail("$#$#@ddd.");
        try {
            validator.validator(sysUser);
        } catch (SystemException e) {
            Map<String, Object> propertyMap = e.getPropertyMap();
            Assert.assertTrue(propertyMap.containsKey("username"));
            Assert.assertFalse(propertyMap.containsKey("fullName"));
            Assert.assertFalse(propertyMap.containsKey("password"));
            Assert.assertTrue(propertyMap.containsKey("email"));
            throw e;
        }
    }

}
