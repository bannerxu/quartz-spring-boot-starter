package io.github.lib.quartz.service.impl;

import io.github.lib.quartz.config.ScheduleManager;
import io.github.lib.quartz.core.JobProvider;
import io.github.lib.quartz.model.IScheduleJob;
import io.github.lib.quartz.model.enums.ScheduleStatus;
import io.github.lib.quartz.service.IScheduleJobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
public class JobService implements IScheduleJobService {

    private JobProvider jobProvider;
    private ScheduleManager scheduleManager;

    @PostConstruct
    public void init() {
        log.info("初始化，将任务加载到ScheduleManager...");
        jobProvider.findAll().forEach(scheduleJob -> {
            CronTrigger trigger = scheduleManager.getCronTrigger(scheduleJob);
            // 如果定时任务不存在，则创建定时任务
            if (trigger == null) {
                scheduleManager.createScheduleJob(scheduleJob);
            } else if (ScheduleStatus.NORMAL.equals(scheduleJob.getStatus())) {
                scheduleManager.resumeJob(scheduleJob);
            } else if (ScheduleStatus.PAUSE.equals(scheduleJob.getStatus())) {
                scheduleManager.pauseJob(scheduleJob);
            }
        });
    }


    public void run(IScheduleJob iScheduleJob) {
        scheduleManager.run(iScheduleJob);
    }
}
