package com.sjl.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author song
 * @create 2020/2/13 19:49
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "username", required = false, defaultValue = "张三") String username, Model model) {
        model.addAttribute("username", username);
        return "index";
    }
}
