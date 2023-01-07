package com.zee.club.home.ui.about;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.AppListReq;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.DataLoadState;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ClassicAppListViewModel extends BaseViewModel {

    private final DataRepository dataRepository;
    public MutableLiveData<DataLoadState<String>> mldAppListLoadState = new MutableLiveData<>();
    private final HashMap<String, List<AppInfoResp>> moduleAppListMap = new HashMap<>();

    public ClassicAppListViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<AppInfoResp> getAppInfoListFromCache(String moduleCode){
        return moduleAppListMap.get(moduleCode);
    }

    public void reqAppList(final String moduleCode) {
        mldAppListLoadState.setValue(new DataLoadState<>(LoadState.Loading));
        dataRepository.getAppList(new AppListReq(moduleCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<List<AppInfoResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<AppInfoResp>> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            moduleAppListMap.put(moduleCode, resp.data);
                            mldAppListLoadState.setValue(new DataLoadState<>(LoadState.Success, moduleCode));
                        }else{
                            mldAppListLoadState.setValue(new DataLoadState<>(LoadState.Failed));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldAppListLoadState.setValue(new DataLoadState<>(LoadState.Failed));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}