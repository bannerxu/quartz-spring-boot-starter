/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package io.github.bannerxu.quartz.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class ScheduleJobLog {
    /**
     * 任务日志id
     */
    @Id
    private Long logId;

    /**
     * 任务id
     */

    private Long jobId;

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
     * 任务状态    1：成功    0：失败
     */
    private Integer status;

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