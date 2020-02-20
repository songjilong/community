package com.sjl.community.dto;

import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author song
 * @create 2020/2/20 19:31
 */
@Data
@AllArgsConstructor
public class ResultDto {
    private Integer code;
    private String message;

    public static ResultDto errorOf(Integer code, String message) {
        return new ResultDto(code, message);
    }

    public static ResultDto errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static Object okOf() {
        return new ResultDto(2000, "请求成功");
    }
}
