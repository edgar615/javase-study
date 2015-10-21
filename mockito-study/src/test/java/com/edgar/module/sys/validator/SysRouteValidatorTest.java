package com.edgar.module.sys.validator;

import com.edgar.core.exception.SystemException;
import com.edgar.core.validator.ValidatorStrategy;
import com.edgar.module.sys.repository.domain.SysRoute;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Administrator on 14-9-11.
 */
public class SysRouteValidatorTest {

    private ValidatorStrategy validator;

    @Before
    public void setUp() {
        validator = new SysRouteValidator();
    }

    @Test(expected = SystemException.class)
    public void testNull() {
        SysRoute sysRoute = new SysRoute();
        try {
            validator.validator(sysRoute);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertTrue(map.containsKey("name"));
            Assert.assertTrue(map.containsKey("url"));
            throw e;
        }
    }

    @Test(expected = SystemException.class)
    public void testLong() {
        SysRoute sysRoute = new SysRoute();
        sysRoute.setUrl("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        sysRoute.setName("012345678901234567890123456789123");
        try {
            validator.validator(sysRoute);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertTrue(map.containsKey("name"));
            Assert.assertTrue(map.containsKey("url"));
            throw e;
        }
    }

    @Test(expected = SystemException.class)
    public void testPattern() {
        SysRoute sysRoute = new SysRoute();
        sysRoute.setUrl("123");
        sysRoute.setName("1233");
        try {
            validator.validator(sysRoute);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertTrue(map.containsKey("url"));
            throw e;
        }
    }

    @Test
    public void testOk() {
        SysRoute sysRoute = new SysRoute();
        sysRoute.setUrl("/123");
        sysRoute.setName("1233");
        validator.validator(sysRoute);
    }

}
