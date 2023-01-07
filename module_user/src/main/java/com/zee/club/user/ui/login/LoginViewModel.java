package com.zee.club.user.ui.login;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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

public class LoginViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mldImageCaptchaState = new MutableLiveData<>();
    // 错误提示
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();
    // 验证码
    public MutableLiveData<LoadState> mldPhoneCaptchaState = new MutableLiveData<>();
    public String capUuid = "";
    // 登录信息
    public MutableLiveData<PhoneLoginResp> mPhoneLoginResp = new MutableLiveData<>();

    public MutableLiveData<String> captchaString = new MutableLiveData<>();

    // 社团信息
    public MutableLiveData<List<AssociationResp>> mAssociationResp = new MutableLiveData<>();

    public LoginViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void reqImageCaptcha() {
        mldImageCaptchaState.setValue(LoadState.Loading);
        mUserRepository.imageCaptchaReq(new ImageCaptchaReq("0"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<ImageCaptchaResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ImageCaptchaResp> resp) {
                        mldImageCaptchaState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldImageCaptchaState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void reqPhoneCaptcha(String phone, int type, String randstr, String ticket) {
        mldPhoneCaptchaState.setValue(LoadState.Loading);
        mUserRepository.phoneCaptchaReq(new PhoneCaptchaReq(phone, type, randstr, ticket))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<PhoneCaptchaResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<PhoneCaptchaResp> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mldPhoneCaptchaState.setValue(LoadState.Success);
                            capUuid = resp.data.getUuid();
                        } else {
                            mldPhoneCaptchaState.setValue(LoadState.Failed);
                            capUuid = "";
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldPhoneCaptchaState.setValue(LoadState.Failed);
                        capUuid = "";
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqPhoneLogin(String code, String loginName, String loginType, String uuid) {
        PhoneLoginReq req = new PhoneLoginReq();
        req.setLoginName(loginName);
        req.setCode(code);
        req.setType(loginType);
        req.setUuid(uuid);
        mUserRepository.phoneLoginReq(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<PhoneLoginResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<PhoneLoginResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            mPhoneLoginResp.setValue(resp.data);
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

    public void reqAccountLogin(String loginName, String loginType, String password,String randStr,String ticket ) {
        PhoneLoginReq req = new PhoneLoginReq();
        req.setLoginName(loginName);
        req.setUserPwd(password);
        req.setType(loginType);
        req.setRandstr(randStr);
        req.setTicket(ticket);
        req.setCode("111");
        req.setUuid("111");
        mUserRepository.phoneLoginReq(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<PhoneLoginResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<PhoneLoginResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            mPhoneLoginResp.setValue(resp.data);
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

    public void reqGetAssociationList() {
        mUserRepository.getSelfList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<List<AssociationResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<AssociationResp>> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS)
                            mAssociationResp.setValue(resp.data);
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