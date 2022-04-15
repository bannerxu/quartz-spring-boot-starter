package top.bannerxu.controller;

import io.github.lib.quartz.service.impl.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.bannerxu.entity.dao.ScheduleJobDao;

import javax.annotation.Resource;

@RestController
public class HelloController {


    @Resource
    private JobService jobService;
    @Resource
    private ScheduleJobDao scheduleJobDao;


    @GetMapping("/run/{jobId}")
    public void run(@PathVariable String jobId) {

        jobService.run(scheduleJobDao.findByJobId(jobId));
    }
}
