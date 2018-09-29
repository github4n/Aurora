package me.aurora.domain.utils;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 邮件配置类，数据存覆盖式存入数据存
 */
@Entity
@Data
@Table(name = "zj_email_config")
public class EmailConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *邮件服务器SMTP地址
     */
    @NotBlank(groups = Update.class)
    private String host;

    /**
     * 邮件服务器SMTP端口
     */
    @NotBlank(groups = Update.class)
    private String port;

    @NotBlank(groups = Update.class)
    private String user;

    @NotBlank(groups = Update.class)
    private String pass;

    /**
     * 发件人
     */
    @NotBlank(groups = Update.class)
    private String fromUser;

    /**
     * 是否开启ssl
     */
    private Boolean sslEnable;

    public interface Update {}
}
