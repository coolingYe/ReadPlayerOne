package com.zee.club.user.ui.login;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.ForgetPasswordReq;
import com.zee.club.user.data.protocol.request.ImageCaptchaReq;
import com.zee.club.user.data.protocol.request.PhoneCaptchaReq;
import com.zee.club.user.data.protocol.request.PhoneLoginReq;
import com.zee.club.user.data.protocol.response.AssociationResp;
import com.zee.club.user.data.protocol.response.ImageCaptchaResp;
import com.zee.club.user.data.protocol.response.PhoneCaptchaResp;
import com.zee.club.user.data.protocol.response.PhoneLoginResp;
import com.zee.club.user.data.protocol.response.UserInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    // 加载状态
    public MutableLiveData<LoadState> mLoadState = new MutableLiveData<>();
    // 错误提示
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();
    // 验证码
    public MutableLiveData<LoadState> mldPhoneCaptchaState = new MutableLiveData<>();
    public String capUUID = "";

    public ForgetPasswordViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void reqForgetPassword(String account, String phone, String code, String password,String capUUID) {
        mUserRepository.forgetPassword(new ForgetPasswordReq(code, password, password, phone,capUUID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<String>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<String> resp) {

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
        mUserRepository.phoneCaptchaReq(new PhoneCaptchaReq(phone,type, "", ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<PhoneCaptchaResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<PhoneCaptchaResp> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mldPhoneCaptchaState.setValue(LoadState.Success);
                            capUUID = resp.data.getUuid();
                        } else  {
                            mErrStr.setValue(resp.message);
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