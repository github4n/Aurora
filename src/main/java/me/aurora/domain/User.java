package me.aurora.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 用户
 * @author 郑杰
 * @date 2018/08/20 19:52:23
 */
@Entity
@Table(name = "zj_user")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    @NotBlank(groups = {New.class,Update.class})
    private String username;

    private String password;

    @NotBlank(groups = {New.class,Update.class})
    @Column(unique = true,nullable = false)
    private String email;

    private Integer enabled=1;

    @NotBlank(groups = {New.class,Update.class})
    @Column(nullable = false)
    private String avatar;

    @Column
    @CreationTimestamp
    private Timestamp createDateTime;

    @Column
    private Timestamp lastLoginTime;

    @ManyToMany
    @JoinTable(name = "zj_users_roles", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private Set<Role> roles;

    public interface New{};
    public interface Update{};

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", avatar='" + avatar + '\'' +
                ", createDateTime=" + createDateTime +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
