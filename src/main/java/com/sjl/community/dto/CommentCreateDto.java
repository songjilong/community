package com.sjl.community.dto;

import lombok.Data;

/**
 * @author song
 * @create 2020/2/19 22:45
 * 前端传递的评论数据
 */
@Data
public class CommentCreateDto {
    private Long parentId;
    private String content;
    private Integer type;
}
