package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.Menu;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018/09/04 7:12:01
 */
public class MenuSpec implements Specification<Menu> {

    private Long id;
    private String name;

    public MenuSpec(Long id, String name) {
       this.id = id;
       this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (null!=id) {
            list.add(cb.equal(root.get("id").as(String.class), id ));
        }

        if (!StrUtil.isEmpty(name)) {
            list.add(cb.like(root.get("name").as(String.class), "%" + name + "%" ));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "MenuSpec{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
