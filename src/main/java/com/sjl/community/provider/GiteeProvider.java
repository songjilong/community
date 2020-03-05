package com.sjl.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sjl.community.config.GiteeParams;
import com.sjl.community.dto.AccessTokenDto;
import com.sjl.community.dto.GiteeUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author song
 * @create 2020/2/14 16:16
 */
@Component
@Slf4j
public class GiteeProvider {
    @Autowired
    private GiteeParams giteeParams;

    private OkHttpClient client = new OkHttpClient();

    /**
     * 获取AccessToken
     */
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));

        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            return JSONObject.parseObject(str).getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Gitee获取access_token失败");
        }
        return null;
    }

    /**
     * 根据access_token获取用户信息
     */
    public GiteeUser getGiteeUser(String access_token) {
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+access_token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return JSON.parseObject(response.body().string(),GiteeUser.class);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Gitee获取User信息失败");
        }
        return null;
    }
}
