/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package io.github.bannerxu.quartz.service;

import io.github.bannerxu.quartz.model.ScheduleJob;

import java.util.List;

/**
 * @author lgh
 */
public interface ScheduleJobService {

    /**
     * 保存并开始定时任务
     *
     * @param scheduleJob
     */
    void saveAndStart(ScheduleJob scheduleJob);

    /**
     * 更新定时任务
     *
     * @param scheduleJob
     */
    void updateScheduleJob(ScheduleJob scheduleJob);

    /**
     * 批量删除定时任务
     *
     * @param jobIds 需要删除的job id列表
     */
    void deleteBatch(List<Long> jobIds);

    /**
     * 批量更新定时任务状态
     *
     * @param jobIds 需要更新的job id列表
     * @param status 更新后的状态
     */
    void updateBatch(List<Long> jobIds, int status);

    /**
     * 立即执行
     *
     * @param jobIds job id列表
     */
    void run(List<Long> jobIds);

    /**
     * 暂停运行
     *
     * @param jobIds job id列表
     */
    void pause(List<Long> jobIds);

    /**
     * 恢复运行
     *
     * @param jobIds job id列表
     */
    void resume(List<Long> jobIds);

    int checkUpdate(String beanName, String methodName, Long jobId);

    ScheduleJob getById(Long jobId);

    Iterable<ScheduleJob> list();
}
