package com.zee.club.user.data.protocol.response;

public class ApprovalResp {
    /**
     * orderId : 175309626022309890
     * orderName : 企业用户:tangsongwang申请绑定
     * associationId : 3
     * objId : 11
     * orderType : 2
     * orderStatus : 1
     * applyTime : 2023-01-05 15:00:31
     */

    private String orderId;
    private String orderName;
    private int associationId;
    private int objId;
    private int orderType;
    private int orderStatus;
    private String applyTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getAssociationId() {
        return associationId;
    }

    public void setAssociationId(int associationId) {
        this.associationId = associationId;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }



}
