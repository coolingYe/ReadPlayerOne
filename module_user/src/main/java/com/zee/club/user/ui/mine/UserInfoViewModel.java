package com.zee.club.user.ui.mine;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.ImageCaptchaReq;
import com.zee.club.user.data.protocol.response.ImageCaptchaResp;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserInfoViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mldImageCaptchaState = new MutableLiveData<>();
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();

    public UserInfoViewModel(UserRepository userRepository) {
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
                        mErrStr.setValue("请检查当前网络环境");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}