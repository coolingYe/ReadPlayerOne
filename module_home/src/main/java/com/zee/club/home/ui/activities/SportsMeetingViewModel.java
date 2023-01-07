package com.zee.club.home.ui.activities;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.ActivityAddReq;
import com.zee.club.home.data.protocol.request.ActivityRankingReq;
import com.zee.club.home.data.protocol.request.AppInfoSoftwareCodeReq;
import com.zee.club.home.data.protocol.request.AppListReq;
import com.zee.club.home.data.protocol.response.ActivityRankingResp;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SportsMeetingViewModel extends BaseViewModel {

    private final DataRepository dataRepository;
    public MutableLiveData<LoadState> mldSportsAppInfoLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldActivityRankingLoadState = new MutableLiveData<>();
    public ActivityRankingResp activityRankingResp;
    public List<AppInfoResp> sportsAppInfoList;


    public SportsMeetingViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void reqAppInfoListBySoftwareCodes(AppInfoSoftwareCodeReq appInfoSoftwareCodeReq) {
        mldSportsAppInfoLoadState.setValue(LoadState.Loading);
        dataRepository.reqAppInfoListBySoftwareCodes(appInfoSoftwareCodeReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<List<AppInfoResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<AppInfoResp>> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            sportsAppInfoList = resp.data;
                            mldSportsAppInfoLoadState.setValue(LoadState.Success);
                        }else{
                            mldSportsAppInfoLoadState.setValue(LoadState.Failed);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldSportsAppInfoLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqActivityRanking(ActivityRankingReq activityRankingReq) {
        mldActivityRankingLoadState.setValue(LoadState.Loading);
        dataRepository.reqActivityRanking(activityRankingReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<ActivityRankingResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ActivityRankingResp> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            activityRankingResp = resp.data;
                            mldActivityRankingLoadState.setValue(LoadState.Success);
                        }else{
                            mldActivityRankingLoadState.setValue(LoadState.Failed);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldActivityRankingLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}