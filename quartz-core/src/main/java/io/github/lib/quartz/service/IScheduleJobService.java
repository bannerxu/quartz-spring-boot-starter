package io.github.lib.quartz.service;

import io.github.lib.quartz.model.IScheduleJob;

public interface IScheduleJobService {
    /**
     * 立刻执行任务
     */
    void run(IScheduleJob iScheduleJob);
}
