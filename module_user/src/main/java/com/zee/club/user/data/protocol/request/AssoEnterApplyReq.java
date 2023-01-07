package com.zee.club.user.data.protocol.request;

public class AssoEnterApplyReq {

    /**
     * adminName :
     * adminTelephone :
     * applyUserName :
     * associationName :
     * code :
     * enterpriseScale : 0
     * uuid :
     */

    private String adminName;
    private String adminTelephone;
    private String applyUserName;
    private String associationName;
    private String code;
    private int enterpriseScale;
    private String uuid;


    public AssoEnterApplyReq(String adminName, String adminTelephone, String applyUserName, String associationName, String code, int enterpriseScale, String uuid) {
        this.adminName = adminName;
        this.adminTelephone = adminTelephone;
        this.applyUserName = applyUserName;
        this.associationName = associationName;
        this.code = code;
        this.enterpriseScale = enterpriseScale;
        this.uuid = uuid;
    }

}
