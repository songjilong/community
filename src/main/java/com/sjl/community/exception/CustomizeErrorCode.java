package com.sjl.community.exception;

/**
 * @author song
 * @create 2020/2/20 16:02
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找到问题不在了，要不要换个试试？"),
    LOGIN_CONNECT_ERROR("第三方登录连接出了问题，再试一次？"),
    USER_NOT_LOGIN("您还未登录，请登录后再试试？");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
