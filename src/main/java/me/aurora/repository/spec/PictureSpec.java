package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.Picture;
import me.aurora.domain.User;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 郑杰
 * @date 2018/09/20 14:30:21
 */
public class PictureSpec implements Specification<Picture> {

    private String username;
    private Timestamp createTime;
    private Timestamp endTime;

    public PictureSpec(String username, Timestamp createTime, Timestamp endTime) {
       this.username = username;
       this.createTime = createTime;
       this.endTime = endTime;
    }

    @Override
    public Predicate toPredicate(Root<Picture> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        Join<User,Picture> userJoin = root.join("user",JoinType.LEFT);
        if (!StrUtil.isEmpty(username)) {
            list.add(cb.like(userJoin.get("username").as(String.class), "%" + username + "%" ));
        }

        if(null!=createTime && null != endTime){
            list.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class),createTime));
            list.add(cb.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class),endTime));

        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "PictureSpec{" +
                "username='" + username + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                '}';
    }
}
