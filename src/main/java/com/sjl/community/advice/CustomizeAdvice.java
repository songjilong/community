package com.sjl.community.advice;

import com.sjl.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

/**
 * @author song
 * @create 2020/2/20 16:19
 */
@ControllerAdvice
public class CustomizeAdvice{

    @ExceptionHandler(CustomizeException.class)
    ModelAndView handleControllerException(Throwable ex, Model model) {
        if(ex instanceof CustomizeException){
            model.addAttribute("message", ex.getMessage());
        }else{
            model.addAttribute("message", "服务器冒烟儿了，稍后再试试吧~");
        }
        return new ModelAndView("error");
    }
}
