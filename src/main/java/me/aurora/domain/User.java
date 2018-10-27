package me.aurora.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    @NotNull(groups = {Update.class,UpdateInfo.class})
    private Long id;

    @Column(unique = true,nullable = false)
    @NotBlank(groups = {New.class,Update.class})
    private String username;

    @NotNull(groups = {New.class,Update.class})
    private String sex;

    @NotNull(groups = {New.class,Update.class})
    private LocalDate birthday;

    @Column(nullable = false)
    private String password;

    @NotBlank(groups = {New.class,Update.class,UpdateInfo.class})
    @Column(unique = true,nullable = false)
    private String email;

    private Integer enabled=1;

    @Column(nullable = false)
    @NotBlank(groups = {UpdateInfo.class})
    private String avatar;

    @Column
    @CreationTimestamp
    private Timestamp createDateTime;

    @ManyToMany
    @JoinTable(name = "zj_users_roles", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @NotNull(groups = {New.class,Update.class})
    @Valid
    private Department department;

    @OneToMany
    @JsonIgnore
    private Set<Picture> pictures;

    public interface New{};
    public interface Update{};
    public interface UpdateInfo{};

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
                '}';
    }
}
