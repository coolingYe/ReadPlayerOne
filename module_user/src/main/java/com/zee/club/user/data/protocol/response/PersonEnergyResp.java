package com.zee.club.user.data.protocol.response;

public class PersonEnergyResp {

    /**
     * monthScore : 0
     * todayScore : 0
     * weekScore : 0
     */

    private int monthScore;
    private int todayScore;
    private int weekScore;

    public int getMonthScore() {
        return monthScore;
    }

    public void setMonthScore(int monthScore) {
        this.monthScore = monthScore;
    }

    public int getTodayScore() {
        return todayScore;
    }

    public void setTodayScore(int todayScore) {
        this.todayScore = todayScore;
    }

    public int getWeekScore() {
        return weekScore;
    }

    public void setWeekScore(int weekScore) {
        this.weekScore = weekScore;
    }

    @Override
    public String toString() {
        return "PersonEnergyResp{" +
                "monthScore=" + monthScore +
                ", todayScore=" + todayScore +
                ", weekScore=" + weekScore +
                '}';
    }
}
