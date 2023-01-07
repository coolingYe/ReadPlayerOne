package com.zee.club.ui.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.data.DataRepository;
import com.zee.club.data.protocol.request.AppRankingReq;
import com.zee.club.data.protocol.request.ProDetailReq;
import com.zee.club.data.protocol.request.PublishReq;
import com.zee.club.data.protocol.request.UpgradeReq;
import com.zee.club.data.protocol.response.AppRankingResp;
import com.zee.club.data.protocol.response.ProDetailResp;
import com.zee.club.data.protocol.response.PublishResp;
import com.zee.club.data.protocol.response.UpgradeResp;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends BaseViewModel {

    private final DataRepository dataRepository;

    public MutableLiveData<LoadState> mldDetailLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldPublishLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldUpgradeLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldAppRankingLoadState = new MutableLiveData<>();
    public ProDetailResp proDetailResp;
    public PublishResp publishResp;
    public UpgradeResp upgradeResp;
    public AppRankingResp appRankingResp;

    public DetailViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void reqDetailInfo(String skuId) {
        mldDetailLoadState.setValue(LoadState.Loading);
        dataRepository.getProDetailInfo(new ProDetailReq(skuId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<ProDetailResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ProDetailResp> response) {
                        proDetailResp = response.data;
                        mldDetailLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldDetailLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqPublishVersionInfo(PublishReq publishReq) {
        mldPublishLoadState.setValue(LoadState.Loading);
        dataRepository.getPublishedVersionInfo(publishReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<PublishResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<PublishResp> response) {
                        publishResp = response.data;
                        mldPublishLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldPublishLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqUpgradeVersionInfo(UpgradeReq upgradeReq) {
        mldUpgradeLoadState.setValue(LoadState.Loading);
        dataRepository.getUpgradeVersionInfo(upgradeReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<UpgradeResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<UpgradeResp> response) {
                        upgradeResp = response.data;
                        if(upgradeResp != null){
                            if(upgradeResp.getVersionId() == null || upgradeResp.getVersionId().isEmpty()){
                                upgradeResp = null;
                            }
                        }
                        mldUpgradeLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldUpgradeLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqAppRanking(AppRankingReq appRankingReq) {
        mldAppRankingLoadState.setValue(LoadState.Loading);
        dataRepository.reqAppRanking(appRankingReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<AppRankingResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<AppRankingResp> response) {
                        appRankingResp = response.data;
                        mldAppRankingLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldAppRankingLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
