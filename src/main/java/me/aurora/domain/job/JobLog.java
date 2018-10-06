package me.aurora.domain.job;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 郑杰
 * @date 2018/10/05 19:47:38
 */
@Entity
@Data
@Table(name = "zj_job_log")
public class JobLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务id
     */
    @Column(name = "job_id")
    private Long jobId;

    /**
     * Bean名称
     */
    @Column(name = "baen_name")
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name = "method_name")
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
    private String cronExpression;

    /**
     * 0=成功,1=失败
     */
    @Column(name = "status")
    private String status;

    /**
     * 错误信息
     */
    @Column(name = "error_msg")
    private String error;

    /**
     * 耗时（毫秒）
     */
    private Long times;

    /**
     * 创建日期
     */
    @CreationTimestamp
    private Timestamp createTime;

    @Override
    public String toString() {
        return "JobLog{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", beanName='" + beanName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params='" + params + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", status='" + status + '\'' +
                ", error='" + error + '\'' +
                ", times=" + times +
                ", createTime=" + createTime +
                '}';
    }
}
