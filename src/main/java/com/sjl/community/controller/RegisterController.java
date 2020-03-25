package com.sjl.community.controller;

import com.sjl.community.dto.ResultDto;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author song
 * @create 2020/3/24 9:58
 */
@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }

    @GetMapping("/sendEmail")
    @ResponseBody
    public ResultDto sendEmail(@RequestParam(name = "email") String email) {
        if (registerService.checkEmail(email)) {
            if(registerService.sendEmail(email)){
                return ResultDto.okOf();
            }
            return ResultDto.errorOf(CustomizeErrorCode.EMAIL_ALREADY_EXISTS);
        }
        return ResultDto.errorOf(CustomizeErrorCode.SEND_EMAIL_FAIL);
    }

    @PostMapping("/register")
    public String register(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("code") Integer code,
                           Model model){
        if(registerService.checkInfo(email, password, code) &&
                registerService.checkCode(email, code)){
            if(registerService.register(email, password)) {
                model.addAttribute("registerInfo", "注册成功，快去登录吧~");
            } else {
                model.addAttribute("registerInfo", "该邮箱已注册，换一个试试");
            }
        }else{
            model.addAttribute("registerInfo", "请输入正确信息");
        }
        return "register";
    }
}
