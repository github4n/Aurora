package me.aurora.service.impl;

import me.aurora.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.*;
import java.util.function.Function;

/**
 * Redis 工具类，只封装了几个常用的 redis 命令，
 * 可根据实际需要按类似的方式扩展即可。
 * @author 郑杰
 * @date 2018/09/22 10:42:52
 */
@Service("redisService")
@SuppressWarnings("unchecked")
public class RedisServiceImpl implements RedisService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JedisPool jedisPool;


    /**
     * 处理jedis请求
     * @param f 处理逻辑，通过lambda行为参数化
     * @return 处理结果
     */
    private Object excuteByJedis(Function<Jedis, Object> f) {
        try (Jedis jedis = jedisPool.getResource()) {
            return f.apply(jedis);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Set<String> getKeys(String pattern) {
        return (Set<String>) this.excuteByJedis(j -> j.keys(pattern));
    }

    @Override
    public String get(String key) {
        return (String) this.excuteByJedis(j -> j.get(key));
    }

    @Override
    public String set(String key, String value) {
        return (String) this.excuteByJedis(j -> j.set(key, value));
    }

    @Override
    public Long del(String... key) {
        return (Long) this.excuteByJedis(j -> j.del(key));
    }

    @Override
    public Boolean exists(String key) {
        return (Boolean) this.excuteByJedis(j -> j.exists(key));
    }

    @Override
    public Long pttl(String key) {
        return (Long) this.excuteByJedis(j -> j.pttl(key));
    }

    @Override
    public Long pexpire(String key, Long milliseconds) { return (Long) this.excuteByJedis(j -> j.pexpire(key, milliseconds)); }

    @Override
    public String flushdb() { return (String) this.excuteByJedis(j -> j.flushDB()); }

    @Override
    public String flushall() { return (String) this.excuteByJedis(j -> j.flushAll()); }
}
