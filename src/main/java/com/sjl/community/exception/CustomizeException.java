package com.sjl.community.exception;

/**
 * @author song
 * @create 2020/2/20 15:59
 */
public class CustomizeException extends RuntimeException{
    String message;

    public CustomizeException(CustomizeErrorCode message) {
        this.message = message.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
