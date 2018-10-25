package me.aurora.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/23 15:49:44
 */
@Entity
@Getter
@Setter
@Table(name = "zj_permission")
public class Permission implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(groups = {Update.class})
	private Long id;

	/**
	 * 权限标识
	 */
	@NotBlank(groups = {New.class,Update.class})
	@Column(name = "perms",nullable = false,unique = true)
	private String perms;

	/**
	 * 上级类目
	 */
	@Column(name = "pid",nullable = false)
	private Integer pid;

	@NotBlank(groups = {New.class,Update.class})
	@Column(nullable = false)
	private String remark;

	@JsonIgnore
	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles;

	@CreationTimestamp
	private Timestamp createTime;

	@UpdateTimestamp
	private Timestamp updateTime;

	public interface New{};
	public interface Update{};

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", perms='" + perms + '\'' +
				", pid=" + pid +
				", remark='" + remark + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
