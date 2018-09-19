package me.aurora.service;

import me.aurora.domain.Menu;
import me.aurora.domain.Permission;
import me.aurora.domain.Role;
import me.aurora.domain.User;
import me.aurora.repository.spec.RoleSpec;
import me.aurora.util.exception.AuroraException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/23 17:26:33
 */
@CacheConfig(cacheNames = "role")
public interface RoleService {

    /**
     * 构建Role tree
     * @param roles
     * @return
     */
    @Cacheable(key="'buildRoleTree'")
    List<Map<String, Object>> buildRoleTree(List<Role> roles);

    /**
     * 查询所有角色
     * @return
     */
    @Cacheable(key="'getAllRole'")
    List<Role> getAllRole();

    /**
     * 条件查询
     * @param roleSpec
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator="keyGenerator")
    Map getRoleInfo(RoleSpec roleSpec, Pageable pageable);

    /**
     * 新增角色
     * @param role
     * @param permissions
     * @return
     */
    @CacheEvict(allEntries = true)
    void inster(Role role, String permissions) throws AuroraException;

    /**
     * 根据ID查找Role
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    Role findById(Long id);

    /**
     * 更新角色
     * @param role
     * @param permissions
     * @return
     */
    @CacheEvict(allEntries = true)
    void update(Role role, String permissions) throws AuroraException;

    /**
     * 删除角色
     * @param id
     * @return
     */
    @CacheEvict(allEntries = true)
    void delete(Long id) throws AuroraException;

    /**
     * 组装树形结构数据
     * @param permissions
     * @return
     */
    String getPermissions(Set<Permission> permissions);

    /**
     * 获取RoleIds
     * @param roles
     * @return
     */
    String getRoles(Set<Role> roles);
}
