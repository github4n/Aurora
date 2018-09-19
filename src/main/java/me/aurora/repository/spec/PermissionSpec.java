package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.Permission;
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
public class PermissionSpec implements Specification<Permission> {

    private Long id;
    private String perms;
    private String remark;

    public PermissionSpec(Long id, String perms, String remark) {
       this.id = id;
       this.perms = perms;
       this.remark = remark;
    }

    @Override
    public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (null!=id) {
            list.add(cb.equal(root.get("id").as(String.class), id ));
        }

        if (!StrUtil.isEmpty(perms)) {
            list.add(cb.like(root.get("perms").as(String.class), "%" + perms + "%" ));
        }

        if (!StrUtil.isEmpty(remark)) {
            list.add(cb.like(root.get("remark").as(String.class), "%" + remark + "%" ));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "PermissionSpec{" +
                "id=" + id +
                ", perms='" + perms + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
