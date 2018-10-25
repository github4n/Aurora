package me.aurora.repository.spec;

import com.alibaba.druid.sql.visitor.functions.Left;
import me.aurora.domain.Department;
import me.aurora.domain.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018/09/01 7:12:01
 */
public class UserSpec implements Specification<User> {

    private String username;
    private String departmentName;
    private String email;
    private Long enabled;
    private Long id;

    public UserSpec(String username,String departmentName, String email, Long enabled, Long id) {
       this.username = username;
       this.departmentName= departmentName;
       this.email = email;
       this.enabled = enabled;
       this.id = id;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (null!=username) {
            list.add(cb.like(root.get("username").as(String.class), "%" + username + "%" ));
        }

        if(null != departmentName){

            Join<Department,User> join = root.join("department", JoinType.LEFT);
            list.add(cb.like(join.get("name").as(String.class), "%" + departmentName + "%" ));
        }

        if (null!=email) {
            list.add(cb.like(root.get("email").as(String.class), "%" + email + "%" ));
        }

        if (null!=enabled) {
            list.add(cb.equal(root.get("enabled").as(Long.class), enabled));
        }

        if (null!=id) {
            list.add(cb.equal(root.get("id").as(Long.class), id));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "UserSpec{" +
                "username='" + username + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", id=" + id +
                '}';
    }
}
