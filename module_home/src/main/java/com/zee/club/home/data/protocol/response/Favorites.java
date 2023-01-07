package com.zee.club.home.data.protocol.response;

public class Favorites {
    private int clientAgent;
    private String favoriteId;
    private String favoriteTime;
    private String objDesc;
    private String objId;
    private String objName;
    private int objType;
    private String objUrl;
    private String systemCode;

    public Favorites(int clientAgent, String favoriteId, String favoriteTime, String objDesc, String objId, String objName, int objType, String objUrl, String systemCode) {
        this.clientAgent = clientAgent;
        this.favoriteId = favoriteId;
        this.favoriteTime = favoriteTime;
        this.objDesc = objDesc;
        this.objId = objId;
        this.objName = objName;
        this.objType = objType;
        this.objUrl = objUrl;
        this.systemCode = systemCode;
    }

    public int getClientAgent() {
        return clientAgent;
    }

    public void setClientAgent(int clientAgent) {
        this.clientAgent = clientAgent;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteTime() {
        return favoriteTime;
    }

    public void setFavoriteTime(String favoriteTime) {
        this.favoriteTime = favoriteTime;
    }

    public String getObjDesc() {
        return objDesc;
    }

    public void setObjDesc(String objDesc) {
        this.objDesc = objDesc;
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

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
