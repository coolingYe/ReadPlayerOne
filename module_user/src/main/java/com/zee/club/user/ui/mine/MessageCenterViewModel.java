package com.zee.club.user.ui.mine;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.ImageCaptchaReq;
import com.zee.club.user.data.protocol.request.OrderReq;
import com.zee.club.user.data.protocol.request.PageListReq;
import com.zee.club.user.data.protocol.response.ActiveResp;
import com.zee.club.user.data.protocol.response.ApprovalResp;
import com.zee.club.user.data.protocol.response.DataInfoListResp;
import com.zee.club.user.data.protocol.response.GatherInfoResp;
import com.zee.club.user.data.protocol.response.ImageCaptchaResp;
import com.zee.club.user.data.protocol.response.NoticeResp;
import com.zee.club.user.data.protocol.response.UserAppInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;
import com.zeewain.base.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MessageCenterViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mNoticeState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mActiveState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mApprovalState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mDownLoadState = new MutableLiveData<>();
    public ArrayList<NoticeResp> noticeRespList = new ArrayList<>();
    public ArrayList<ActiveResp> activeRespList = new ArrayList<>();
    public ArrayList<ApprovalResp> approvalRespList = new ArrayList<>();
    public ArrayList<UserAppInfoResp> userAppInfoRespList = new ArrayList<>();


    public MutableLiveData<String> mErrStr = new MutableLiveData<>();


    public MessageCenterViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void reqGetNoticeList() {
        mNoticeState.setValue(LoadState.Loading);
        mUserRepository.getNoticeList(new PageListReq(0, 10))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<DataInfoListResp<NoticeResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<DataInfoListResp<NoticeResp>> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            if (resp.data.getRecords() != null) {
                                noticeRespList.addAll(resp.data.getRecords());
                                mNoticeState.setValue(LoadState.Success);
                            }
                        } else {
                            mNoticeState.setValue(LoadState.Failed);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mNoticeState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqGetActiveList() {
        mActiveState.setValue(LoadState.Loading);
        mUserRepository.getActivePage(new PageListReq(0, 10))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<DataInfoListResp<ActiveResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<DataInfoListResp<ActiveResp>> resp) {
                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            if (resp.data.getRecords() != null) {
//                                noticeRespList.addAll(resp.data.getRecords());
                                mActiveState.setValue(LoadState.Success);
                            }
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

    public void reqGetApprovalList(String Statue) {
        mApprovalState.setValue(LoadState.Loading);
        OrderReq order = new OrderReq();
        order.setPageNo(0);
        order.setPageSize(10);
        order.setApproveStatus(Integer.parseInt(Statue));
        mUserRepository.getOrderPage(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<DataInfoListResp<ApprovalResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<DataInfoListResp<ApprovalResp>> resp) {

                        if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                            if (resp.data != null) {
                                approvalRespList.clear();
                                approvalRespList.addAll(resp.data.getRecords());
                                mApprovalState.setValue(LoadState.Success);
                            }
                        } else {
                            mApprovalState.setValue(LoadState.Failed);
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