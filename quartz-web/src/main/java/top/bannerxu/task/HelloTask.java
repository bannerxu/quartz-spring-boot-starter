package top.bannerxu.task;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class HelloTask extends BaseTask {

    public void each() {
        log.info("测试方法一 ==> {}", new Date());
    }

    public void test() {
        log.info("测试方法二 ==> {}", new Date());
    }
}
