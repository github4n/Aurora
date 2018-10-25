package me.aurora.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.aurora.domain.Role;
import me.aurora.domain.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;


/**
 * @author 郑杰
 * @date 2018/10/25 11:26:38
 */
@Data
public class DepartmentDTO implements Serializable {

    private Long id;

    private String name;

    private Timestamp createTime;

    private Integer pid;

    private String rolesSelect;

    public interface New{};
    public interface Update{};
}
