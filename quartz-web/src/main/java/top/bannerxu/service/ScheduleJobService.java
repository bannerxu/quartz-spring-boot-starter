package top.bannerxu.service;

import io.github.lib.quartz.core.JobProvider;
import io.github.lib.quartz.model.IScheduleJob;
import org.springframework.stereotype.Service;
import top.bannerxu.entity.dao.ScheduleJobDao;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleJobService implements JobProvider {

    @Resource
    private ScheduleJobDao scheduleJobDao;

    @Override
    public List<IScheduleJob> findAll() {
        return new ArrayList<>(scheduleJobDao.findAll());
    }

    @Override
    public List<IScheduleJob> findAllById(List<String> jobIds) {
        return new ArrayList<>(scheduleJobDao.findByJobIdIn(jobIds));
    }
}
