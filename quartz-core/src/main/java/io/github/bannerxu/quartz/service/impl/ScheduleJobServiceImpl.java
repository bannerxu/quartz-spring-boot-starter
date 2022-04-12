/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package io.github.bannerxu.quartz.service.impl;

import io.github.bannerxu.quartz.config.ScheduleManager;
import io.github.bannerxu.quartz.enums.ScheduleStatus;
import io.github.bannerxu.quartz.model.ScheduleJob;
import io.github.bannerxu.quartz.model.dao.ScheduleJobDao;
import io.github.bannerxu.quartz.service.ScheduleJobService;
import org.quartz.CronTrigger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author lgh
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private ScheduleJobDao scheduleJobDao;
    @Autowired
    private ScheduleManager scheduleManager;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        scheduleJobDao.findAll().forEach(scheduleJob -> {
            CronTrigger trigger = scheduleManager.getCronTrigger(scheduleJob);
            // 如果定时任务不存在，则创建定时任务
            if (trigger == null) {
                scheduleManager.createScheduleJob(scheduleJob);
            } else if (ScheduleStatus.NORMAL.getType().equals(scheduleJob.getStatus())) {
                scheduleManager.resumeJob(scheduleJob);
            } else if (ScheduleStatus.PAUSE.getType().equals(scheduleJob.getStatus())) {
                scheduleManager.pauseJob(scheduleJob);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAndStart(ScheduleJob scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(ScheduleStatus.NORMAL.getType());
        scheduleJobDao.save(scheduleJob);

        scheduleManager.createScheduleJob(scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateScheduleJob(ScheduleJob scheduleJob) {
        scheduleManager.updateScheduleJob(scheduleJob);
        scheduleJobDao.findById(scheduleJob.getJobId()).ifPresent(it -> BeanUtils.copyProperties(scheduleJob, it));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> jobIds) {
        scheduleJobDao.findAllById(jobIds).forEach(scheduleJob -> {
            scheduleManager.deleteScheduleJob(scheduleJob);
            scheduleJobDao.delete(scheduleJob);

        });
    }

    @Override
    @Transactional
    public void updateBatch(List<Long> jobIds, int status) {
        scheduleJobDao.findAllById(jobIds).forEach(scheduleJob -> scheduleJob.setStatus(status));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            scheduleManager.run(scheduleJobDao.findById(jobId).orElse(null));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(List<Long> jobIds) {
        scheduleJobDao.findAllById(jobIds).forEach(scheduleJob -> scheduleManager.pauseJob(scheduleJob));
        updateBatch(jobIds, ScheduleStatus.PAUSE.getType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(List<Long> jobIds) {
        scheduleJobDao.findAllById(jobIds).forEach(scheduleJob -> scheduleManager.resumeJob(scheduleJob));
        updateBatch(jobIds, ScheduleStatus.NORMAL.getType());
    }

    @Override
    public int checkUpdate(String beanName, String methodName, Long jobId) {
       return scheduleJobDao.countByBeanNameAndMethodNameAndJobIdNot(beanName, methodName, jobId);
    }

    @Override
    public ScheduleJob getById(Long jobId) {
        return scheduleJobDao.findById(jobId).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Iterable<ScheduleJob> list() {
        return scheduleJobDao.findAll();
    }
}
