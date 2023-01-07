package com.zee.club.user.data.protocol.request;

public class PhoneLoginReq {


    /**
     * code :
     * loginName :
     * randstr :
     * ticket :
     * type :
     * userPwd :
     * uuid :
     */

    private String code;
    private String loginName;
    private String randstr;
    private String ticket;
    private String type;
    private String userPwd;
    private String uuid;

    public PhoneLoginReq() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
