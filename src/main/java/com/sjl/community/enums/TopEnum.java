package com.sjl.community.enums;

/**
 * @author song
 * @create 2020/3/31 16:23
 */
public enum TopEnum {
    //设为顶置
    SET_TOP(1),
    //取消顶置
    UNSET_TOP(0);

    private Integer type;

    TopEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isNotExist(int type){
        for(TopEnum topEnum : TopEnum.values()){
            if(topEnum.getType() != type){
                return false;
            }
        }
        return true;
    }
}
