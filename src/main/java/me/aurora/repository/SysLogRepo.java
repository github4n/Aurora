package me.aurora.repository;

import me.aurora.domain.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author 郑杰
 * @date 2018/08/23 9:58:19
 */
@Repository
public interface SysLogRepo extends JpaRepository<SysLog,Long>,JpaSpecificationExecutor {
}
