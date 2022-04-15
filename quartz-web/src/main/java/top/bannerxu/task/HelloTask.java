package top.bannerxu.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component("HelloTask")
public class HelloTask {

    public void each() {
        log.info("测试方法一 ==> {}", new Date());
    }

    public void test() {
        log.info("测试方法二 ==> {}", new Date());
    }
}
