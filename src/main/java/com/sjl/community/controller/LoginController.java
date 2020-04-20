package com.sjl.community.controller;

import com.sjl.community.service.LoginService;
import com.sjl.community.service.RegisterService;
import com.sjl.community.service.UserService;
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
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterService registerService;

    @GetMapping("/login")
    public String toLogin(Model model) {
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

    @PostMapping("/updatePwd")
    public String updatePwd(@RequestParam("email") String email,
                            @RequestParam("code") Integer code,
                            @RequestParam("pwd1") String pwd1,
                            @RequestParam("pwd2") String pwd2,
                            Model model) {
        if (!registerService.checkEmail(email) || !registerService.checkCode(email, code)) {
            model.addAttribute("updatePwdInfo", "输入信息有误");
        } else if (!StringUtils.equals(pwd1, pwd2)) {
            model.addAttribute("updatePwdInfo", "两次输入的密码不一致");
        } else {
            userService.updatePwd(email, pwd1);
            model.addAttribute("updatePwdInfo", "密码修改成功");
        }
        return "login";
    }
}
