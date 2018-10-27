package me.aurora.repository;

import me.aurora.domain.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author 郑杰
 * @date 2018/08/23 9:58:19
 */
@Repository
public interface SysLogRepo extends JpaRepository<SysLog,Long>,JpaSpecificationExecutor {

    /**
     * 获取一个时间段的IP记录
     * @param toString
     * @param toString1
     * @return
     */
    @Query(value = "select count(*) FROM (select * FROM zj_syslog where createTime between ?1 and ?2 GROUP BY ip) as s",nativeQuery = true)
    Long findIp(String toString, String toString1);
}
