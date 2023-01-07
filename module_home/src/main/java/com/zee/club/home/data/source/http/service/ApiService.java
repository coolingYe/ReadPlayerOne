package com.zee.club.home.data.source.http.service;


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
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST(BaseConstants.basePath + "/association/article/page/list")
    Observable<BaseResp<ArticleListResp>> getArticleList(@Body ArticleListReq articleListReq);

    @GET(BaseConstants.basePath + "/association/article/info")
    Observable<BaseResp<ArticleInfoResp>> getArticleInfo(@Query("articleId") String articleId);

    @POST(BaseConstants.basePath + "/association/app/list")
    Observable<BaseResp<List<AppInfoResp>>> getAppList(@Body AppListReq appListReq);

    @GET(BaseConstants.basePath + "/association/energy/ranking/enterprise")
    Observable<BaseResp<RankingCompanyResp>> getRankingEnterprise(@Query("topN") String topN, @Query("iShowSelf") Boolean isShowSelf);

    @GET(BaseConstants.basePath + "/association/energy/ranking/excellent/people")
    Observable<BaseResp<RankingPeopleResp>> getRankingPeople(@Query("topN") String topN, @Query("iShowSelf") Boolean isShowSelf);

    @POST(BaseConstants.basePath + "/association/user/energy/add")
    Observable<JsonObject> requestEnergyAdd(@Body EnergyAddReq energyAddReq);

    @GET(BaseConstants.basePath + "/usercentre/praise/information/info")
    Observable<BaseResp<Praise>> checkInformationPraised(@Query("objId") String objId);

    @GET(BaseConstants.basePath + "/usercentre/favorites/information/info")
    Observable<BaseResp<Favorites>> checkInformationFavorites(@Query("objId") String objId);

    @POST(BaseConstants.basePath + "/usercentre/praise/information/add")
    Observable<BaseResp<PraiseFavoriteResp.addResponseBody>> addInformationPraised(@Body PraiseFavoriteReq praiseFavoriteReq);

    @POST(BaseConstants.basePath + "/usercentre/favorites/information/add")
    Observable<BaseResp<PraiseFavoriteResp.addResponseBody>> addInformationFavorite(@Body PraiseFavoriteReq praiseFavoriteReq);

    @POST(BaseConstants.basePath + "/usercentre/praise/information/del")
    Observable<JsonObject> delInformationPraised(@Body PraiseFavoriteReq.delRequestBody objIdList);

    @POST(BaseConstants.basePath + "/usercentre/favorites/information/del")
    Observable<JsonObject> delInformationFavorite(@Body PraiseFavoriteReq.delRequestBody objIdList);

    @POST(BaseConstants.basePath + "/association/activity/add")
    Observable<BaseResp<String>> reqActivityAdd(@Body ActivityAddReq activityAddReq);

    @POST(BaseConstants.basePath + "/association/activity/into")
    Observable<BaseResp<ActivityInfoResp>> reqActivityInfo();

    @POST(BaseConstants.basePath + "/association/activity/ranking/activity/overall")
    Observable<BaseResp<ActivityRankingResp>> reqActivityRanking(@Body ActivityRankingReq activityRankingReq);

    @POST(BaseConstants.basePath + "/product/online/query/list")
    Observable<BaseResp<List<AppInfoResp>>> reqAppInfoListBySoftwareCodes(@Body AppInfoSoftwareCodeReq appInfoSoftwareCodeReq);
}
