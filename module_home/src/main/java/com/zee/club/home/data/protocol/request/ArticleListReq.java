package com.zee.club.home.data.protocol.request;

public class ArticleListReq {
    private String moduleCode;
    private int pageSize;
    private Integer columnId;

    public ArticleListReq(String moduleCode, int pageSize, Integer columnId) {
        this.moduleCode = moduleCode;
        this.pageSize = pageSize;
        this.columnId = columnId;
    }

    public ArticleListReq(String moduleCode, int pageSize) {
        this.moduleCode = moduleCode;
        this.pageSize = pageSize;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }
}
