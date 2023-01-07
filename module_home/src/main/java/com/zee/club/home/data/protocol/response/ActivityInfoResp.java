package com.zee.club.home.data.protocol.response;

import com.zee.club.home.data.model.ActivityAppMo;

import java.io.Serializable;
import java.util.List;

public class ActivityInfoResp implements Serializable {

    private String activityName;
    private int activityType;
    private int associationId;
    private int activityId;
    private String beginTime;
    private String endTime;
    private int status;

    private List<ActivityAppMo> activityAppJson;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public int getAssociationId() {
        return associationId;
    }

    public void setAssociationId(int associationId) {
        this.associationId = associationId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ActivityAppMo> getActivityAppJson() {
        return activityAppJson;
    }

    public void setActivityAppJson(List<ActivityAppMo> activityAppJson) {
        this.activityAppJson = activityAppJson;
    }
}
