package me.aurora.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;


/**
 * 部门，部门关联角色，该部门下的用户都将拥有该角色的权限
 * @author 郑杰
 * @date 2018/10/25 11:26:38
 */
@Getter
@Setter
@Entity
@Table(name = "zj_department")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Department.Update.class})
    private Long id;

    @NotBlank(groups = {Department.New.class, Department.Update.class})
    @Column(name = "name",nullable = false)
    private String name;

    @CreationTimestamp
    private Timestamp createTime;

    /**
     * 上级部门ID
     */
    @Column(name = "pid",nullable = false)
    @NotNull(groups = {Department.New.class, Department.Update.class})
    private Integer pid;

    @ManyToMany
    @JoinTable(name = "zj_departments_roles", joinColumns = {@JoinColumn(name = "department_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private Set<Role> roles;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<User> users;

    public interface New{};
    public interface Update{};
}
