package me.aurora.repository;

import me.aurora.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 郑杰
 * @date 2018/08/22
 */
@Repository
public interface UserRepo extends JpaRepository<User,Long>,JpaSpecificationExecutor {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 根据ID查询，解决懒加载异常
     * @param id
     * @return
     */
    @Override
    @Query("from User u join fetch u.roles where u.id = :id")
    Optional<User> findById(@Param("id") Long id);
}
