package com.sjl.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author song
 * @create 2020/2/24 15:29
 */
@Component
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunParams {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String firstKey;
}
