package com.sjl.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author song
 * @create 2020/2/14 17:50
 */
@Component
@ConfigurationProperties(prefix = "gitee")
@Data
public class GiteeParams {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
}
