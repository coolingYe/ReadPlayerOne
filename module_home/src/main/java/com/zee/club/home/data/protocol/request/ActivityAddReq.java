package com.zee.club.home.data.protocol.request;

import com.zee.club.home.data.model.ActivityAppMo;

import java.util.List;

public class ActivityAddReq {
    private String activityName;
    private int activityType;
    private String beginTime;
    private String endTime;
    private List<ActivityAppMo> activityAppJson;

    public ActivityAddReq(String activityName, int activityType, String beginTime, String endTime, List<ActivityAppMo> activityAppJson) {
        this.activityName = activityName;
        this.activityType = activityType;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.activityAppJson = activityAppJson;
    }

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

    public List<ActivityAppMo> getActivityAppJson() {
        return activityAppJson;
    }

    public void setActivityAppJson(List<ActivityAppMo> activityAppJson) {
        this.activityAppJson = activityAppJson;
    }
}
