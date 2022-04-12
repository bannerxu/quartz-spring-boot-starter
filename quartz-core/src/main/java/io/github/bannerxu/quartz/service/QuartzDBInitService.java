package io.github.bannerxu.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class QuartzDBInitService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    String SCHEDULE_JOB_SQL = "CREATE TABLE `schedule_job` (\n" +
            "  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `bean_name` varchar(255) DEFAULT NULL,\n" +
            "  `create_time` datetime DEFAULT NULL,\n" +
            "  `cron_expression` varchar(255) DEFAULT NULL,\n" +
            "  `method_name` varchar(255) DEFAULT NULL,\n" +
            "  `params` varchar(255) DEFAULT NULL,\n" +
            "  `remark` varchar(255) DEFAULT NULL,\n" +
            "  `status` varchar(255) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`job_id`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

    @PostConstruct
    public void init() {

        if (isExist("schedule_job")) {
            log.info("schedule_job 表已经存在");
        } else {
            log.info("创建 表 schedule_job");
            jdbcTemplate.execute(SCHEDULE_JOB_SQL);
        }
    }


    /**
     * 判断表是否已经存在
     *
     * @param table 表名
     * @return 存在返回true
     */
    public Boolean isExist(String table) {
        final Map<String, Object> map = jdbcTemplate.queryForMap("show tables like ?", table);
        return !map.isEmpty();
    }
}
