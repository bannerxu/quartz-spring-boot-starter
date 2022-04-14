package io.github.lib.quartz.util;


import io.github.lib.quartz.model.IScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;


/**
 * 定时任务spring bean 执行定时任务
 *
 * @author LGH
 */
@Slf4j
public class SpringBeanTaskUtil {

    public static void invokeMethod(IScheduleJob IScheduleJob) {
        Object target = SpringContextUtils.getBean(IScheduleJob.getBeanName());
        try {
            if (StringUtils.isNotBlank(IScheduleJob.getParams())) {
                Method method = target.getClass().getDeclaredMethod(IScheduleJob.getMethodName(), String.class);
                ReflectionUtils.makeAccessible(method);
                method.invoke(target, IScheduleJob.getParams());
            } else {
                Method method = target.getClass().getDeclaredMethod(IScheduleJob.getMethodName());
                ReflectionUtils.makeAccessible(method);
                method.invoke(target);
            }
        } catch (Exception e) {
            log.error("执行定时任务失败：", e);
            throw new RuntimeException("执行定时任务失败", e);
        }
    }
}
