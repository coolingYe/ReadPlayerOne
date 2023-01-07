package com.zee.club.home.data.protocol.response;

public class Praise {
    private int clientAgent;
    private String objId;
    private String objName;
    private int objType;
    private String objUrl;
    private String praiseId;
    private String praiseTime;
    private String systemCode;
    private String userId;

    public Praise(int clientAgent, String objId, String objName, int objType, String objUrl, String praiseId, String praiseTime, String systemCode, String userId) {
        this.clientAgent = clientAgent;
        this.objId = objId;
        this.objName = objName;
        this.objType = objType;
        this.objUrl = objUrl;
        this.praiseId = praiseId;
        this.praiseTime = praiseTime;
        this.systemCode = systemCode;
        this.userId = userId;
    }

    public int getClientAgent() {
        return clientAgent;
    }

    public void setClientAgent(int clientAgent) {
        this.clientAgent = clientAgent;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public String getObjUrl() {
        return objUrl;
    }

    public void setObjUrl(String objUrl) {
        this.objUrl = objUrl;
    }

    public String getPraiseId() {
        return praiseId;
    }

    public void setPraiseId(String praiseId) {
        this.praiseId = praiseId;
    }

    public String getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(String praiseTime) {
        this.praiseTime = praiseTime;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
