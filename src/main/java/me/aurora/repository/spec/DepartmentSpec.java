package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.Department;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018/10/25 12:42:54
 */
public class DepartmentSpec implements Specification<Department> {

    private Long id;
    private String name;

    public DepartmentSpec(Long id, String name) {
       this.id = id;
       this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (!StrUtil.isEmpty(name)) {
            list.add(cb.like(root.get("name").as(String.class), "%" + name + "%" ));
        }

        if (null!=id) {
            list.add(cb.equal(root.get("id").as(String.class), id ));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
