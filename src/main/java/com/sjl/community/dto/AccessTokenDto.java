package com.sjl.community.dto;

/**
 * @author song
 * @create 2020/2/14 16:06
 */
public class AccessTokenDto {
    private String client_id; //客户端ID。
    private String client_secret; //客户端密钥
    private String code; //作为对步骤 1 的响应而接收的代码。
    private String redirect_uri; //应用程序中的用户在授权后发送的URL
    private String state; //在步骤 1 中提供的不可猜测的随机字符串

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
