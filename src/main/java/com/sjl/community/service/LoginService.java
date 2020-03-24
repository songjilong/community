package com.sjl.community.service;

import com.sjl.community.mapper.UserMapper;
import com.sjl.community.model.User;
import com.sjl.community.model.UserExample;
import com.sjl.community.utils.CodecUtils;
import com.sjl.community.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author song
 * @create 2020/3/24 17:11
 */
@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    private static final int COOKIE_EXPIRY = 60 * 60 * 24 * 7;

    /**
     * 登录
     * @param email
     * @param password
     */
    public boolean checkLogin(String email, String password, HttpServletResponse response) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        if(users.size() == 1){
            User user = users.get(0);
            password = CodecUtils.md5Hex(password, user.getSalt());
            if(StringUtils.equals(user.getPassword(), password)){
                CookieUtils.setCookie(response, "token", user.getToken(), COOKIE_EXPIRY);
                return true;
            }
        }
        return false;
    }
}
