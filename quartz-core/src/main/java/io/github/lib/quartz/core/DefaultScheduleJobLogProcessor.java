package io.github.lib.quartz.core;

import io.github.lib.quartz.model.ScheduleJobLog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultScheduleJobLogProcessor implements JobLogProcessor {
    @Override
    public void logProcessor(ScheduleJobLog scheduleJobLog) {
        log.info("[MyDefaultScheduleJobLogProcessor] ==> {}", scheduleJobLog);
    }
}
