package com.zee.club.data.protocol.request;

public class MainCategoryReq {
    String categoryType;
    String parentId;

    public MainCategoryReq(String categoryType, String parentId) {
        this.categoryType = categoryType;
        this.parentId = parentId;
    }
}
