package me.aurora.domain.job;

import com.google.common.base.MoreObjects;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 郑杰
 * @date 2018/10/05 19:06:42
 */
@Data
@Entity
@Table(name = "zj_job")
public class Job implements Serializable {

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private String value;

        private ScheduleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Bean名称
     */
    @Column(name = "baen_name")
    @NotBlank(groups = {New.class,Update.class})
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name = "method_name")
    @NotBlank(groups = {New.class,Update.class})
    private String methodName;

    /**
     * 参数
     */
    @Column(name = "params")
    private String params;

    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    @NotBlank(groups = {New.class,Update.class})
    private String cronExpression;

    /**
     * 状态 0=正常,1=暂停
     */
    @Column(name = "status")
    @NotBlank(groups = {New.class,Update.class})
    private String status = "0";

    /**
     * 备注
     */
    @Column(name = "remark")
    @NotBlank(groups = {New.class,Update.class})
    private String remark;

    /**
     * 创建日期
     */
    @UpdateTimestamp
    private Timestamp updateTime;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("beanName", beanName)
                .add("methodName", methodName)
                .add("params", params)
                .add("cronExpression", cronExpression)
                .add("status", status)
                .add("remark", remark)
                .add("updateTime", updateTime)
                .toString();
    }

    public interface New{};
    public interface Update{};
}