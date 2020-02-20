package com.sjl.community.exception;

/**
 * @author song
 * @create 2020/2/20 15:59
 */
public class CustomizeException extends RuntimeException{
    Integer code;
    String message;

    public CustomizeException(CustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
