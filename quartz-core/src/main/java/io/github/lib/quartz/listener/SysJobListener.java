package io.github.lib.quartz.listener;


import io.github.lib.quartz.core.ScheduleJobLogProcessor;
import io.github.lib.quartz.event.ScheduleJobEvent;
import io.github.lib.quartz.model.IScheduleJob;
import io.github.lib.quartz.model.ScheduleJobLog;
import io.github.lib.quartz.util.SpringBeanTaskUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;


/**
 * 异步监听定时任务事件，解决job线程无故丢失的问题
 */
@Slf4j
@Component
@EnableAsync
@AllArgsConstructor
public class SysJobListener {
    @Resource
    private ScheduleJobLogProcessor scheduleJobLogProcessor;

    /**
     * 将 Exception 转化为 String
     */
    public static String getExceptionToString(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @Async
    @EventListener(ScheduleJobEvent.class)
    public void scheduleJobEventListener(ScheduleJobEvent event) {
        IScheduleJob IScheduleJob = event.getIScheduleJob();
        //数据库保存执行记录
        ScheduleJobLog jobLog = new ScheduleJobLog();
        jobLog.setJobId(IScheduleJob.getJobId());
        jobLog.setBeanName(IScheduleJob.getBeanName());
        jobLog.setMethodName(IScheduleJob.getMethodName());
        jobLog.setParams(IScheduleJob.getParams());
        jobLog.setCreateTime(new Date());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            log.info("任务准备执行，任务ID：" + IScheduleJob.getJobId());

            SpringBeanTaskUtil.invokeMethod(IScheduleJob);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes((int) times);
            jobLog.setSuccess(true);
            StringBuilder sb = new StringBuilder();

            log.info(sb.append("任务执行完毕，任务ID：").append(jobLog.getJobId()).append("  总共耗时：").append(times).append("毫秒").toString());
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + IScheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes((int) times);
            jobLog.setSuccess(false);
            String exceptionToString = getExceptionToString(e);
            if (exceptionToString.length() > 2000) {
                exceptionToString = exceptionToString.substring(0, 2000);
            }
            jobLog.setError(exceptionToString);
        } finally {
            scheduleJobLogProcessor.logProcessor(jobLog);
        }
    }
}
