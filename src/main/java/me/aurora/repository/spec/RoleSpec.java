package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.Role;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018/09/01 7:12:01
 */
public class RoleSpec implements Specification<Role> {

    private Long id;
    private String name;
    private String remark;

    public RoleSpec(Long id, String name, String remark) {
       this.id = id;
       this.name = name;
       this.remark = remark;
    }

    @Override
    public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (null!=id) {
            list.add(cb.equal(root.get("id").as(String.class), id ));
        }

        if (!StrUtil.isEmpty(name)) {
            list.add(cb.like(root.get("name").as(String.class), "%" + name + "%" ));
        }

        if (!StrUtil.isEmpty(remark)) {
            list.add(cb.like(root.get("remark").as(String.class), "%" + remark + "%" ));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "RoleSpec{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
