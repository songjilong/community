package com.sjl.community.controller;

import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author song
 * @create 2020/2/13 19:49
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        for(Cookie cookie : request.getCookies()){
            if("token".equals(cookie.getName())){
                String token = cookie.getValue();
                User user  = userMapper.findByToken(token);
//                System.out.println(user);
                if(user != null){
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return "index";
    }
}
