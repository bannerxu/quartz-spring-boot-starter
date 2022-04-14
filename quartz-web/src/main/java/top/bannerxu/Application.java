package top.bannerxu;

import io.github.lib.quartz.util.SpringContextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.bannerxu.service.ScheduleJobService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        ScheduleJobService service = SpringContextUtils.getBean("ScheduleJobService", ScheduleJobService.class);

    }

}
