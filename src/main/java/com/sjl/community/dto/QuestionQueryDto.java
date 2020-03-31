package com.sjl.community.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author song
 * @create 2020/3/6 17:56
 */
@Data
@Builder
public class QuestionQueryDto {
    private Integer offerIndex;
    private Integer pageNum;
    private Integer pageSize;
    private Long creatorId;
    private String search;
    private String tag;
    private String sort;
    private Long time;
}
