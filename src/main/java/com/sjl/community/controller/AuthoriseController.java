package com.sjl.community.controller;

import com.sjl.community.config.GiteeParams;
import com.sjl.community.config.GithubParams;
import com.sjl.community.config.QQParams;
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
import com.sjl.community.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private GiteeParams giteeParams;
    @Autowired
    private QQParams qqParams;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private QQProvider qqProvider;
    @Autowired
    private GiteeProvider giteeProvider;

    private AccessTokenDto accessTokenDto = new AccessTokenDto();

    private static final int COOKIE_EXPIRY = 60 * 60 * 24 * 7;

    //Github授权
    @GetMapping("/githubCallback")
    public String githubCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 HttpServletResponse response) {
        setAccessTokenDto(code, state, githubParams.getClient_id(), githubParams.getClient_secret(), githubParams.getRedirect_uri());
        //获取access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        //根据accessToken获取用户信息
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);

        if (githubUser != null && githubUser.getId() != null) {
            String token = UUID.randomUUID().toString();
            //设置user信息
            setUserInfo(token, githubUser.getName(), githubUser.getAvatarUrl(), "Github-"+githubUser.getId(), githubUser.getBio());
            CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
            return "redirect:/";
        } else {
            log.error("githubUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }

    //Gitee授权
    @GetMapping("/giteeCallback")
    public String giteeCallback(@RequestParam("code") String code,
                                @RequestParam("state") String state,
                                HttpServletResponse response) {
        setAccessTokenDto(code, state, giteeParams.getClient_id(), giteeParams.getClient_secret(), giteeParams.getRedirect_uri());
        String accessToken = giteeProvider.getAccessToken(accessTokenDto);
        GiteeUser giteeUser = giteeProvider.getGiteeUser(accessToken);
        if (giteeUser != null && giteeUser.getId() != null) {
            String token = UUID.randomUUID().toString();
            //设置user信息
            setUserInfo(token, giteeUser.getName(), giteeUser.getAvatarUrl(), "Gitee-"+giteeUser.getId(), giteeUser.getBio());
            CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
            return "redirect:/";
        } else {
            log.error("giteeUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }

    //QQ授权
    @GetMapping("/qqCallback")
    public String qqCallback(HttpServletResponse response,
                             @RequestParam("code") String code,
                             @RequestParam("state") String state) {
        String accessToken = qqProvider.getAccessToken(code);
        String openId = qqProvider.getOpenId(accessToken);
        QQUser qqUser = qqProvider.getQQUser(accessToken, openId);
        if (qqUser != null && qqUser.getRet() == 0) {
            String token = UUID.randomUUID().toString();
            setUserInfo(token, qqUser.getNickname(), qqUser.getFigureurl_qq_2(), "QQ-"+openId, null);
            CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
            //返回首页
            return "redirect:/";
        } else {
            log.error("qqUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }


    private void setAccessTokenDto(String code, String state, String clientId, String clientSecret, String redirectUri) {
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
    }

    //设置用户信息
    private void setUserInfo(String token, String name, String avatarUrl, String accountId, String bio){
        User user = new User();
        user.setToken(token);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);
        user.setAccountId(accountId);
        user.setBio(bio);
        userService.createOrUpdateUser(user);
    }
}
