package com.zee.club.home.data.protocol.request;

public class EnergyAddReq {
    private int energySource;
    private String objId;
    private String objName;
    private int objType;

    public EnergyAddReq(int energySource, String objId, String objName, int objType) {
        this.energySource = energySource;
        this.objId = objId;
        this.objName = objName;
        this.objType = objType;
    }



    public int getEnergySource() {
        return energySource;
    }

    public void setEnergySource(int energySource) {
        this.energySource = energySource;
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
}
