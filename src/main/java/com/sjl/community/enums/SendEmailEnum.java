package com.sjl.community.enums;

/**
 * @author song
 * @create 2020/3/31 16:23
 */
public enum SendEmailEnum {
    //注册
    REGISTER(0, "发送注册邮件"),
    //修改密码
    UPDATE_PWD(1,"发送密码修改邮件");

    private Integer code;
    private String msg;

    SendEmailEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
