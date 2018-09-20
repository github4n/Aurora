package me.aurora.service;

import me.aurora.domain.User;
import me.aurora.repository.spec.UserSpec;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/08/23 15:54:56
 */
@CacheConfig(cacheNames = "user")
public interface UserService {

    /**
     * 根据用户ID获取用户详细信息
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    User findById(Long id);

    /**
     * 根据用户名获取用户详细信息
     * @param userName
     * @return
     */
    @Cacheable(key = "#p0")
    User findByUsername(String userName);

    /**
     * 根据用户邮箱获取用户详细信息
     * @param email
     * @return
     */
    @Cacheable(key = "#p0")
    User findByEmail(String email);

    /**
     * 查询所有用户信息
     * @param userSpec
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator="keyGenerator")
    Map getUsersInfo(UserSpec userSpec, Pageable pageable);

    /**
     * 新增用户
     * @param user
     * @param roles
     * @return
     */
    @CacheEvict(allEntries = true)
    void inster(User user, String roles);

    /**
     * 更新用户
     * @param user
     * @param roles
     * @return
     */
    @CacheEvict(allEntries = true)
    void update(User user, String roles);

    /**
     * 更新最近登录时间
     * @param user
     */
    @CacheEvict(allEntries = true)
    void checkLastLoginTime(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    @CacheEvict(allEntries = true)
    void delete(User id);

    /**
     * 是否允许登录
     * @param user
     * @return
     */
    @CacheEvict(allEntries = true)
    void updateEnabled(User user);
}
