package me.aurora.service;

import me.aurora.domain.Dict;
import me.aurora.repository.spec.DictSpce;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/05 12:08:53
 */
@CacheConfig(cacheNames = "dict")
public interface DictService {

    /**
     * 查询全部字典
     * @param dictSpce
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Map getDictInfo(DictSpce dictSpce, Pageable pageable);

    /**
     * 新增
     * @param dict
     */
    @CacheEvict(allEntries = true)
    void insert(Dict dict);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    Dict findById(Long id);

    /**
     * 更新
     * @param old
     * @param dict
     */
    @CacheEvict(allEntries = true)
    void update(Dict old, Dict dict);

    /**
     * 删除
     * @param dict
     */
    @CacheEvict(allEntries = true)
    void delete(Dict dict);
}
