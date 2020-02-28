package com.sjl.community.dto;

import com.sjl.community.model.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author song
 * @create 2020/2/17 13:32
 */
@Data
public class QuestionDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String tags;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private Integer top;
    private User user;
}
