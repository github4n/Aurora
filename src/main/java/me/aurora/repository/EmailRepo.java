package me.aurora.repository;

import me.aurora.domain.utils.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 郑杰
 * @date 2018/09/28 7:12:43
 */
public interface EmailRepo extends JpaRepository<EmailConfig,Long> {
}
