package com.zee.club.user.data.protocol.request;

public class PageListReq {

    /**
     * count : true
     * objType : 0
     * pageNo : 0
     * pageSize : 0
     * recordStartNo : 0
     * sort :
     * sortOrder :
     */

    private boolean count;
    private int objType;
    private int pageNo;
    private int pageSize;
    private int recordStartNo;
    private String sort;
    private String sortOrder;

    public PageListReq(int objType, int pageNo, int pageSize) {
        this.objType = objType;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageListReq(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }


    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
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
