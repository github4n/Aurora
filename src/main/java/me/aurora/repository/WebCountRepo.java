package me.aurora.repository;

import me.aurora.domain.utils.WebCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 郑杰
 * @date 2018/10/27 13:11:33
 */
public interface WebCountRepo extends JpaRepository<WebCount,Long> {

    /**
     * 获得一个时间段的登录记录
     * @param toString
     * @param toString1
     * @return
     */
    @Query(value = "select * FROM zj_web_count where " +
            "createTime between ?1 and ?2",nativeQuery = true)
    List<WebCount> findPv(String toString, String toString1);

    /**
     * 根据日期查询
     * @param toString
     * @return
     */
    WebCount findByDate(String toString);
}
