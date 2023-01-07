package com.zee.club.home.data.protocol.request;

import java.util.List;

/**
 * common use the RequestBody for Praise and Favorite
 */
public class PraiseFavoriteReq {
    private String objId;
    private String objName;
    private int objType;
    private String objUrl;

    public PraiseFavoriteReq(String objId, String objName, String objUrl) {
        this.objId = objId;
        this.objName = objName;
        this.objUrl = objUrl;
    }

    public PraiseFavoriteReq(String objId, String objName, int objType, String objUrl) {
        this.objId = objId;
        this.objName = objName;
        this.objType = objType;
        this.objUrl = objUrl;
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

    public static class delRequestBody {
        private List<String> objIdList;

        public delRequestBody(List<String> objIdList) {
            this.objIdList = objIdList;
        }

        public List<String> getObjIdList() {
            return objIdList;
        }

        public void setObjIdList(List<String> objIdList) {
            this.objIdList = objIdList;
        }
    }
}
