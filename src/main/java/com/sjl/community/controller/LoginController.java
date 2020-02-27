package com.sjl.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author song
 * @create 2020/2/27 10:47
 */
@Controller
public class LoginController {

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
