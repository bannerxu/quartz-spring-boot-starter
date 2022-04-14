package io.github.lib.quartz.core;

import io.github.lib.quartz.model.IScheduleJob;

import java.util.List;

/**
 * Schedule数据的提供者
 */
public interface ScheduleJobProvider {
    /**
     * 获取全部的任务
     */
    List<IScheduleJob> findAll();

    List<IScheduleJob> findAllById(List<String> jobIds);
}
