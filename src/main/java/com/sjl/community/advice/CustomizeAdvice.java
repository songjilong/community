package com.sjl.community.advice;

import com.alibaba.fastjson.JSON;
import com.sjl.community.dto.ResultDto;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author song
 * @create 2020/2/20 16:19
 */
@ControllerAdvice
public class CustomizeAdvice{

    @ExceptionHandler(CustomizeException.class)
    Object handleControllerException(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        //如果处理的是json数据，也响应json数据，否则返回视图
        if(StringUtils.equals(contentType, "application/json")){
            ResultDto resultDto;
            if (e instanceof CustomizeException) {
                //自定义异常信息
                resultDto = ResultDto.errorOf((CustomizeException) e);
            } else {
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            //响应结果
            PrintWriter writer = null;
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");

                writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally {
                assert writer != null;
                writer.close();
            }
            return null;
        }else{
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
