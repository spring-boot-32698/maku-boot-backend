package net.maku.storage.properties;

import lombok.Data;
import net.maku.storage.enums.StorageTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Storage settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    /**
     * Whether to enable storage
     */
    private boolean enabled;
    /**
     * Common settings
     */
    private StorageConfig config;
    /**
     * Local settings
     */
    private LocalStorageProperties local;
    /**
     * Alibaba Cloud settings
     */
    private AliyunStorageProperties aliyun;
    /**
     * Qiniu Cloud settings
     */
    private QiniuStorageProperties qiniu;
    /**
     * Huawei Cloud settings
     */
    private HuaweiStorageProperties huawei;
    /**
     * MinioSettings
     */
    private MinioStorageProperties minio;
    /**
     * Tencent Cloud settings
     */
    private TencentStorageProperties tencent;

    @Data
    public static class StorageConfig {
        /**
         * Access domain
         */
        private String domain;
        /**
         * Configure path prefix
         */
        private String prefix;
        /**
         * Storage type
         */
        private StorageTypeEnum type;
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.local")
    public LocalStorageProperties localStorageProperties() {
        return new LocalStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.aliyun")
    public AliyunStorageProperties aliyunStorageProperties() {
        return new AliyunStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.qiniu")
    public QiniuStorageProperties qiniuStorageProperties() {
        return new QiniuStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.huawei")
    public HuaweiStorageProperties huaweiStorageProperties() {
        return new HuaweiStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.minio")
    public MinioStorageProperties minioStorageProperties() {
        return new MinioStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.tencent")
    public TencentStorageProperties tencentStorageProperties() {
        return new TencentStorageProperties();
    }

}
