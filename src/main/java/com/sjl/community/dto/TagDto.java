package com.sjl.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author song
 * @create 2020/2/22 19:39
 */
@Data
public class TagDto {
    private String categoryName;
    private List<String> tags;
}
