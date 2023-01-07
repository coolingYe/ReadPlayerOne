package com.zee.club.home.data.protocol.response;

import java.util.List;

public class ArticleListResp {

    @com.alibaba.fastjson.annotation.JSONField(name = "records")
    public List<ArticleInfoResp> records;
    @com.alibaba.fastjson.annotation.JSONField(name = "recordStartNo")
    public Integer recordStartNo;
    @com.alibaba.fastjson.annotation.JSONField(name = "returnNum")
    public Integer returnNum;
    @com.alibaba.fastjson.annotation.JSONField(name = "total")
    public Integer total;
}
