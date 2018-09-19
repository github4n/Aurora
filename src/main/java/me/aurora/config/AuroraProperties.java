package me.aurora.config;

import lombok.Data;
import me.aurora.config.scheduled.db.DBProperties;
import me.aurora.config.shiro.ShiroProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置类
 * @author 郑杰
 * @date 2018/08/23 16:32:25
 */
@Data
@Component
@ConfigurationProperties(prefix="aurora")
public class AuroraProperties {

    private ShiroProperties shiro  = new ShiroProperties();

    private DBProperties db = new DBProperties();

    private Boolean openAopLog;

    private String userAvatar;
}
