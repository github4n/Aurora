package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.Dict;
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
public class DictSpce implements Specification<Dict> {

    private String tableName;
    private String fieldName;
    private String fieldValue;
    private String fieldDetail;

    public DictSpce(String tableName, String fieldName, String fieldValue, String fieldDetail) {
       this.tableName = tableName;
       this.fieldName = fieldName;
       this.fieldValue = fieldValue;
       this.fieldDetail = fieldDetail;
    }

    @Override
    public Predicate toPredicate(Root<Dict> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();


        if (!StrUtil.isEmpty(tableName)) {
            list.add(cb.like(root.get("tableName").as(String.class), "%" + tableName + "%" ));
        }

        if (!StrUtil.isEmpty(fieldName)) {
            list.add(cb.like(root.get("method").as(String.class), "%" + fieldName + "%" ));
        }

        if (!StrUtil.isEmpty(fieldValue)) {
            list.add(cb.like(root.get("operation").as(String.class), "%" + fieldValue + "%" ));
        }

        if (!StrUtil.isEmpty(fieldDetail)) {
            list.add(cb.like(root.get("location").as(String.class), "%" + fieldDetail + "%" ));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "DictSpce{" +
                "tableName='" + tableName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                ", fieldDetail='" + fieldDetail + '\'' +
                '}';
    }
}
