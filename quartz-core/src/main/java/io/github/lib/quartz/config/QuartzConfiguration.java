package io.github.lib.quartz.config;

import io.github.lib.quartz.core.DefaultScheduleJobLogProcessor;
import io.github.lib.quartz.core.DefaultScheduleJobProvider;
import io.github.lib.quartz.core.JobLogProcessor;
import io.github.lib.quartz.core.JobProvider;
import io.github.lib.quartz.listener.SysJobListener;
import io.github.lib.quartz.service.impl.JobService;
import io.github.lib.quartz.util.SpringContextUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Quartz config
 */
@Configuration
public class QuartzConfiguration implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.setApplicationContext(applicationContext);
    }

    @Bean
    public JobService jobService(JobProvider provider, ScheduleManager scheduleManager) {
        if (provider == null) {
            provider = new DefaultScheduleJobProvider();
        }
        return new JobService(provider, scheduleManager);
    }

    @Bean
    public SysJobListener sysJobListener(JobLogProcessor processor) {
        if (processor == null) {
            processor = new DefaultScheduleJobLogProcessor();
        }
        return new SysJobListener(processor);
    }
}
