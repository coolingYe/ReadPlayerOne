package com.zee.club.home.data.protocol.response;

import java.io.Serializable;
import java.util.List;

public class RankingCompanyResp implements Serializable {
    private List<RankingCompany> rankingList;
    private List<SelfRanking> selfRanking;

    public RankingCompanyResp(List<RankingCompany> rankingList, List<SelfRanking> selfRanking) {
        this.rankingList = rankingList;
        this.selfRanking = selfRanking;
    }

    public List<RankingCompany> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<RankingCompany> rankingList) {
        this.rankingList = rankingList;
    }

    public List<SelfRanking> getSelfRanking() {
        return selfRanking;
    }

    public void setSelfRanking(List<SelfRanking> selfRanking) {
        this.selfRanking = selfRanking;
    }

    public static class RankingCompany implements Serializable {
        private int energyScore;
        private int enterpriseId;
        private String enterpriseName;
        private int rank;

        public int getEnergyScore() {
            return energyScore;
        }

        public void setEnergyScore(int energyScore) {
            this.energyScore = energyScore;
        }

        public int getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(int enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }

    public static class SelfRanking implements Serializable {
        private int energyScore;
        private int enterpriseId;
        private String enterpriseName;
        private int rank;

        public int getEnergyScore() {
            return energyScore;
        }

        public void setEnergyScore(int energyScore) {
            this.energyScore = energyScore;
        }

        public int getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(int enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}

