package me.aurora.domain.utils;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 上传成功后，存储结果
 * @author 郑杰
 * @date 2018/10/02 10:01:05
 */
@Data
@Entity
@Table(name = "zj_qiniu_content")
public class QiniuContent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文件名，如qiniu.jpg
     */
    @Column(name = "name",unique = false)
    private String key;

    /**
     * 空间名
     */
    private String bucket;

    /**
     * 大小
     */
    private String size;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";

    /**
     * 更新时间
     */
    @UpdateTimestamp
    private Timestamp updateTime;
}
