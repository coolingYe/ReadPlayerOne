package com.zee.club.user.data.protocol.response;

public class PhoneLoginResp {
    String token;

    public String getToken() {
        return token;
    }

    public PhoneLoginResp(String token) {
        this.token = token;
    }
}
