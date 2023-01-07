package com.zee.club.user.ui.bind;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.BindCommunityReq;
import com.zee.club.user.data.protocol.request.EnterpriseReq;
import com.zee.club.user.data.protocol.request.ImageCaptchaReq;
import com.zee.club.user.data.protocol.request.JoinApplyReq;
import com.zee.club.user.data.protocol.request.MemberReq;
import com.zee.club.user.data.protocol.request.PhoneCaptchaReq;
import com.zee.club.user.data.protocol.response.EnterpriseResp;
import com.zee.club.user.data.protocol.response.ImageCaptchaResp;
import com.zee.club.user.data.protocol.response.MemberResp;
import com.zee.club.user.data.protocol.response.PhoneCaptchaResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BindCommViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mldImageCaptchaState = new MutableLiveData<>();
    public MutableLiveData<List<MemberResp>> mMember = new MutableLiveData<>();
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();
    public MutableLiveData<LoadState> mBindComm = new MutableLiveData<>();
    public MutableLiveData<LoadState> mEnterPriseState = new MutableLiveData<>();
    public List<EnterpriseResp> mEnterpriseList = new ArrayList<>();


    // 验证码
    public MutableLiveData<LoadState> mldPhoneCaptchaState = new MutableLiveData<>();

    public String capUUID = "";
    public int mEnterpriseId;


    public BindCommViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void reqImageCaptcha() {
        mldImageCaptchaState.setValue(LoadState.Loading);
        mUserRepository.imageCaptchaReq(new ImageCaptchaReq("0")).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(this).subscribe(new DisposableObserver<BaseResp<ImageCaptchaResp>>() {
            @Override
            public void onNext(@NonNull BaseResp<ImageCaptchaResp> resp) {
                mldImageCaptchaState.setValue(LoadState.Success);
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

    public void reqGetMemberList(String id) {
        mUserRepository.getMemberList(new MemberReq(id)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this).subscribe(new DisposableObserver<BaseResp<List<MemberResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<MemberResp>> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mMember.setValue(resp.data);
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


    public void reqBindCommunity(BindCommunityReq req) {
        mUserRepository.bindCommunity(req).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<String>>() {
            @Override
            public void onNext(@NonNull BaseResp<String> resp) {
                if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                    mBindComm.setValue(LoadState.Success);
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

    public void reqBindPerCommunity(JoinApplyReq req) {
        mUserRepository.bindPerCommunity(req).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<String>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<String> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mBindComm.setValue(LoadState.Success);
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

    public void getEnterpriseList(int associationId) {
        mUserRepository.getEnterpriseList(new EnterpriseReq(associationId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(this).subscribe(new DisposableObserver<BaseResp<List<EnterpriseResp>>>() {
            @Override
            public void onNext(@NonNull BaseResp<List<EnterpriseResp>> resp) {

                if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                    mEnterpriseList = resp.data;
                    mEnterPriseState.setValue(LoadState.Success);
                } else {
                    mEnterPriseState.setValue(LoadState.Failed);
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