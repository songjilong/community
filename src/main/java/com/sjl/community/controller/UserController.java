package com.sjl.community.controller;

import com.sjl.community.model.User;
import com.sjl.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author song
 * @create 2020/2/27 20:21
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public String getInformationById(@PathVariable Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("userInfo", user);
        return "user";
    }
}
