package io.github.lib.quartz.core;

import io.github.lib.quartz.model.ScheduleJobLog;

/**
 * 提供一个处理者，可以是实现该处理着，自行处理任务执行日志
 */
public interface ScheduleJobLogProcessor {

    void logProcessor(ScheduleJobLog scheduleJobLog);
}
