package com.sjl.community.controller;

import com.sjl.community.config.GithubParams;
import com.sjl.community.dto.AccessTokenDto;
import com.sjl.community.dto.GithubUser;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.User;
import com.sjl.community.provider.GithubProvider;
import com.sjl.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author song
 * @create 2020/2/14 16:01
 */
@Slf4j
@Controller
public class AuthoriseController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private GithubParams githubParams;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(githubParams.getClient_id());
        accessTokenDto.setClient_secret(githubParams.getClient_secret());
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(githubParams.getRedirect_uri());
        accessTokenDto.setState(state);

        //获取access_token
        String access_token = githubProvider.getAccessToken(accessTokenDto);
        //根据accessToken获取用户信息
        GithubUser githubUser = githubProvider.getGithubUser(access_token);

        if (githubUser != null && githubUser.getId() != null) {
            //设置user信息
            User user = new User();
            user.setAccountId("Github"+ githubUser.getId());
            user.setName(githubUser.getName());
            user.setBio(githubUser.getBio());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdateUser(user);
            //将token存入cookie
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //清除session
        request.getSession().removeAttribute("user");
        //清除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        //返回到主页
        return "redirect:/";
    }
}
