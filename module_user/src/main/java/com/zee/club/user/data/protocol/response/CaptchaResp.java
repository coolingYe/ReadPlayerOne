package com.zee.club.user.data.protocol.response;

public class CaptchaResp {
    String randstr;
    String ticket;

    public String getRandstr() {
        return randstr;
    }

    public void setRandstr(String randstr) {
        this.randstr = randstr;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
