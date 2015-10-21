package com.edgar.module.sys.validator;

import com.edgar.core.exception.SystemException;
import com.edgar.core.validator.ValidatorStrategy;
import com.edgar.module.sys.repository.domain.SysJob;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Administrator on 14-9-11.
 */
public class SysJobValidatorTest {

    private ValidatorStrategy validator;

    @Before
    public void setUp() {
        validator = new SysJobValidator();
    }

    @Test(expected = SystemException.class)
    public void testSaveNull() {
        SysJob sysJob = new SysJob();
        try {
            validator.validator(sysJob);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertTrue(map.containsKey("jobName"));
            Assert.assertTrue(map.containsKey("clazzName"));
            Assert.assertTrue(map.containsKey("cron"));
            Assert.assertFalse(map.containsKey("jobId"));
            throw e;
        }
    }

    @Test(expected = SystemException.class)
    public void testSaveLong() {
        SysJob sysJob = new SysJob();
        sysJob.setJobName("012345678901234567890123456789123");
        sysJob.setCron("012345678901234567890123456789123");
        sysJob.setClazzName("01234567890123456789012345678901234567890123456789012345678901234");
        try {
            validator.validator(sysJob);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertTrue(map.containsKey("jobName"));
            Assert.assertTrue(map.containsKey("clazzName"));
            Assert.assertTrue(map.containsKey("cron"));
            Assert.assertFalse(map.containsKey("jobId"));
            throw e;
        }

    }

    @Test(expected = SystemException.class)
    public void testSavePattern() {
        SysJob sysJob = new SysJob();
        sysJob.setJobName("123");
        sysJob.setClazzName("com2");
        sysJob.setCron("1");
        try {
            validator.validator(sysJob);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertFalse(map.containsKey("jobName"));
            Assert.assertTrue(map.containsKey("clazzName"));
            Assert.assertFalse(map.containsKey("cron"));
            Assert.assertFalse(map.containsKey("jobId"));
            throw e;
        }

    }

}
