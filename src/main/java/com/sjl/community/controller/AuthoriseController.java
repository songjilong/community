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
import lombok.extern.slf4j.Slf4j;
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

    //Github授权
    @GetMapping("/githubCallback")
    public String githubCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 HttpServletResponse response) {
        setAccessTokenDto(code, state, githubParams.getClient_id(), githubParams.getClient_secret(), githubParams.getRedirect_uri());
        //获取access_token
        String access_token = githubProvider.getAccessToken(accessTokenDto);
        //根据accessToken获取用户信息
        GithubUser githubUser = githubProvider.getGithubUser(access_token);

        if (githubUser != null && githubUser.getId() != null) {
            String token = UUID.randomUUID().toString();
            //设置user信息
            setUserInfo(token, githubUser.getName(), githubUser.getAvatarUrl(), "Github-"+githubUser.getId(), githubUser.getBio());
            addCookieForToken(response, token);
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
            addCookieForToken(response, token);
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
                             @RequestParam("state") String state) throws IOException {
        setAccessTokenDto(code, state, qqParams.getClient_id(), qqParams.getClient_secret(), qqParams.getRedirect_uri());
        String accessToken = qqProvider.getAccessToken(accessTokenDto);
        String openId = qqProvider.getOpenId(accessToken);
        QQUser qqUser = qqProvider.getQQUser(accessToken, openId);
        if (qqUser != null && qqUser.getRet() == 0) {
            String token = UUID.randomUUID().toString();
            setUserInfo(token, qqUser.getNickname(), qqUser.getFigureurl_qq_1(), "QQ-"+openId, null);
            addCookieForToken(response, token);
            //返回首页
            return "redirect:/";
        } else {
            log.error("qqUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }


    private void setAccessTokenDto(String code, String state, String client_id, String client_secret, String redirect_uri) {
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirect_uri);
        accessTokenDto.setState(state);
    }

    //将token添加到cookie
    private void addCookieForToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60 * 60 * 24 * 7);//7天有效期
        response.addCookie(cookie);
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
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
