package io.github.lib.quartz.model;


import lombok.Data;

import java.util.Date;

@Data
public class ScheduleJobLog {

    /**
     * 任务id
     */

    private String jobId;

    /**
     * spring bean名称
     */

    private String beanName;

    /**
     * 方法名
     */

    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 任务是否执行成功
     */
    private Boolean success;

    /**
     * 失败信息
     */
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    private Integer times;

    /**
     * 创建时间
     */
    private Date createTime;
}