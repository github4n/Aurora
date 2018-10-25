package me.aurora.service;

import me.aurora.domain.Department;
import me.aurora.repository.spec.DepartmentSpec;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/25 11:32:15
 */
@CacheConfig(cacheNames = "department")
public interface DepartmentService {

    /**
     * 查询全部
     * @param departmentSpec
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Map getDepartmentsInfo(DepartmentSpec departmentSpec, Pageable pageable);

    /**
     * 构建树
     * @param departments
     * @return
     */
    List<Map<String, Object>> buildDepartmentTree(List<Department> departments);

    /**
     * 根据Pid查询
     * @param pid
     * @param isTop
     * @return
     */
    List<Department> findByPid(int pid, Boolean isTop);

    /**
     * 新增部门
     * @param department
     * @param roles
     */
    @CacheEvict(allEntries = true)
    void insert(Department department, String roles);

    /**
     * 查询
     * @param id
     * @return
     */
    Department findById(Long id);

    /**
     * 更新部门
     * @param department
     * @param roles
     * @param old
     */
    void update(Department department,Department old, String roles);

    /**
     * 删除
     * @param byId
     */
    void delete(Department byId);
}
