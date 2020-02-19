package com.sjl.community.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author song
 * @create 2020/2/16 13:18
 */
@Data
public class User implements Serializable {
    private Long id;
    private String accountId;
    private String name;
    private String bio;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
