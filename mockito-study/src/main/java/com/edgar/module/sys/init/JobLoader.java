package com.edgar.module.sys.init;

import com.edgar.core.exception.SystemException;
import com.edgar.core.init.AppInitializer;
import com.edgar.core.init.Initialization;
import com.edgar.core.job.JobAdpater;
import com.edgar.core.job.JobScheduler;
import com.edgar.core.util.ExceptionFactory;
import com.edgar.module.sys.repository.domain.SysJob;
import com.edgar.module.sys.service.SysJobService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobLoader implements Initialization {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);

    @Autowired
    private SysJobService sysJobService;

    @Autowired
    private JobScheduler jobSchedulerService;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void init() throws SystemException {
        LOGGER.info("/*****load job start*****/");
        List<SysJob> sysJobs = sysJobService.findEnabledJob();
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            for (SysJob sysJob : sysJobs) {
                JobAdpater job = new JobAdpater();
                job.setClazzName(sysJob.getClazzName());
                job.setCron(sysJob.getCron());
                jobSchedulerService.addJob(job, scheduler);
            }
            scheduler.start();
            LOGGER.info("/*****load job finished*****/");
        } catch (SchedulerException e) {
            LOGGER.error("load job failed");
            throw ExceptionFactory.job();
        }
    }

}
