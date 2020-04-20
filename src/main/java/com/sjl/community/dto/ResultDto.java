package com.sjl.community.dto;

import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import lombok.Data;

/**
 * @author song
 * @create 2020/2/20 19:31
 */
@Data
public class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResultDto<T> errorOf(Integer code, String message) {
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static <T> ResultDto<T> errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> ResultDto<T> errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static <T> ResultDto<T> okOf() {
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setCode(2000);
        resultDto.setMessage("请求成功");
        return resultDto;
    }

    public static <T> ResultDto<T> okOf(T t){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setCode(2000);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return  resultDto;
    }
}
