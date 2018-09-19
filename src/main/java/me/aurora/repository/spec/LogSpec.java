package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.SysLog;
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
public class LogSpec implements Specification<SysLog> {

    private String username;
    private String method;
    private String operation;
    private String location;

    public LogSpec(String username, String method, String operation,String location) {
       this.username = username;
       this.method = method;
       this.operation = operation;
       this.location = location;
    }

    @Override
    public Predicate toPredicate(Root<SysLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();


        if (!StrUtil.isEmpty(username)) {
            list.add(cb.like(root.get("username").as(String.class), "%" + username + "%" ));
        }

        if (!StrUtil.isEmpty(method)) {
            list.add(cb.like(root.get("method").as(String.class), "%" + method + "%" ));
        }

        if (!StrUtil.isEmpty(operation)) {
            list.add(cb.like(root.get("operation").as(String.class), "%" + operation + "%" ));
        }

        if (!StrUtil.isEmpty(location)) {
            list.add(cb.like(root.get("location").as(String.class), "%" + location + "%" ));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "LogSpec{" +
                "username='" + username + '\'' +
                ", method='" + method + '\'' +
                ", operation='" + operation + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
