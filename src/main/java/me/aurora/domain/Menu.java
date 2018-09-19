package me.aurora.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/20 20:10:15
 */
@Entity
@Table(name = "zj_menu")
@Getter
@Setter
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private Long soft;

    private String ico;

    @NotBlank(groups = {New.class,Update.class})
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "url")
    private String url;

    /**
     * 上级菜单ID
     */
    @Column(name = "pid",nullable = false)
    @NotBlank(groups = {New.class,Update.class})
    private Integer pid;

    /**
     * 菜单类目
     */
    @Column(nullable = false)
    @NotBlank(groups = {New.class,Update.class})
    private Integer level;

    /**
     * 子菜单数量
     */
    @Column(name = "level_number",nullable = false)
    @NotBlank(groups = {New.class,Update.class})
    private Integer levelNum = 0;

    @ManyToMany
    @JoinTable(name = "zj_menus_roles", joinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private Set<Role> roles;

    @CreationTimestamp
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp updateTime;

    public interface New{};
    public interface Update{};

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", soft=" + soft +
                ", ico='" + ico + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                ", level=" + level +
                ", levelNum=" + levelNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

