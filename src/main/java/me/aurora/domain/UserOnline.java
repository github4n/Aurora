package me.aurora.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 在线用户管理
 * @author 郑杰
 * @date 2018/09/21 15:38:06
 */
@Getter
@Setter
public class UserOnline implements Serializable{

	/**
	 * session id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户名称
	 */
	private String username;

	/**
	 * 用户主机地址
	 */
	private String host;

	/**
	 * 用户登录时系统IP
	 */
	private String systemHost;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * session创建时间
	 */
	private Date startTimestamp;

	/**
	 * session最后访问时间
	 */
	private Date lastAccessTime;

	/**
	 * 超时时间
	 */
	private Long timeout;

	/**
	 * 所在地
	 */
	private String location;
}
