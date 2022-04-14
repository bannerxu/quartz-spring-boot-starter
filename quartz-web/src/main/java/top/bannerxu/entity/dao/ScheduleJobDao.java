package top.bannerxu.entity.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import top.bannerxu.entity.ScheduleJob;

public interface ScheduleJobDao extends JpaRepository<ScheduleJob, Long> {
}
