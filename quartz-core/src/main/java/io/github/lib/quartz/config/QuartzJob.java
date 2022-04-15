package io.github.lib.quartz.config;


import io.github.lib.quartz.listener.event.ScheduleJobEvent;
import io.github.lib.quartz.model.IScheduleJob;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

/**
 * 该类将会被org.springframework.scheduling.quartz.SpringBeanJobFactory 实例化
 * 并使@Autowired 生效
 */
@Slf4j
@DisallowConcurrentExecution
public class QuartzJob implements Job {

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @SneakyThrows
    public void execute(JobExecutionContext jobExecutionContext) {
        IScheduleJob sysJob = (IScheduleJob) jobExecutionContext.getMergedJobDataMap().get(JOB_PARAM_KEY);
        applicationEventPublisher.publishEvent(new ScheduleJobEvent(sysJob));
    }

}
