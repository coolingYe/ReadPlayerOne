package com.zee.club.home.ui.activities;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.ActivityAddReq;
import com.zee.club.home.data.protocol.request.AppListReq;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ActivitiesPublishViewModel extends BaseViewModel {

    private final DataRepository dataRepository;
    public MutableLiveData<LoadState> mldSportsAppInfoLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldActivityAddLoadState = new MutableLiveData<>();
    public List<AppInfoResp> sportsAppInfoList;


    public ActivitiesPublishViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void reqAppList(String moduleCode) {
        mldSportsAppInfoLoadState.setValue(LoadState.Loading);
        dataRepository.getAppList(new AppListReq(moduleCode))
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

    public void reqActivityAdd(ActivityAddReq activityAddReq) {
        mldActivityAddLoadState.setValue(LoadState.Loading);
        dataRepository.reqActivityAdd(activityAddReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<String>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<String> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            mldActivityAddLoadState.setValue(LoadState.Success);
                        }else{
                            mldActivityAddLoadState.setValue(LoadState.Failed);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldActivityAddLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}