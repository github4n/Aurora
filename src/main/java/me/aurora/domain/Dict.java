package me.aurora.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 字典表
 * @author 郑杰
 * @date 2018/10/05 12:08:24
 */
@Data
@Entity
@Table(name = "zj_dict")
public class Dict implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = {New.class, Update.class})
    @Column(name = "table_name",nullable = false)
    private String tableName;

    @NotBlank(groups = {New.class, Update.class})
    @Column(name = "fteld_name",nullable = false)
    private String fieldName;

    @NotBlank(groups = {New.class, Update.class})
    @Column(name = "field_value",nullable = false)
    private String fieldValue;

    @Column(name = "field_detail",nullable = false)
    @NotBlank(groups = {New.class, Update.class})
    private String fieldDetail;

    @CreationTimestamp
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp updateTime;

    public interface New{};
    public interface Update{};
}
