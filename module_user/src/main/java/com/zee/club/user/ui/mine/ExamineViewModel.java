package com.zee.club.user.ui.mine;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.ExamineReq;
import com.zee.club.user.data.protocol.request.ImageCaptchaReq;
import com.zee.club.user.data.protocol.response.ImageCaptchaResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ExamineViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mLoadState = new MutableLiveData<>();
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();

    public ExamineViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void reqExamineApprove(int associationId, String orderId, int orderStatus, int userType) {
        mLoadState.setValue(LoadState.Loading);
        mUserRepository.examineApproveReq(new ExamineReq(associationId, orderId, orderStatus, userType))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<String>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<String> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mLoadState.setValue(LoadState.Success);
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