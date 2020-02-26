package com.sjl.community.dto;

import lombok.Data;

/**
 * @author song
 * @create 2020/2/26 17:17
 */
@Data
public class QQUser {
    private Long ret;//返回码
    private String msg;//错误信息
    private String nickname;
    private String gender;
    private String figureurl_qq_1;//头像
}
