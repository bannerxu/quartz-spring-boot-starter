package io.github.lib.quartz.config;


import io.github.lib.quartz.model.IScheduleJob;
import io.github.lib.quartz.model.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * 定时任务工具类
 */
@Component
@AllArgsConstructor
public class ScheduleManager {
    private final static String JOB_NAME_PREFIX = "TASK_";

    private final Scheduler scheduler;

    /**
     * 获取触发器key
     */
    private TriggerKey getTriggerKey(IScheduleJob IScheduleJob) {
        return TriggerKey.triggerKey(JOB_NAME_PREFIX + IScheduleJob.getJobId());
    }

    /**
     * 获取jobKey
     */
    private JobKey getJobKey(IScheduleJob IScheduleJob) {
        return JobKey.jobKey(JOB_NAME_PREFIX + IScheduleJob.getJobId());
    }

    /**
     * 获取表达式触发器
     */
    public CronTrigger getCronTrigger(IScheduleJob IScheduleJob) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(IScheduleJob));
        } catch (SchedulerException e) {
            throw new RuntimeException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 创建定时任务
     */
    public void createScheduleJob(IScheduleJob iScheduleJob) {
        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).withIdentity(getJobKey(iScheduleJob)).build();

            //表达式调度构建器，可以根据scheduleJob修改withMisfireHandling方法，但是使用异步执行定时任务，没必要
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(iScheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionFireAndProceed();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(iScheduleJob)).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(QuartzJob.JOB_PARAM_KEY, iScheduleJob);

            scheduler.scheduleJob(jobDetail, trigger);

            //暂停任务
            if (iScheduleJob.getStatus().equals(ScheduleStatus.PAUSE)) {
                pauseJob(iScheduleJob);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     */
    public void updateScheduleJob(IScheduleJob IScheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(IScheduleJob);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(IScheduleJob.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();

            CronTrigger trigger = getCronTrigger(IScheduleJob);

            // 如果定时任务不存在，则创建定时任务
            if (trigger == null) {
                createScheduleJob(IScheduleJob);
                return;
            }

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //参数
            trigger.getJobDataMap().put(QuartzJob.JOB_PARAM_KEY, IScheduleJob);

            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if (IScheduleJob.getStatus().equals(ScheduleStatus.PAUSE)) {
                pauseJob(IScheduleJob);
            }

        } catch (SchedulerException e) {
            throw new RuntimeException("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     */
    public void run(IScheduleJob IScheduleJob) {
        try {
            //参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(QuartzJob.JOB_PARAM_KEY, IScheduleJob);

            scheduler.triggerJob(getJobKey(IScheduleJob), dataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException("立即执行定时任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public void pauseJob(IScheduleJob IScheduleJob) {
        try {
            scheduler.pauseJob(getJobKey(IScheduleJob));
        } catch (SchedulerException e) {
            throw new RuntimeException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public void resumeJob(IScheduleJob IScheduleJob) {
        try {
            scheduler.resumeJob(getJobKey(IScheduleJob));
        } catch (SchedulerException e) {
            throw new RuntimeException("恢复定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     */
    public void deleteScheduleJob(IScheduleJob IScheduleJob) {
        try {
            // 停止触发器
            scheduler.pauseTrigger(getTriggerKey(IScheduleJob));
            //移除触发器
            scheduler.unscheduleJob(getTriggerKey(IScheduleJob));
            //删除任务
            scheduler.deleteJob(getJobKey(IScheduleJob));
        } catch (SchedulerException e) {
            throw new RuntimeException("删除定时任务失败", e);
        }
    }

}
