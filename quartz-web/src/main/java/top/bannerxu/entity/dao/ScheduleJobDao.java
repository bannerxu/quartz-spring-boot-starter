package top.bannerxu.entity.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import top.bannerxu.entity.ScheduleJob;

import java.util.List;

public interface ScheduleJobDao extends JpaRepository<ScheduleJob, Long> {

    List<ScheduleJob> findByJobIdIn(List<String> jobIds);

    ScheduleJob findByJobId(String jobId);
}
