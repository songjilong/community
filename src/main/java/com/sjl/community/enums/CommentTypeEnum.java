package com.sjl.community.enums;

import com.sjl.community.dto.CommentDto;

/**
 * @author song
 * @create 2020/2/20 19:35
 */
public enum CommentTypeEnum {
    TYPE_QUESTION(1),
    TYPE_COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type){
        this.type = type;
    }

    public static boolean isNotExist(int type) {
        for(CommentTypeEnum commentType : CommentTypeEnum.values()){
            if(type == commentType.getType()){
                return false;
            }
        }
        return true;
    }

    public Integer getType() {
        return type;
    }
}
