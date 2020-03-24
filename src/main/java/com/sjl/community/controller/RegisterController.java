package com.sjl.community.controller;

import com.sjl.community.dto.ResultDto;
import com.sjl.community.service.EmailService;
import com.sjl.community.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author song
 * @create 2020/3/24 9:58
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("code") Integer code){
        if(emailService.checkCode(email, code)){
            ResultDto resultDto = registerService.register(email, password);
            return "redirect:/login";
        }else{
            return "register";
        }
    }
}
