package me.aurora.domain.utils;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * pv统计
 */
@Entity
@Data
@Table(name = "zj_web_count")
public class WebCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Timestamp createTime;

    private String date;

    @Column(name = "web_counts")
    private Long counts;
}
