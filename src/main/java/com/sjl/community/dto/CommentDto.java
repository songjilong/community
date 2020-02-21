package com.sjl.community.dto;

import com.sjl.community.model.User;
import lombok.Data;

/**
 * @author song
 * @create 2020/2/21 12:18
 * 数据库的评论数据
 */
@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private User user;
    private Integer commentCount;
}
