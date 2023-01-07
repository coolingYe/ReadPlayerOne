package com.zee.club.user.data.protocol.request;

public class PhoneCaptchaReq {

    int type;
    String telephone;
    String randstr;

    public PhoneCaptchaReq(String telephone, int type, String randstr, String ticket) {
        this.type = type;
        this.telephone = telephone;
//        this.randstr = randstr;
//        this.ticket = ticket;
    }

    String ticket;
}
