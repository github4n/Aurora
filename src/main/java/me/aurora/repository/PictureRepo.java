package me.aurora.repository;

import me.aurora.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 郑杰
 * @date 2018/09/20 14:16:05
 */
public interface PictureRepo extends JpaRepository<Picture,Long>, JpaSpecificationExecutor {
}
