package me.aurora.repository;

import me.aurora.domain.utils.AlipayConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 郑杰
 * @date 2018/09/30 14:17:19
 */
public interface AlipayRepo extends JpaRepository<AlipayConfig,Long> {
}
