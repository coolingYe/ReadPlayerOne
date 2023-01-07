package com.zee.club.home.data.protocol.response;

import java.io.Serializable;
import java.util.List;

public class RankingPeopleResp implements Serializable {
    private List<RankingPeople> rankingList;
    private SelfRanking selfRanking;

    public RankingPeopleResp(List<RankingPeople> rankingList, SelfRanking selfRanking) {
        this.rankingList = rankingList;
        this.selfRanking = selfRanking;
    }

    public List<RankingPeople> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<RankingPeople> rankingList) {
        this.rankingList = rankingList;
    }

    public SelfRanking getSelfRanking() {
        return selfRanking;
    }

    public void setSelfRanking(SelfRanking selfRanking) {
        this.selfRanking = selfRanking;
    }

    public static class RankingPeople implements Serializable {
        private int energyScore;
        private String enterpriseName;
        private String pic;
        private int rank;
        private String userId;
        private String userName;

        public int getEnergyScore() {
            return energyScore;
        }

        public void setEnergyScore(int energyScore) {
            this.energyScore = energyScore;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class SelfRanking implements Serializable {
        private int energyScore;
        private String enterpriseName;
        private String pic;
        private int rank;
        private String userId;
        private String userName;

        public int getEnergyScore() {
            return energyScore;
        }

        public void setEnergyScore(int energyScore) {
            this.energyScore = energyScore;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}

