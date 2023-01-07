package com.zee.club.user.data.protocol.request;

public class OrderReq {

    /**
     * approveStatus : 0
     * count : true
     * pageNo : 0
     * pageSize : 0
     * recordStartNo : 0
     * sort :
     * sortOrder :
     */

    private int approveStatus;
    private boolean count;
    private int pageNo;
    private int pageSize;
    private int recordStartNo;
    private String sort;
    private String sortOrder;


    public int getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(int approveStatus) {
        this.approveStatus = approveStatus;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordStartNo() {
        return recordStartNo;
    }

    public void setRecordStartNo(int recordStartNo) {
        this.recordStartNo = recordStartNo;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
