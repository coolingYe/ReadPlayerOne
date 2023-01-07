package com.zee.club.user.data.protocol.request;

public class ForgetPasswordReq {

    /**
     * code :
     * confirmUserPwd :
     * newUserPwd :
     * telephone :
     * uuid :
     */

    private String code;
    private String confirmUserPwd;
    private String newUserPwd;
    private String telephone;
    private String uuid;

    public ForgetPasswordReq(String code, String confirmUserPwd, String newUserPwd, String telephone,String uuid) {
        this.code = code;
        this.confirmUserPwd = confirmUserPwd;
        this.newUserPwd = newUserPwd;
        this.telephone = telephone;
        this.uuid = uuid;
    }
}