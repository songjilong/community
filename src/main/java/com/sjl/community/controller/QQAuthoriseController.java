package com.sjl.community.controller;

import com.sjl.community.dto.QQUser;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.User;
import com.sjl.community.provider.QQProvider;
import com.sjl.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author song
 * @create 2020/2/26 17:26
 */
@Controller
@Slf4j
public class QQAuthoriseController {

    @Autowired
    private QQProvider qqProvider;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/qqcallback")
    public String callback(HttpServletResponse response,
                           @RequestParam("code") String code,
                           @RequestParam("state") String state) throws IOException {
        String accessToken = qqProvider.getAccessToken(code);
        String openId = qqProvider.getOpenId(accessToken);
        User user = new User();
        QQUser qqUser = qqProvider.getQQUser(accessToken, openId);
        if(qqUser != null && qqUser.getRet() == 0){
            user.setAccountId("QQ"+openId);
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
        }else{
            assert qqUser != null;
            log.error("qqUser获取失败，返回的错误信息：{}",qqUser.getMsg());
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }
}
