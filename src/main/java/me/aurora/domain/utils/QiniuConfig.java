package me.aurora.domain.utils;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 七牛云对象存储配置类
 * @author 郑杰
 * @date 2018/10/02 9:31:11
 */
@Data
@Entity
@Table(name = "zj_qiniu_config")
public class QiniuConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 一个账号最多拥有两对密钥(Access/Secret Key)
     */
    @NotBlank(groups = Update.class)
    private String accessKey;

    /**
     * 一个账号最多拥有两对密钥(Access/Secret Key)
     */
    @NotBlank(groups = Update.class)
    private String secretKey;

    /**
     * 存储空间名称作为唯一的 Bucket 识别符
     */
    @NotBlank(groups = Update.class)
    private String bucket;

    /**
     * Zone表示与机房的对应关系
     * 华东	Zone.zone0()
     * 华北	Zone.zone1()
     * 华南	Zone.zone2()
     * 北美	Zone.zoneNa0()
     * 东南亚	Zone.zoneAs0()
     */
    @NotBlank(groups = Update.class)
    private String zone;

    /**
     * 外链域名，可自定义，需在七牛云绑定
     */
    @NotBlank(groups = Update.class)
    private String host;

    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";

    public interface Update {}
}
