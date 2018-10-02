package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.utils.QiniuContent;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018/10/02 12:13:46
 */
public class QiNiuContentSpec implements Specification<QiniuContent> {

    private String key;
    private String bucket;

    public QiNiuContentSpec(String name,String bucket) {
       this.key = name;
       this.bucket = bucket;
    }


    @Override
    public Predicate toPredicate(Root<QiniuContent> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (!StrUtil.isEmpty(key)) {
            list.add(cb.like(root.get("key").as(String.class), "%" + key + "%" ));
        }

        if (!StrUtil.isEmpty(bucket)) {
            list.add(cb.like(root.get("bucket").as(String.class), bucket));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "QiNiuContentSpec{" +
                "key='" + key + '\'' +
                '}';
    }
}
