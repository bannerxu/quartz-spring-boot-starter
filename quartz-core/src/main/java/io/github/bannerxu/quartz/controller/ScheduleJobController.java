/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package io.github.bannerxu.quartz.controller;


import io.github.bannerxu.quartz.model.ScheduleJob;
import io.github.bannerxu.quartz.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 定时任务
 *
 * @author lgh
 */
@Slf4j
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     */
    @GetMapping("/list")
    public ResponseEntity<Iterable<ScheduleJob>> page() {
        Iterable<ScheduleJob> list = scheduleJobService.list();
        return ResponseEntity.ok(list);
    }

    /**
     * 定时任务信息
     */
    @GetMapping("/info/{jobId}")
    public ResponseEntity<ScheduleJob> info(@PathVariable("jobId") Long jobId) {
        ScheduleJob schedule = scheduleJobService.getById(jobId);
        return ResponseEntity.ok(schedule);
    }

    /**
     * 保存定时任务
     */
    @PostMapping
    public void save(@RequestBody @Valid ScheduleJob scheduleJob) {

        final int dbAlikeCount = scheduleJobService.checkUpdate(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getJobId());
        if (dbAlikeCount > 0) {
            throw new IllegalStateException("定时任务已存在");
        }
        scheduleJobService.saveAndStart(scheduleJob);
    }

    /**
     * 修改定时任务
     */
    @PutMapping
    public void update(@RequestBody @Valid ScheduleJob scheduleJob) {
        final int dbAlikeCount = scheduleJobService.checkUpdate(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getJobId());

        if (dbAlikeCount > 0) {
            throw new IllegalStateException("定时任务已存在");
        }

        scheduleJobService.updateScheduleJob(scheduleJob);
    }

    /**
     * 删除定时任务
     */
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody List<Long> jobIds) {
        scheduleJobService.deleteBatch(jobIds);
        return ResponseEntity.ok().build();
    }

    /**
     * 立即执行任务
     */
    @PostMapping("/run")
    public ResponseEntity<Void> run(@RequestBody List<Long> jobIds) {
        scheduleJobService.run(jobIds);
        return ResponseEntity.ok().build();
    }

    /**
     * 暂停定时任务
     */
    @PostMapping("/pause")
    public ResponseEntity<Void> pause(@RequestBody List<Long> jobIds) {
        scheduleJobService.pause(jobIds);
        return ResponseEntity.ok().build();
    }

    /**
     * 恢复定时任务
     */
    @PostMapping("/resume")
    public ResponseEntity<Void> resume(@RequestBody List<Long> jobIds) {
        scheduleJobService.resume(jobIds);
        return ResponseEntity.ok().build();
    }

}
