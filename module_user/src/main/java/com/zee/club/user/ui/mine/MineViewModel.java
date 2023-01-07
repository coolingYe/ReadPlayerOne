package com.zee.club.user.ui.mine;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.ImageCaptchaReq;
import com.zee.club.user.data.protocol.response.ImageCaptchaResp;
import com.zee.club.user.data.protocol.response.PersonEnergyResp;
import com.zee.club.user.data.protocol.response.UserInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;
import com.zeewain.base.utils.SPUtils;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MineViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mldImageCaptchaState = new MutableLiveData<>();
    // 错误信息
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();
    // 用户信息
    public MutableLiveData<UserInfoResp> mUserInfoResp = new MutableLiveData<>();

    public MutableLiveData<PersonEnergyResp> mPersonEnergyResp = new MutableLiveData<>();


    public MineViewModel(UserRepository userRepository) {
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

    public void reqGetPersonEnergy() {
        mUserRepository.getEnergyGeneral(SPUtils.getInstance().getInt(SharePrefer.associationId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<PersonEnergyResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<PersonEnergyResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            mPersonEnergyResp.setValue(resp.data);
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

}