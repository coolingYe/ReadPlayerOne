package com.zee.club.data.source.http.service;


import com.zee.club.data.protocol.request.AppRankingReq;
import com.zee.club.data.protocol.request.ProDetailReq;
import com.zee.club.data.protocol.request.PublishReq;
import com.zee.club.data.protocol.request.UpgradeReq;
import com.zee.club.data.protocol.response.AppRankingResp;
import com.zee.club.data.protocol.response.ProDetailResp;
import com.zee.club.data.protocol.response.PublishResp;
import com.zee.club.data.protocol.response.UpgradeResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST(BaseConstants.basePath + "/product/online/detail")
    Observable<BaseResp<ProDetailResp>> getProDetailInfo(@Body ProDetailReq proDetailReq);

    @POST(BaseConstants.basePath + "/software/version/latest-published")
    Observable<BaseResp<PublishResp>> getPublishedVersionInfo(@Body PublishReq publishReq);

    @POST(BaseConstants.basePath + "/software/version/newer-published")
    Observable<BaseResp<UpgradeResp>> getUpgradeVersionInfo(@Body UpgradeReq upgradeReq);

    @POST(BaseConstants.basePath + "/association/app/user/ranking")
    Observable<BaseResp<AppRankingResp>> reqAppRanking(@Body AppRankingReq appRankingReq);
}
