package io.github.lib.quartz.service.impl;

import io.github.lib.quartz.config.ScheduleManager;
import io.github.lib.quartz.core.ScheduleJobProvider;
import io.github.lib.quartz.enums.ScheduleStatus;
import io.github.lib.quartz.service.IScheduleJobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@AllArgsConstructor
public class QuartzService implements IScheduleJobService {

    private ScheduleJobProvider scheduleJobProvider;
    private ScheduleManager scheduleManager;

    @PostConstruct
    public void a() {
        log.info("ssssssssssssssssssssssssssssssssss");
    }

    @PostConstruct
    public void init() {
        scheduleJobProvider.findAll().forEach(scheduleJob -> {
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
}
