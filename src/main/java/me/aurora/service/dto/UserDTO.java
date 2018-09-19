package me.aurora.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * UserDTO
 * @author 郑杰
 * @date 2018/08/22 19:34:59
 */
@Data
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String email;

    private Integer enabled;

    private String avatar;

    private String rolesSelect;

    private Timestamp createDateTime;

    private Timestamp lastLoginTime;
}
