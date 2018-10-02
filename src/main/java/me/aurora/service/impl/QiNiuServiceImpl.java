package me.aurora.service.impl;

import cn.hutool.http.HttpStatus;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import jdk.nashorn.internal.ir.Optimistic;
import me.aurora.config.exception.AuroraException;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.utils.QiniuConfig;
import me.aurora.domain.utils.QiniuContent;
import me.aurora.repository.QiNiuConfigRepo;
import me.aurora.repository.QiniuContentRepo;
import me.aurora.repository.spec.QiNiuContentSpec;
import me.aurora.service.QiNiuService;
import me.aurora.util.PageUtil;
import me.aurora.util.QiNiuUtil;
import me.aurora.util.SizeUtil;
import me.aurora.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired
    private QiNiuConfigRepo qiNiuConfigRepo;

    @Autowired
    private QiniuContentRepo qiniuContentRepo;

    private final String TYPE = "公开";

    @Override
    public QiniuConfig findById(long id) {
        Optional<QiniuConfig> qiniuConfig = qiNiuConfigRepo.findById(id);
        if(qiniuConfig.isPresent()){
            return qiniuConfig.get();
        } else {
            return null;
        }

    }

    @Override
    public QiniuConfig updateConfig(QiniuConfig qiniuConfig) {
        if (!(qiniuConfig.getHost().toLowerCase().startsWith("http://")||qiniuConfig.getHost().toLowerCase().startsWith("https://"))) {
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"外链域名必须以http://或者https://开头");
        }
        qiNiuConfigRepo.saveAndFlush(qiniuConfig);
        return qiniuConfig;
    }

    @Override
    public Map getContentInfo(QiNiuContentSpec qiNiuContentSpec, Pageable pageable) {
        Page<QiniuContent> qiniuContents = qiniuContentRepo.findAll(qiNiuContentSpec,pageable);
        return PageUtil.buildPage(qiniuContents.getContent(),qiniuContents.getTotalElements());
    }

    @Override
    public void upload(MultipartFile file,QiniuConfig qiniuConfig) {

        /**
         * 构造一个带指定Zone对象的配置类
         */
        Configuration cfg = QiNiuUtil.getConfiguration(qiniuConfig.getZone());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());
        try {
            Response response = uploadManager.put(file.getBytes(), QiNiuUtil.getKey(file.getOriginalFilename()), upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //存入数据库
            QiniuContent qiniuContent = new QiniuContent();
            qiniuContent.setBucket(qiniuConfig.getBucket());
            qiniuContent.setType(qiniuConfig.getType());
            qiniuContent.setKey(putRet.key);
            qiniuContent.setUrl(qiniuConfig.getHost()+"/"+putRet.key);
            qiniuContent.setSize(SizeUtil.getSize(Integer.parseInt(file.getSize()+"")));
            qiniuContentRepo.save(qiniuContent);
        } catch (Exception e) {
           throw new AuroraException(HttpStatus.HTTP_INTERNAL_ERROR,e.getMessage());
        }
    }

    @Override
    public QiniuContent findByContentId(Long id) {
        Optional<QiniuContent> qiniuContent = qiniuContentRepo.findById(id);
        ValidationUtil.isNull(qiniuContent,"文件不存在");
        return qiniuContent.get();
    }

    @Override
    public ResponseEntity download(QiniuContent content,QiniuConfig config) throws UnsupportedEncodingException {
        String finalUrl = null;
        if(TYPE.equals(content.getType())){
            finalUrl  = content.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            /**
             * 1小时，可以自定义链接过期时间
             */
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(content.getUrl(), expireInSeconds);
        }
        return ResponseEntity.ok(finalUrl);
    }

    @Override
    public void delete(QiniuContent content, QiniuConfig config) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = QiNiuUtil.getConfiguration(config.getZone());
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getKey());
            qiniuContentRepo.delete(content);
        } catch (QiniuException ex) {
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    @Override
    public void synchronize(QiniuConfig config) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = QiNiuUtil.getConfiguration(config.getZone());
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            QiniuContent qiniuContent = null;
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                if(qiniuContentRepo.findByKey(item.key) == null){
                    qiniuContent = new QiniuContent();
                    qiniuContent.setSize(SizeUtil.getSize(Integer.parseInt(item.fsize+"")));
                    qiniuContent.setKey(item.key);
                    qiniuContent.setType(config.getType());
                    qiniuContent.setBucket(config.getBucket());
                    qiniuContent.setUrl(config.getHost()+"/"+item.key);
                    qiniuContentRepo.save(qiniuContent);
                }
            }
        }

    }
}
