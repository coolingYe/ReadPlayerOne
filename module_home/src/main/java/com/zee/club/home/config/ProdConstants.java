package com.zee.club.home.config;


import java.util.HashMap;
import java.util.Map;

public class ProdConstants {
    public static final Boolean isMockData = false;

    public static class AboutModule {
        public static final String TYPE_1 = "sports-health";
        public static final String TYPE_2 = "lucky-universe";
        public static final String TYPE_3 = "science-technology-culture";
        public static final String TYPE_4 = "other";
    }

    public static class ActivitiesModule {
        public static final String SPECIAL_SKILL = "special-skill";
        public static final String SPORTS_MEETING = "sports-meeting";
    }

    public static class ModuleCode {
        public static final String ABOUT_ASSOCIATION_TOP = "about-association-top"; //关于社团顶部轮播图

        public static final String ASSOCIATION_MESSAGE_TOP = "association-message-top"; //社团通知顶部轮播图
        public static final String INDUSTRY_ASSOCIATION = "industry-association";  //行业社团
        public static final String ASSOCIATION_INFORM = "association-inform";  //本社团通知
        public static final String BID_INFORMATION = "bid-information";  //招投标信息


        public static final String INDUSTRY_INFORMATION_TOP = "industry-information-top";  //行业资讯顶部轮播图

        public static final String INDUSTRY_LATEST_INFORMATION = "industry-latest-information";  //行业最新资讯
        public static final String INDUSTRY_RELATED_INFORMATION = "industry-related-information";  //行业相关资讯

        public static final String MEMBER_MIEN_TOP = "member-mien-top";  //会员风采顶部轮播图
        public static final String WIN_BID_INFORMATION = "win-bid-information";  //中标喜庆
        public static final String COMPANY_THRILLING_INFORMATION = "company-thrilling-information";  //公司喜庆

        public static final String INDUSTRY_REGULATION_TOP = "industry-regulation-top";  //行业法规顶部轮播图
        public static final String INDUSTRY_REGULATION = "industry-regulation";  //行业法规
        public static final String INDUSTRY_COMMON_REGULATION = "industry-common-regulation";  //行业常用规范

        public static final String PUBLIC_BENEFIT_TOP = "public-benefit-top";  //社团公益顶部轮播图
        public static final String PUBLIC_BENEFIT_FOUNDATION = "public-benefit-foundation";  //基金会
        public static final String PUBLIC_BENEFIT_ACTIVITY = "public-benefit-activity";  //公益活动
    }

    public static class BiddingType {
        public static final int NOTICE = 1;
        public static final int PREVIEW = 2;
        public static final int FLOW = 3;
        public static final int MANAGE = 4;
        public static final int INFO = 5;
    }

    public static class ActivitiesStatus {
        //0：未开始或者结束 1：进行中
        public static final int NOT_STARTED = 1;
        public static final int IN_PROGRESS = 2;
        public static final int CLOSED = 3;
    }

    public static class EnergySourceType {
        public static final int PLAY = 1;
        public static final int ACTIVITY_PLAY = 2;
        public static final int SHARE = 3;
        public static final int INFO = 4;
    }

    public static final Map<String, Integer> InfoCategoryMap = new HashMap<String, Integer>() {
        {
            put("技术", 2001);
            put("产品", 2002);
            put("方案", 2003);
            put("活动", 2004);
            put("会议", 2005);
        }
    };

    public static final int PRD_PAGE_SIZE = 6;

}
