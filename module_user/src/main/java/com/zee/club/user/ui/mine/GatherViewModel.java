package com.zee.club.user.ui.mine;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.PageListReq;
import com.zee.club.user.data.protocol.response.DataInfoListResp;
import com.zee.club.user.data.protocol.response.GatherInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GatherViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;

    public MutableLiveData<LoadState> mBrowseLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mCollectLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mThumbsState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mLoadState = new MutableLiveData<>();
    public MutableLiveData<String> mErrStr = new MutableLiveData<>();
    public HashMap<String, List<GatherInfoResp>> gatherInfoListMap = new HashMap<>();


    public GatherViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public void reqListData(String type) {
        if (type.equals(UserConstants.Gather_Type_Browse)) {
            mUserRepository.getBrowsePage(new PageListReq(2, 0, 10)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(this)
                    .subscribe(new DisposableObserver<BaseResp<DataInfoListResp<GatherInfoResp>>>() {
                        @Override
                        public void onNext(@NonNull BaseResp<DataInfoListResp<GatherInfoResp>> resp) {
                            if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                                gatherInfoListMap.put(type, resp.data.getRecords());
                                mBrowseLoadState.setValue(LoadState.Success);
                            } else {
                                mBrowseLoadState.setValue(LoadState.Success);
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

        if (type.equals(UserConstants.Gather_Type_Collect)) {
            mUserRepository.getFavoritesPage(new PageListReq(2, 0, 10)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(this)
                    .subscribe(new DisposableObserver<BaseResp<DataInfoListResp<GatherInfoResp>>>() {
                        @Override
                        public void onNext(@NonNull BaseResp<DataInfoListResp<GatherInfoResp>> resp) {
                            if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                                gatherInfoListMap.put(type, resp.data.getRecords());
                                mCollectLoadState.setValue(LoadState.Success);
                            } else {
                                mCollectLoadState.setValue(LoadState.Failed);
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
        if (type.equals(UserConstants.Gather_Type_Thumbs)) {
            Log.e("1111","Gather_Type_Thumbs");
            mUserRepository.getPraisePage(new PageListReq(2, 0, 10)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(this)
                    .subscribe(new DisposableObserver<BaseResp<DataInfoListResp<GatherInfoResp>>>() {
                        @Override
                        public void onNext(@NonNull BaseResp<DataInfoListResp<GatherInfoResp>> resp) {

                            Log.e("onNext",type);


                            if (resp.code == BaseConstants.API_HANDLE_SUCCESS) {
                                gatherInfoListMap.put(type, resp.data.getRecords());
                                mThumbsState.setValue(LoadState.Success);
                            } else {
                                mThumbsState.setValue(LoadState.Failed);
                            }
                        }
                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e("onError",e.toString());
                            mErrStr.setValue("请检查当前网络环境");
                        }

                        @Override
                        public void onComplete() {
                            Log.e("onComplete",type);
                        }
                    });
        }
    }


}