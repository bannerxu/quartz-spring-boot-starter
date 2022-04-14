package io.github.lib.quartz.model;


import io.github.lib.quartz.enums.ScheduleStatus;

/**
 * 持久化的任务
 */
public interface IScheduleJob {

    /**
     * 任务类名
     */
    String getBeanName();


    /**
     * 任务参数
     */
    String getParams();


    /**
     * 任务方法
     */
    String getMethodName();

    /**
     * 任务ID
     */
    String getJobId();

    /**
     * cron表达式
     */
    String getCronExpression();

    /**
     * 任务状态
     */
    ScheduleStatus getStatus();
}