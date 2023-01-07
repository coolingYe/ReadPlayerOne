package com.zee.club.home.data;

import com.google.gson.JsonObject;
import com.zee.club.home.data.protocol.request.ActivityAddReq;
import com.zee.club.home.data.protocol.request.ActivityRankingReq;
import com.zee.club.home.data.protocol.request.AppInfoSoftwareCodeReq;
import com.zee.club.home.data.protocol.request.AppListReq;
import com.zee.club.home.data.protocol.request.ArticleListReq;
import com.zee.club.home.data.protocol.request.EnergyAddReq;
import com.zee.club.home.data.protocol.request.PraiseFavoriteReq;
import com.zee.club.home.data.protocol.response.ActivityInfoResp;
import com.zee.club.home.data.protocol.response.ActivityRankingResp;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.data.protocol.response.ArticleListResp;
import com.zee.club.home.data.protocol.response.Favorites;
import com.zee.club.home.data.protocol.response.Praise;
import com.zee.club.home.data.protocol.response.PraiseFavoriteResp;
import com.zee.club.home.data.protocol.response.RankingCompanyResp;
import com.zee.club.home.data.protocol.response.RankingPeopleResp;
import com.zee.club.home.data.source.http.service.ApiService;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.utils.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

public class DataRepository implements ApiService{

    private static volatile DataRepository instance;
    private final ApiService apiService;

    private DataRepository(ApiService apiService){
        this.apiService = apiService;
    }

    public static DataRepository getInstance(){
        if(instance == null){
            synchronized (DataRepository.class){
                if (instance == null){
                    instance = new DataRepository(RetrofitClient.getInstance().create(ApiService.class));
                }
            }
        }
        return instance;
    }

    @Override
    public Observable<BaseResp<ArticleListResp>> getArticleList(ArticleListReq articleListReq) {
        return apiService.getArticleList(articleListReq);
    }

    @Override
    public Observable<BaseResp<ArticleInfoResp>> getArticleInfo(String articleId) {
        return apiService.getArticleInfo(articleId);
    }

    @Override
    public Observable<BaseResp<List<AppInfoResp>>> getAppList(AppListReq appListReq) {
        return apiService.getAppList(appListReq);
    }

    @Override
    public Observable<BaseResp<RankingCompanyResp>> getRankingEnterprise(String topN, Boolean isShowSelf) {
        return apiService.getRankingEnterprise(topN, isShowSelf);
    }

    @Override
    public Observable<BaseResp<RankingPeopleResp>> getRankingPeople(String topN, Boolean isShowSelf) {
        return apiService.getRankingPeople(topN, isShowSelf);
    }

    @Override
    public Observable<JsonObject> requestEnergyAdd(EnergyAddReq energyAddReq) {
        return apiService.requestEnergyAdd(energyAddReq);
    }

    @Override
    public Observable<BaseResp<Praise>> checkInformationPraised(String objId) {
        return apiService.checkInformationPraised(objId);
    }

    @Override
    public Observable<BaseResp<Favorites>> checkInformationFavorites(String objId) {
        return apiService.checkInformationFavorites(objId);
    }

    @Override
    public Observable<BaseResp<PraiseFavoriteResp.addResponseBody>> addInformationPraised(PraiseFavoriteReq praiseFavoriteReq) {
        return apiService.addInformationPraised(praiseFavoriteReq);
    }

    @Override
    public Observable<BaseResp<PraiseFavoriteResp.addResponseBody>> addInformationFavorite(PraiseFavoriteReq praiseFavoriteReq) {
        return apiService.addInformationFavorite(praiseFavoriteReq);
    }

    @Override
    public Observable<JsonObject> delInformationPraised(PraiseFavoriteReq.delRequestBody objIdList) {
        return apiService.delInformationPraised(objIdList);
    }

    @Override
    public Observable<JsonObject> delInformationFavorite(PraiseFavoriteReq.delRequestBody objIdList) {
        return apiService.delInformationFavorite(objIdList);
    }

    @Override
    public Observable<BaseResp<String>> reqActivityAdd(ActivityAddReq activityAddReq) {
        return apiService.reqActivityAdd(activityAddReq);
    }

    @Override
    public Observable<BaseResp<ActivityInfoResp>> reqActivityInfo() {
        return apiService.reqActivityInfo();
    }

    @Override
    public Observable<BaseResp<ActivityRankingResp>> reqActivityRanking(ActivityRankingReq activityRankingReq) {
        return apiService.reqActivityRanking(activityRankingReq);
    }

    @Override
    public Observable<BaseResp<List<AppInfoResp>>> reqAppInfoListBySoftwareCodes(AppInfoSoftwareCodeReq appInfoSoftwareCodeReq) {
        return apiService.reqAppInfoListBySoftwareCodes(appInfoSoftwareCodeReq);
    }
}
