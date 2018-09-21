package me.aurora.service;

import me.aurora.domain.Picture;
import me.aurora.domain.User;
import me.aurora.repository.spec.PictureSpec;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/09/20 14:14:16
 */
@CacheConfig(cacheNames = "picture")
public interface PictureService {

    /**
     * 查询图床
     * @param pictureSpec
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Map getPictureInfo(PictureSpec pictureSpec, Pageable pageable);

    /**
     * 上传图片
     * @param file
     * @param user
     * @return
     */
    @CacheEvict(allEntries = true)
    void upload(MultipartFile file, User user);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    Picture findById(Long id);

    /**
     * 删除图片
     * @param picture
     */
    @CacheEvict(allEntries = true)
    void delete(Picture picture);
}
