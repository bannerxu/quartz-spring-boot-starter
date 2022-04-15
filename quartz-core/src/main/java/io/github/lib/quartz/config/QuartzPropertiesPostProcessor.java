package io.github.lib.quartz.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.quartz.JobStoreType;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 定义Bean初始化前后的动作
 */
@Service
public class QuartzPropertiesPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof QuartzProperties) {

            System.out.println("这里进行数据重置");
            QuartzProperties quartzProperties = (QuartzProperties) bean;
            quartzProperties.setJobStoreType(JobStoreType.JDBC);
            Map<String, String> properties = quartzProperties.getProperties();

            properties.put("org.quartz.scheduler.instanceName", "clusteredScheduler");
            properties.put("org.quartz.scheduler.instanceId", "AUTO");
            properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
            properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
            properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
            properties.put("org.quartz.jobStore.isClustered", "true");
            properties.put("org.quartz.jobStore.clusterCheckinInterval", "10000");
            properties.put("org.quartz.jobStore.useProperties", "false");
            properties.put("org.quartz.jobStore.misfireThreshold", "12000");
            properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
            properties.put("org.quartz.threadPool.threadCount", "3");
            properties.put("org.quartz.threadPool.threadPriority", "5");
            properties.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
            return quartzProperties;
        } else {
            return bean;
        }
    }

}