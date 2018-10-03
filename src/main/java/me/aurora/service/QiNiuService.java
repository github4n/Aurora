package me.aurora.service;

import me.aurora.domain.ResponseEntity;
import me.aurora.domain.utils.QiniuConfig;
import me.aurora.domain.utils.QiniuContent;
import me.aurora.repository.spec.QiNiuContentSpec;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/02 10:04:35
 */
@CacheConfig(cacheNames = "qiNiu")
public interface QiNiuService {

    /**
     * 根据id查配置
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    QiniuConfig findById(long id);

    /**
     * 修改配置
     * @param qiniuConfig
     * @return
     */
    @CacheEvict(allEntries = true)
    QiniuConfig updateConfig(QiniuConfig qiniuConfig);

    /**
     * 获取所有在数据库存在的文件列表
     * @param qiNiuContentSpec
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Map getContentInfo(QiNiuContentSpec qiNiuContentSpec, Pageable pageable);

    /**
     * 上传文件
     * @param file
     * @param qiniuConfig
     */
    @CacheEvict(allEntries = true)
    void upload(MultipartFile file,QiniuConfig qiniuConfig);

    /**
     * 查询待下载的文件
     * @param id
     * @return
     */
    @Cacheable(key = "'content:'+#p0")
    QiniuContent findByContentId(Long id);

    /**
     * 下载文件
     * @param content
     * @param config
     * @return
     * @throws UnsupportedEncodingException
     */
    ResponseEntity download(QiniuContent content,QiniuConfig config) throws UnsupportedEncodingException;

    /**
     * 删除文件
     * @param content
     * @param config
     * @return
     */
    @CacheEvict(allEntries = true)
    void delete(QiniuContent content, QiniuConfig config);

    /**
     * 同步数据
     * @param config
     */
    @CacheEvict(allEntries = true)
    void synchronize(QiniuConfig config);
}
