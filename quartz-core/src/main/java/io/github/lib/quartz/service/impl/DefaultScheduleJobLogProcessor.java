package io.github.lib.quartz.service.impl;

import io.github.lib.quartz.core.ScheduleJobLogProcessor;
import io.github.lib.quartz.model.ScheduleJobLog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultScheduleJobLogProcessor implements ScheduleJobLogProcessor {
    @Override
    public void logProcessor(ScheduleJobLog scheduleJobLog) {
        log.info("[DefaultScheduleJobLogProcessor] ==> {}", scheduleJobLog);
    }
}
