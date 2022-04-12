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


import io.github.bannerxu.quartz.model.ScheduleJobLog;
import io.github.bannerxu.quartz.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 定时任务日志
 *
 * @author lgh
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     */
    @GetMapping
    public ResponseEntity<List<ScheduleJobLog>> page(Long jobId) {
        //IPage<ScheduleJobLog> list = scheduleJobLogService.page(page,new LambdaQueryWrapper<ScheduleJobLog>().eq(jobId != null,ScheduleJobLog::getJobId,jobId));
        //return ResponseEntity.ok(list);
        return null;
    }
}
