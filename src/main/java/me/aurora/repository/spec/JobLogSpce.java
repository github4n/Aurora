package me.aurora.repository.spec;

import cn.hutool.core.util.StrUtil;
import me.aurora.domain.job.JobLog;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 郑杰
 * @date 2018/10/06 11:43:38
 */
public class JobLogSpce implements Specification<JobLog> {

    private String beanName;
    private String remark;
    private String methodName;
    private String status;

    public JobLogSpce(String beanName, String methodName, String remark, String status) {
       this.beanName = beanName;
       this.remark = remark;
       this.methodName = methodName;
       this.status = status;
    }

    @Override
    public Predicate toPredicate(Root<JobLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();

        if (!StrUtil.isEmpty(status)) {
            list.add(cb.equal(root.get("status").as(String.class), status));
        }

        if (!StrUtil.isEmpty(beanName)) {
            list.add(cb.equal(root.get("beanName").as(String.class), "%"+beanName+"%"));
        }

        if (!StrUtil.isEmpty(remark)) {
            list.add(cb.like(root.get("remark").as(String.class), "%"+remark+"%"));
        }

        if (!StrUtil.isEmpty(methodName)) {
            list.add(cb.equal(root.get("methodName").as(String.class), "%"+methodName+"%"));
        }

        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }

    @Override
    public String toString() {
        return "JobSpce{" +
                "beanName='" + beanName + '\'' +
                ", remark='" + remark + '\'' +
                ", methodName='" + methodName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
