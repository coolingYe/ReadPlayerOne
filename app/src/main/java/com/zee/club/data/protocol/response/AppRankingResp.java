package com.zee.club.data.protocol.response;

import java.util.List;

public class AppRankingResp {
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
        public String enterpriseNames;
        public String pic;
        public String playTime;
        public int score;
        public String userName;
        public int userRank;

        public String getPlayDate(){
            if(playTime != null && playTime.length() > 10){
                return playTime.substring(0, 10);
            }
            return "";
        }
    }
}
