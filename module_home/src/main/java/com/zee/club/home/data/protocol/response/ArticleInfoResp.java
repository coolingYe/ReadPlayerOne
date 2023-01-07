package com.zee.club.home.data.protocol.response;

import java.io.Serializable;

public class ArticleInfoResp implements Serializable {
    @com.alibaba.fastjson.annotation.JSONField(name = "articleId")
    public String articleId;
    @com.alibaba.fastjson.annotation.JSONField(name = "associationId")
    public Integer associationId;
    @com.alibaba.fastjson.annotation.JSONField(name = "moduleCode")
    public String moduleCode;
    @com.alibaba.fastjson.annotation.JSONField(name = "articleType")
    public Integer articleType;
    @com.alibaba.fastjson.annotation.JSONField(name = "publishTime")
    public String publishTime;
    @com.alibaba.fastjson.annotation.JSONField(name = "publisherId")
    public String publisherId;
    @com.alibaba.fastjson.annotation.JSONField(name = "author")
    public String author;
    @com.alibaba.fastjson.annotation.JSONField(name = "priority")
    public Integer priority;
    @com.alibaba.fastjson.annotation.JSONField(name = "title")
    public String title;
    @com.alibaba.fastjson.annotation.JSONField(name = "summary")
    public String summary;
    @com.alibaba.fastjson.annotation.JSONField(name = "content")
    public String content;
    @com.alibaba.fastjson.annotation.JSONField(name = "covers")
    public String covers;
    @com.alibaba.fastjson.annotation.JSONField(name = "articleOrder")
    public Integer articleOrder;
    @com.alibaba.fastjson.annotation.JSONField(name = "createTime")
    public String createTime;
    @com.alibaba.fastjson.annotation.JSONField(name = "updateTime")
    public String updateTime;
    @com.alibaba.fastjson.annotation.JSONField(name = "favoriteNum")
    public int favoriteNum;
    @com.alibaba.fastjson.annotation.JSONField(name = "praiseNum")
    public int praiseNum;

    @Override
    public String toString() {
        return "ArticleInfoResp{" +
                "publishTime='" + publishTime + '\'' +
                ", title='" + title + '\'' +
                ", covers='" + covers + '\'' +
                '}';
    }

    public String getPublishDate(){
        return publishTime.substring(0, 10);
    }
}
