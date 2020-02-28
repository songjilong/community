package com.sjl.community.controller;

import com.sjl.community.config.GiteeParams;
import com.sjl.community.config.GithubParams;
import com.sjl.community.dto.AccessTokenDto;
import com.sjl.community.dto.GiteeUser;
import com.sjl.community.dto.GithubUser;
import com.sjl.community.dto.QQUser;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.User;
import com.sjl.community.provider.GiteeProvider;
import com.sjl.community.provider.GithubProvider;
import com.sjl.community.provider.QQProvider;
import com.sjl.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private GiteeParams giteeParams;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private QQProvider qqProvider;
    @Autowired
    private GiteeProvider giteeProvider;

    //Github授权
    @GetMapping("/github/callback")
    public String githubCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 HttpServletResponse response,
                                 HttpServletRequest request) {
        AccessTokenDto accessTokenDto = getAccessTokenDto(code, state, githubParams.getClient_id(), githubParams.getClient_secret(), githubParams.getRedirect_uri());
        //获取access_token
        String access_token = githubProvider.getAccessToken(accessTokenDto);
        //根据accessToken获取用户信息
        GithubUser githubUser = githubProvider.getGithubUser(access_token);

        if (githubUser != null && githubUser.getId() != null) {
            //设置user信息
            User user = new User();
            user.setAccountId("Github-" + githubUser.getId());
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

    //Gitee授权
    @GetMapping("/gitee/callback")
    public String giteeCallback(@RequestParam("code") String code,
                                @RequestParam("state") String state,
                                HttpServletResponse response,
                                HttpServletRequest request) {
        AccessTokenDto accessTokenDto = getAccessTokenDto(code, state, giteeParams.getClient_id(), giteeParams.getClient_secret(), giteeParams.getRedirect_uri());
        if (StringUtils.equals(state, "giteelogin")) {
            String accessToken = giteeProvider.getAccessToken(accessTokenDto);
            GiteeUser giteeUser = giteeProvider.getGiteeUser(accessToken);
            if (giteeUser != null && giteeUser.getId() != null) {
                User user = new User();
                user.setAccountId("Gitee-" + giteeUser.getId());
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setAvatarUrl(giteeUser.getAvatarUrl());
                user.setName(giteeUser.getName());
                user.setBio(giteeUser.getBio());
                userService.createOrUpdateUser(user);
                //将token存入cookie
                response.addCookie(new Cookie("token", token));
                return "redirect:/";
            } else {
                throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
            }
        } else {
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }

    //QQ授权
    @GetMapping("/qqcallback")
    public String qqCallback(HttpServletResponse response,
                             @RequestParam("code") String code,
                             @RequestParam("state") String state,
                             HttpServletRequest request) throws IOException {
        String accessToken = qqProvider.getAccessToken(code);
        String openId = qqProvider.getOpenId(accessToken);
        User user = new User();
        QQUser qqUser = qqProvider.getQQUser(accessToken, openId);
        if (qqUser != null && qqUser.getRet() == 0) {
            user.setAccountId("QQ-" + openId);
            user.setToken(UUID.randomUUID().toString());
            user.setName(qqUser.getNickname());
            user.setAvatarUrl(qqUser.getFigureurl_qq_1());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userService.createOrUpdateUser(user);
            //将token存入cookie
            response.addCookie(new Cookie("token", user.getToken()));
            //返回首页
            return "redirect:/";
        } else {
            assert qqUser != null;
            log.error("qqUser获取失败，返回的错误信息：{}", qqUser.getMsg());
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }

    private AccessTokenDto getAccessTokenDto(String code, String state, String client_id, String client_secret, String redirect_uri) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirect_uri);
        accessTokenDto.setState(state);
        return accessTokenDto;
    }
}
