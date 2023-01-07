package com.zee.club.home.ui.activities;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.AppListReq;
import com.zee.club.home.data.protocol.response.ActivityInfoResp;
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

public class ClubActivitiesViewModel extends BaseViewModel {

    private final DataRepository dataRepository;
    public MutableLiveData<LoadState> mldInitDataLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldAppListLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldActivityInfoLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldActivityInfoCheckLoadState = new MutableLiveData<>();
    private final AtomicInteger pendingPrepareCount = new AtomicInteger();
    public List<AppInfoResp> skillsAppInfoList;
    public ActivityInfoResp activityInfoResp;

    public ClubActivitiesViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void initNetData(){
        mldInitDataLoadState.setValue(LoadState.Loading);
        pendingPrepareCount.set(2);
        reqAppList(ProdConstants.ActivitiesModule.SPECIAL_SKILL);
        reqActivityInfo();
    }

    private void decrementCountAndCheck(){
        int newPendingCount = pendingPrepareCount.decrementAndGet();
        if(newPendingCount <= 0){
            if(LoadState.Success == mldAppListLoadState.getValue() && LoadState.Success == mldActivityInfoLoadState.getValue()){
                mldInitDataLoadState.setValue(LoadState.Success);
            }else{
                mldInitDataLoadState.setValue(LoadState.Failed);
            }
        }
    }

    public void reqAppList(String moduleCode) {
        mldAppListLoadState.setValue(LoadState.Loading);
        dataRepository.getAppList(new AppListReq(moduleCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<List<AppInfoResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<AppInfoResp>> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            skillsAppInfoList = resp.data;
                            mldAppListLoadState.setValue(LoadState.Success);
                        }else{
                            mldAppListLoadState.setValue(LoadState.Failed);
                        }
                        decrementCountAndCheck();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldAppListLoadState.setValue(LoadState.Failed);
                        decrementCountAndCheck();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqActivityInfo() {
        mldActivityInfoLoadState.setValue(LoadState.Loading);
        dataRepository.reqActivityInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<ActivityInfoResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ActivityInfoResp> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            if(resp.data.getActivityAppJson() != null && resp.data.getActivityAppJson().size() > 0){
                                activityInfoResp = resp.data;
                            }else{
                                activityInfoResp = null;
                            }
                            mldActivityInfoLoadState.setValue(LoadState.Success);
                        }else{
                            mldActivityInfoLoadState.setValue(LoadState.Failed);
                        }
                        decrementCountAndCheck();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldActivityInfoLoadState.setValue(LoadState.Failed);
                        decrementCountAndCheck();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void checkActivityInfo() {
        mldActivityInfoCheckLoadState.setValue(LoadState.Loading);
        dataRepository.reqActivityInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<ActivityInfoResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ActivityInfoResp> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            if(resp.data.getActivityAppJson() != null && resp.data.getActivityAppJson().size() > 0){
                                activityInfoResp = resp.data;
                            }else{
                                activityInfoResp = null;
                            }
                            mldActivityInfoCheckLoadState.setValue(LoadState.Success);
                        }else{
                            mldActivityInfoCheckLoadState.setValue(LoadState.Failed);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldActivityInfoCheckLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}