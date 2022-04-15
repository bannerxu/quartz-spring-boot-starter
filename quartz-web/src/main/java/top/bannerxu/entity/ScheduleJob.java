package top.bannerxu.entity;

import io.github.lib.quartz.model.IScheduleJob;
import io.github.lib.quartz.model.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleJob implements IScheduleJob, Serializable {

    /**
     * 任务id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "任务id不能为空")
    private String jobId;

    /**
     * spring bean名称
     */
    @NotBlank(message = "bean名称不能为空")
    private String beanName;

    /**
     * 方法名
     */
    @NotBlank(message = "方法名称不能为空")
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * cron表达式
     */

    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    /**
     * 任务状态
     */
    @Enumerated(value = EnumType.STRING)
    private ScheduleStatus status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;
}