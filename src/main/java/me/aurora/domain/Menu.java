package me.aurora.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(groups = {Update.class})
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
    private Integer pid;

    /**
     * 菜单类目
     */
    @Column(nullable = false)
    private Integer level;

    /**
     * 子菜单数量
     */
    @Column(name = "level_number",nullable = false)
    private Integer levelNum = 0;

    /**
     * 是否为外链 true/false
     */
    private Boolean iframe = false;

    /**
     * 系统菜单
     */
    private Boolean sys = false;

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

