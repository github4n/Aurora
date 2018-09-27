package me.aurora.service.dto;

import lombok.Data;
import me.aurora.domain.Role;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/23 11:54:10
 */
@Data
public class MenuDTO implements Serializable {

    private Long id;

    private Long soft;

    private String ico;

    private String name;

    private String url;

    private Integer pid;

    private Boolean iframe;

    private Integer level;

    private String rolesSelect;

    private Integer levelNum;

    private Timestamp createTime;

    private Timestamp updateTime;
}
