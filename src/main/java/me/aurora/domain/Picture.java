package me.aurora.domain;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 郑杰
 * @date 2018/09/20 14:08:31
 */
@Getter
@Setter
@Entity
@Table(name = "zj_picture")
public class Picture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String url;

    private String size;

    private String height;

    private String width;

    /**
     * delete URl
     */
    @Column(name = "deleteUrl")
    private String delete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createTime;

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", size='" + size + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", deleteUrl='" + delete + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
