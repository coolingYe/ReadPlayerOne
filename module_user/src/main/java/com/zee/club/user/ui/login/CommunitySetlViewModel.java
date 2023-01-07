package com.zee.club.user.ui.login;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.AssoEnterApplyReq;
import com.zee.club.user.data.protocol.request.ForgetPasswordReq;
import com.zee.club.user.data.protocol.request.PhoneCaptchaReq;
import com.zee.club.user.data.protocol.response.PhoneCaptchaResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CommunitySetlViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    // 加载状态
    public MutableLiveData<LoadState> mApplyState = new MutableLiveData<>();
    // 错误提示
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();
    // 验证码
    public MutableLiveData<LoadState> mldPhoneCaptchaState = new MutableLiveData<>();

    public String capUUID = "";

    public CommunitySetlViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void reqEnterApply(String adminName, String adminTelephone, String applyUserName, String associationName, String code, int enterpriseScale, String uuid) {
        mUserRepository.reqEnterApply(new AssoEnterApplyReq(adminName, adminTelephone, applyUserName, associationName, code, enterpriseScale, uuid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<String>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<String> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mApplyState.setValue(LoadState.Success);
                        } else {
                            mErrStr.setValue(resp.message);
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

    public void reqPhoneCaptcha(String phone, int type) {
        mldPhoneCaptchaState.setValue(LoadState.Loading);
        mUserRepository.phoneCaptchaReq(new PhoneCaptchaReq(phone, type, "", ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<PhoneCaptchaResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<PhoneCaptchaResp> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mldPhoneCaptchaState.setValue(LoadState.Success);
                            capUUID = resp.data.getUuid();
                        } else {
                            mErrStr.setValue(resp.message);
                            capUUID = "";
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldPhoneCaptchaState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}