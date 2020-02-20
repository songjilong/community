package com.sjl.community.dto;

import lombok.Data;

/**
 * @author song
 * @create 2020/2/19 22:45
 */
@Data
public class CommentDto {
    private Long parentId;
    private String content;
    private Integer type;
}
