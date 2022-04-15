package top.bannerxu.service;

import io.github.lib.quartz.core.JobLogProcessor;
import io.github.lib.quartz.model.ScheduleJobLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleJobLogProcessor implements JobLogProcessor {
    @Override
    public void logProcessor(ScheduleJobLog scheduleJobLog) {
        log.info("[ScheduleJobLogProcessor] ==> {}", scheduleJobLog);
    }
}
