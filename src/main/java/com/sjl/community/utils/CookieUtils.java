package com.sjl.community.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author song
 * @create 2020/3/24 17:24
 */
public class CookieUtils {

    /**
     * 添加指定cookie，并指定有效期
     * @param response
     * @param name
     * @param value
     * @param expiry
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    /**
     * 清空指定cookie
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletResponse response, String name){
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
