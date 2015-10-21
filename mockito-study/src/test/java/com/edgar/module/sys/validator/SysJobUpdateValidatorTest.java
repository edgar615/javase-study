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
public class SysJobUpdateValidatorTest {

    private ValidatorStrategy validator;

    @Before
    public void setUp() {
        validator = new SysJobUpdateValidator();
    }

    @Test(expected = SystemException.class)
    public void testUpdateNull() {
        SysJob sysJob = new SysJob();
        try {
            validator.validator(sysJob);
        } catch (SystemException e) {
            Map<String, Object> map = e.getPropertyMap();
            Assert.assertFalse(map.containsKey("jobName"));
            Assert.assertFalse(map.containsKey("clazzName"));
            Assert.assertFalse(map.containsKey("cron"));
            Assert.assertTrue(map.containsKey("jobId"));
            throw e;
        }

    }

    @Test(expected = SystemException.class)
    public void testUpdateLong() {
        SysJob sysJob = new SysJob();
        sysJob.setJobId(1);
        sysJob.setJobName("012345678901234567890123456789123");
        sysJob.setCron("012345678901234567890123456789123");
        sysJob.setClazzName("1");
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

    @Test
    public void testUpdateEnabled() {
        SysJob sysJob = new SysJob();
        sysJob.setJobId(1);
        sysJob.setEnabled(true);
        sysJob.setJobName("测试作业");
        validator.validator(sysJob);
    }

    @Test
    public void testUpdateDisabled() {
        SysJob sysJob = new SysJob();
        sysJob.setJobId(1);
        sysJob.setCron("0/1 * * * * ? *");
        sysJob.setEnabled(false);
        sysJob.setJobName("测试作业");
        validator.validator(sysJob);

    }

}
