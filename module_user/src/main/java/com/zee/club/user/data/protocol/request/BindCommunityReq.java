package com.zee.club.user.data.protocol.request;

public class BindCommunityReq {
    /**
     * associationId : 0
     * code :
     * enterpriseName :
     * enterpriseScale : 0
     * industryType :
     * objType : 0
     * telephone :
     * userName :
     * uuid :
     */


    private int associationId;
    private String code;
    private String enterpriseName;
    private int enterpriseScale;
    private String industryType;
    private int objType;
    private String telephone;
    private String applyUserName;
    private String uuid;

    public int getAssociationId() {
        return associationId;
    }

    public void setAssociationId(int associationId) {
        this.associationId = associationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public int getEnterpriseScale() {
        return enterpriseScale;
    }

    public void setEnterpriseScale(int enterpriseScale) {
        this.enterpriseScale = enterpriseScale;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
