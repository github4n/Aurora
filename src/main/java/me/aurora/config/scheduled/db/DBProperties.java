package me.aurora.config.scheduled.db;

import lombok.Data;

/**
 * @author 郑杰
 * @date 2018/09/16 13:59:01
 */
@Data
public class DBProperties {

    private String username;

    private String password;

    private String dbName;

    private String path;

    private String linuxDbPath;

    private String pathName;
}
