package com.sjl.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author song
 * @create 2020/2/14 17:501
 */
@Component
@ConfigurationProperties(prefix = "github")
@Data
public class GithubParams {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
}
