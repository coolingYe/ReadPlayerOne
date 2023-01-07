package com.zee.club.user.data.protocol.request;

public class ExamineReq {

    /**
     * associationId : 0
     * orderId : 0
     * orderStatus : 0
     * userType : 0
     */

    private int associationId;
    private String orderId;
    private int orderStatus;
    private int userType;

    public int getAssociationId() {
        return associationId;
    }

    public void setAssociationId(int associationId) {
        this.associationId = associationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public ExamineReq(int associationId, String orderId, int orderStatus, int userType) {
        this.associationId = associationId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.userType = userType;
    }
}
