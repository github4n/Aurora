package me.aurora.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 郑杰
 * @date 2018/08/20 20:10:15
 */
@Entity
@Data
@Table(name = "zj_syslog")
public class SysLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;

    private String location;

    @CreationTimestamp
    private Timestamp createTime;
}
