package me.aurora.repository;

import me.aurora.domain.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 郑杰
 * @date 2018/10/05 19:18:54
 */
public interface JobRepo extends JpaRepository<Job,Long>, JpaSpecificationExecutor {

    /**
     * 查找启用的任务
     * @return
     */
    List<Job> findByStatusIsTrue();
}
