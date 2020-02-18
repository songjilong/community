package com.sjl.community.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author song
 * @create 2020/2/14 16:09
 */
@Data
public class GithubUser implements Serializable {
    private Long id;
    private String name;
    private String bio;
    private String avatarUrl;
}
