package me.aurora.repository;

import me.aurora.domain.Permission;
import me.aurora.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/23 17:32:05
 */
@Repository
public interface PermissionRepo extends JpaRepository<Permission,Long>,JpaSpecificationExecutor {

    /**
     * 根据权限名称查询权限
     * @param perms
     * @return
     */
    Permission findByPerms(String perms);

    /**
     * 根据pid查询
     * @param pid
     * @return
     */
    List<Permission> findByPid(Integer pid);

    /**
     * 根据role查询
     * @param roles
     * @return
     */
    List<Permission> findByRoles(Set<Role> roles);
}
