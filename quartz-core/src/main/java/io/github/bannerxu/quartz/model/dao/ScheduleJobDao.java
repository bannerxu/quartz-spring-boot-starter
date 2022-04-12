package io.github.bannerxu.quartz.model.dao;

import io.github.bannerxu.quartz.model.ScheduleJob;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleJobDao extends CrudRepository<ScheduleJob, Long> {

    int countByBeanNameAndMethodNameAndJobIdNot(String beanName, String methodName, Long jobId);
}
