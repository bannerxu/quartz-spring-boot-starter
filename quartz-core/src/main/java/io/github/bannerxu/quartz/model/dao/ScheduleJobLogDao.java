package io.github.bannerxu.quartz.model.dao;

import io.github.bannerxu.quartz.model.ScheduleJob;
import io.github.bannerxu.quartz.model.ScheduleJobLog;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleJobLogDao extends CrudRepository<ScheduleJobLog, Long> {
}
