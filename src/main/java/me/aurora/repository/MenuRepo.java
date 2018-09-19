package me.aurora.repository;

import me.aurora.domain.Menu;
import me.aurora.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/22
 */
@Repository
public interface MenuRepo extends JpaRepository<Menu,Long>,JpaSpecificationExecutor {

    /**
     * 根据role查询菜单
     * @param roles
     * @return
     */
    List<Menu> findByRoles(Set<Role> roles);

    /**
     * 根据排序序号查询
     * @param soft
     * @return
     */
    Menu findBySoft(Long soft);

    /**
     * 查询所有一级菜单
     * @param level
     * @return
     */
    List<Menu> findAllByLevel(Integer level);

    /**
     * 根据pid查询
     * @param pid
     * @return
     */
    @Query("from Menu m join fetch m.roles where m.pid = :pid")
    List<Menu> findByPid(@Param("pid") int pid);

    /**
     * 根据ID查询，解决懒加载异常
     * @param id
     * @return
     */
    @Override
    @Query("from Menu m join fetch m.roles where m.id = :id")
    Optional<Menu> findById(@Param("id") Long id);
}
