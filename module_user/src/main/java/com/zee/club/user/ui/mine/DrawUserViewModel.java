package com.zee.club.user.ui.mine;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.response.AssociationResp;
import com.zee.club.user.data.protocol.response.PersonEnergyResp;
import com.zee.club.user.data.protocol.response.UserInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;
import com.zeewain.base.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DrawUserViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mldImageCaptchaState = new MutableLiveData<>();
    // 错误信息
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();
    // 用户信息
    public MutableLiveData<UserInfoResp> mUserInfoResp = new MutableLiveData<>();

    public MutableLiveData<PersonEnergyResp> mPersonEnergyResp = new MutableLiveData<>();

    // 社团信息
    public MutableLiveData<LoadState> mAssociationLoadState = new MutableLiveData<>();

    public List<AssociationResp> mAssociationRespList = new ArrayList<>();

    public DrawUserViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }


    public void reqGetUserInfo() {
        mUserRepository.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<UserInfoResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<UserInfoResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            mUserInfoResp.setValue(resp.data);
                        } else {
                            mErrStr.setValue(resp.message);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mErrStr.setValue("请重新检查网路");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqGetAssociationList() {
        mUserRepository.getSelfList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<List<AssociationResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<AssociationResp>> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mAssociationRespList.addAll(resp.data);
                            mAssociationLoadState.setValue(LoadState.Success);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mErrStr.setValue("请检查当前网络环境");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}