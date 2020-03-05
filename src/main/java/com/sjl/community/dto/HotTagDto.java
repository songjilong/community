package com.sjl.community.dto;

import lombok.Data;

/**
 * @author song
 * @create 2020/3/5 21:24
 */
@Data
public class HotTagDto implements Comparable<HotTagDto> {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(HotTagDto o) {
        return this.priority - o.getPriority();
    }
}
