package com.sjl.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author song
 * @create 2020/2/27 20:21
 */
@Controller
public class InformationController {

    @RequestMapping("/information")
    public String getInformation(){
        return "information";
    }
}
