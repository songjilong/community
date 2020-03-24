package com.sjl.community.controller;

import com.sjl.community.dto.ResultDto;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author song
 * @create 2020/3/24 10:12
 */
@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendEmail")
    @ResponseBody
    public ResultDto sendEmail(@RequestParam(name = "email") String email){
        try {
            emailService.sendEmail(email);
            return ResultDto.okOf();
        } catch (Exception e) {
            return ResultDto.errorOf(CustomizeErrorCode.SEND_EMAIL_FAIL);
        }
    }
}
