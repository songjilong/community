package com.sjl.community.exception;

/**
 * @author song
 * @create 2020/2/20 16:02
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "这个问题不见了，换一个试试？"),
    COMMENT_NOT_FOUND(2002, "这个回复不见了，换一个试试？"),
    LOGIN_CONNECT_ERROR(2003, "第三方登录连接出了问题，再试一次？"),
    USER_NOT_LOGIN(2004, "您还未登录，请登录后再试试？"),
    TYPE_NOT_EXIST(2005, "评论的类型错误或不存在"),
    COMMENT_PARENT_NOT_EXIST(2006, "未选中任何问题或回复进行评论"),
    SYSTEM_ERROR(2007, "服务器冒烟儿了，稍后再试试吧~"),
    CONTENT_IS_EMPTY(2008, "不能输入空的内容哦~");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
