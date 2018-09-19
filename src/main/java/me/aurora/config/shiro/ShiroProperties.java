package me.aurora.config.shiro;

import lombok.Data;

/**
 * @author 郑杰
 * @date 2018/08/23 13:31:59
 */
@Data
public class ShiroProperties {

    /**
     * shiro redis缓存时长，默认值 1800 秒
     */
    private Integer expireIn;
    /**
     * session 超时时间，默认 1800000毫秒
     */
    private long sessionTimeout;
    /**
     * rememberMe 有效时长，默认为 86400 秒，即一天
     */
    private Integer cookieTimeout;

    private String anonUrls;

    private String loginUrl;

    private String successUrl;

    private String logoutUrl;

    private String unauthorizedUrl;
}
