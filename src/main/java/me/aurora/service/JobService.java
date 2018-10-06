package me.aurora.service;

import me.aurora.domain.job.Job;
import me.aurora.repository.spec.JobSpce;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/05 19:17:38
 */
@CacheConfig(cacheNames = "job")
public interface JobService {

    /**
     * 查询全部任务
     * @param jobSpce
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Map getJobsInfo(JobSpce jobSpce, Pageable pageable);

    /**
     * 新增任务
     * @param job
     */
    @CacheEvict(allEntries = true)
    void insert(Job job);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    Job findById(Long id);

    /**
     * 更新任务状态
     * @param byId
     */
    @CacheEvict(allEntries = true)
    void updateStatus(Job byId);

    /**
     * 更新任务
     * @param old
     * @param job
     */
    @CacheEvict(allEntries = true)
    void update(Job old, Job job);

    /**
     * 删除任务
     * @param byId
     */
    @CacheEvict(allEntries = true)
    void delete(Job byId);
}
