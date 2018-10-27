package me.aurora.service;

import me.aurora.repository.spec.LogSpec;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/08/23 9:57:25
 */
@CacheConfig(cacheNames = "sysLog")
public interface SysLogService {

    /**
     * 查询所有日志
     * @param logSpec
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator="keyGenerator")
    Map getLogInfo(LogSpec logSpec, Pageable pageable);

    /**
     * 新增日志
     * @param joinPoint
     * @param time
     */
    @CacheEvict(allEntries = true)
    @Async
    void save(ProceedingJoinPoint joinPoint, long time);

    /**
     * 得到今日IP
     * @return
     */
    Long getIp();

    /**
     * 得到近7日Ip
     * @return
     */
    Long getWeekIP();
}
