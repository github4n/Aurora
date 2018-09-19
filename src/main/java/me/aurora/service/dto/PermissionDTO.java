package me.aurora.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 郑杰
 * @date 2018/08/23 15:49:44
 */
@Data
public class PermissionDTO implements Serializable{

	private Long id;

	private String perms;

	private Integer pid;

	private String remark;

	private Timestamp createTime;

	private Timestamp updateTime;
}
