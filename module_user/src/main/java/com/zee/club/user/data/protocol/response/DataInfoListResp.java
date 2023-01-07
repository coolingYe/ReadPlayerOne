package com.zee.club.user.data.protocol.response;

import java.util.List;

public class DataInfoListResp<T> {

    private int recordStartNo;
    private int returnNum;
    private int total;
    private List<T> records;

    public int getRecordStartNo() {
        return recordStartNo;
    }

    public void setRecordStartNo(int recordStartNo) {
        this.recordStartNo = recordStartNo;
    }

    public int getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(int returnNum) {
        this.returnNum = returnNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public static class approvalResp {

        /**
         * applyTime :
         * applyUser : 0
         * approveTime :
         * approveUser : 0
         * associationId : 0
         * objId : 0
         * orderId : 0
         * orderName :
         * orderStatus : 0
         * orderType : 0
         */

        private String applyTime;
        private int applyUser;
        private String approveTime;
        private int approveUser;
        private int associationId;
        private int objId;
        private int orderId;
        private String orderName;
        private int orderStatus;
        private int orderType;

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public int getApplyUser() {
            return applyUser;
        }

        public void setApplyUser(int applyUser) {
            this.applyUser = applyUser;
        }

        public String getApproveTime() {
            return approveTime;
        }

        public void setApproveTime(String approveTime) {
            this.approveTime = approveTime;
        }

        public int getApproveUser() {
            return approveUser;
        }

        public void setApproveUser(int approveUser) {
            this.approveUser = approveUser;
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

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }
    }
}
