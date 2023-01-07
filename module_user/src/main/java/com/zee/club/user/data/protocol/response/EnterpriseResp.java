package com.zee.club.user.data.protocol.response;

public class EnterpriseResp {


    /**
     * enterpriseId : 1
     * enterpriseName : 测试企业
     * userId : 12345678900
     * userCode : 企业账号
     * industryType : 3
     * enterpriseScale : 2
     * registerTime : 2022-12-15 17:18:10
     * adminTelephone : 17820293617
     * adminName : 龙悦的爸爸
     * applyStatus : 2
     * cancelTime : 2022-12-15 17:17:17
     * authNum : 10
     */

    private int enterpriseId;
    private String enterpriseName;
    private String userId;
    private String userCode;
    private String industryType;
    private int enterpriseScale;
    private String registerTime;
    private String adminTelephone;
    private String adminName;
    private int applyStatus;
    private String cancelTime;
    private int authNum;

    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
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

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
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

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public int getAuthNum() {
        return authNum;
    }

    public void setAuthNum(int authNum) {
        this.authNum = authNum;
    }
}
