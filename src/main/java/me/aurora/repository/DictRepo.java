package me.aurora.repository;

import me.aurora.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 郑杰
 * @date 2018/09/28 7:12:43
 */
public interface DictRepo extends JpaRepository<Dict,Long>, JpaSpecificationExecutor {
}
