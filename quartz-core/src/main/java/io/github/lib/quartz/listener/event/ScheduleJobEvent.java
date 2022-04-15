package io.github.lib.quartz.listener.event;

import io.github.lib.quartz.model.IScheduleJob;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定时任务事件
 */
@Getter
@AllArgsConstructor
public class ScheduleJobEvent {

    private final IScheduleJob IScheduleJob;
}
