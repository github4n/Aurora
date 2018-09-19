package me.aurora.repository;

import me.aurora.domain.Role;
import me.aurora.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/22
 */
@Repository
public interface RoleRepo extends JpaRepository<Role,Long>,JpaSpecificationExecutor {

    /**
     * 根据name查询role
     * @param name
     * @return
     */
    Role findByName(String name);

    /**
     * 根据用户查询权限
     * @param users
     * @return
     */
    List<Role> findByUsers(Set<User> users);
}
