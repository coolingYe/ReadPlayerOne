package com.zee.club.data.protocol.request;

public class AppRankingReq {
    private String softwareCode;
    private int topN;

    public AppRankingReq(String softwareCode, int topN) {
        this.softwareCode = softwareCode;
        this.topN = topN;
    }

    public String getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(String softwareCode) {
        this.softwareCode = softwareCode;
    }

    public int getTopN() {
        return topN;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }
}
