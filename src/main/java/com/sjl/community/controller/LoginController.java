package com.sjl.community.controller;

import com.sjl.community.service.LoginService;
import com.sjl.community.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author song
 * @create 2020/2/27 10:47
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String toLogin() {

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("email") String email,
                          @RequestParam("password") String password,
                          HttpServletResponse response, Model model) {
        if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password)) {
            boolean flag = loginService.checkLogin(email, password, response);
            if (flag) {
                return "redirect:/";
            }
        }
        model.addAttribute("loginError", "用户名或密码错误！");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //清除session
        request.getSession().removeAttribute("user");
        //清除cookie
        CookieUtils.removeCookie(response, "token");
        //返回到主页
        return "redirect:/";
    }
}
