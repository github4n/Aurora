package me.aurora.service;

import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/09/22 10:42:52
 */
public interface RedisService {

    /**
     * 获取 key
     * @param pattern 正则
     * @return Set
     */
    Set<String> getKeys(String pattern);

    /**
     * get命令
     *
     * @param key key
     * @return String
     */
    String get(String key);

    /**
     * set命令
     *
     * @param key   key
     * @param value value
     * @return String
     */
    String set(String key, String value);

    /**
     * del命令
     *
     * @param key key
     * @return Long
     */
    Long del(String... key);

    /**
     * exists命令
     *
     * @param key key
     * @return Boolean
     */
    Boolean exists(String key);

    /**
     * pttl命令
     *
     * @param key key
     * @return Long
     */
    Long pttl(String key);

    /**
     * pexpire命令
     *
     * @param key         key
     * @param milliscends 毫秒
     * @return Long
     */
    Long pexpire(String key, Long milliscends);

    /**
     * flushdb 命令
     * @return
     */
    String flushdb();

    /**
     * flushall 命令
     * @return
     */
    String flushall();
}
