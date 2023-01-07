package com.zee.club.user.data.protocol.response;

public class MemberResp {
    /**
     * associationId : 3
     * associationName : 社团(勿删)
     * userId : 166984790992756739
     * userCode : zwn_166984790971785218
     * enterpriseScale : 1
     * registerTime : 2022-12-13 15:40:36
     * adminTelephone : 13042099751
     * adminName : 超级管理员2
     * applyStatus : 1
     * authNum : 3
     */

    private int associationId;
    private String associationName;
    private String userId;
    private String userCode;
    private int enterpriseScale;
    private String registerTime;
    private String adminTelephone;
    private String adminName;

    public String getAssociationCode() {
        return associationCode;
    }

    public void setAssociationCode(String associationCode) {
        this.associationCode = associationCode;
    }

    private String associationCode;
    private int applyStatus;
    private int authNum;

    public int getAssociationId() {
        return associationId;
    }

    public void setAssociationId(int associationId) {
        this.associationId = associationId;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getEnterpriseScale() {
        return enterpriseScale;
    }

    public void setEnterpriseScale(int enterpriseScale) {
        this.enterpriseScale = enterpriseScale;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getAdminTelephone() {
        return adminTelephone;
    }

    public void setAdminTelephone(String adminTelephone) {
        this.adminTelephone = adminTelephone;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }

    public int getAuthNum() {
        return authNum;
    }

    public void setAuthNum(int authNum) {
        this.authNum = authNum;
    }
}
