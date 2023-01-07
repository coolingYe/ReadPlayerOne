package com.zee.club.data;


import com.zee.club.data.protocol.request.AppRankingReq;
import com.zee.club.data.protocol.request.ProDetailReq;
import com.zee.club.data.protocol.request.PublishReq;
import com.zee.club.data.protocol.request.UpgradeReq;
import com.zee.club.data.protocol.response.AppRankingResp;
import com.zee.club.data.protocol.response.ProDetailResp;
import com.zee.club.data.protocol.response.PublishResp;
import com.zee.club.data.protocol.response.UpgradeResp;
import com.zee.club.data.source.http.service.ApiService;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.utils.RetrofitClient;
import io.reactivex.Observable;
import retrofit2.http.Body;

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

    public Observable<BaseResp<ProDetailResp>> getProDetailInfo(@Body ProDetailReq proDetailReq){
        return apiService.getProDetailInfo(proDetailReq);
    }

    public Observable<BaseResp<PublishResp>> getPublishedVersionInfo(@Body PublishReq publishReq){
        return apiService.getPublishedVersionInfo(publishReq);
    }

    public Observable<BaseResp<UpgradeResp>> getUpgradeVersionInfo(@Body UpgradeReq upgradeReq){
        return apiService.getUpgradeVersionInfo(upgradeReq);
    }

    @Override
    public Observable<BaseResp<AppRankingResp>> reqAppRanking(AppRankingReq appRankingReq) {
        return apiService.reqAppRanking(appRankingReq);
    }

}
