package com.zee.club.user.ui.mine;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.PageListReq;
import com.zee.club.user.data.protocol.response.ActiveResp;
import com.zee.club.user.data.protocol.response.DataInfoListResp;
import com.zee.club.user.data.protocol.response.ActivitiesResp;
import com.zee.club.user.data.protocol.response.NoticeResp;
import com.zee.club.user.data.protocol.response.UserAppInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InteractionViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mInteractState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mActiveState = new MutableLiveData<>();

    public ArrayList<UserAppInfoResp> userAppInfoRespList = new ArrayList<>();
    public ArrayList<ActivitiesResp> activitiesRespList = new ArrayList<>();

    public MutableLiveData<String> mErrStr = new MutableLiveData<>();

    public InteractionViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void reqGetInteractList() {
        mInteractState.setValue(LoadState.Loading);
        mUserRepository.getReqInteractionList( )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<List<UserAppInfoResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<UserAppInfoResp>> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
//                                userAppInfoRespList.addAll(resp.data.getRecords());
                                mInteractState.setValue(LoadState.Success);
                        } else {
                            mInteractState.setValue(LoadState.Failed);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mInteractState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqGetActivitiesList() {
        mActiveState.setValue(LoadState.Loading);
        mUserRepository.getActivitiesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<List<ActivitiesResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<ActivitiesResp>> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
//                                noticeRespList.addAll(resp.data.getRecords());
                                mActiveState.setValue(LoadState.Success);
                        } else {
                            mActiveState.setValue(LoadState.Failed);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mActiveState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}