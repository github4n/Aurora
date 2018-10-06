package me.aurora.repository;

import me.aurora.domain.job.JobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 郑杰
 * @date 2018/10/05 19:52:11
 */
public interface JobLogRepo extends JpaRepository<JobLog,Long>, JpaSpecificationExecutor {

}
