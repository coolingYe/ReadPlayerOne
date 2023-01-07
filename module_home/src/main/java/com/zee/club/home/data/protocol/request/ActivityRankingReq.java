package com.zee.club.home.data.protocol.request;

public class ActivityRankingReq {
    private int activityId;
    private int topN;

    public ActivityRankingReq(int activityId, int topN) {
        this.activityId = activityId;
        this.topN = topN;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getTopN() {
        return topN;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }
}
