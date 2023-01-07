package com.zee.club.home.data.protocol.response;

import java.util.List;

public class ActivityRankingResp {
    private List<RankingInfo> rankingTopN;
    private RankingInfo self;

    public List<RankingInfo> getRankingTopN() {
        return rankingTopN;
    }

    public void setRankingTopN(List<RankingInfo> rankingTopN) {
        this.rankingTopN = rankingTopN;
    }

    public RankingInfo getSelf() {
        return self;
    }

    public void setSelf(RankingInfo self) {
        this.self = self;
    }

    public static class RankingInfo{
        public String pic;
        public int score;
        public String userName;
        public int userRank;
    }
}
