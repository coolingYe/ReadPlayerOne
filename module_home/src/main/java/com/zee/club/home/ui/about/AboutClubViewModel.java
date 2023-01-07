package com.zee.club.home.ui.about;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.AppListReq;
import com.zee.club.home.data.protocol.request.ArticleListReq;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.data.protocol.response.ArticleListResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AboutClubViewModel extends BaseViewModel {

    private final DataRepository dataRepository;
    public MutableLiveData<LoadState> mldArticleListLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldInitDataLoadState = new MutableLiveData<>();
    public List<ArticleInfoResp> articleList = new ArrayList<>();
    private final HashMap<String, List<AppInfoResp>> moduleAppListMap = new HashMap<>();
    private final AtomicInteger pendingPrepareCount = new AtomicInteger();

    public AboutClubViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<AppInfoResp> getAppInfoListFromCache(String moduleCode){
        return moduleAppListMap.get(moduleCode);
    }

    public void initData(){
        mldInitDataLoadState.setValue(LoadState.Loading);
        moduleAppListMap.clear();
        pendingPrepareCount.set(5);
        reqArticleList();
        reqAppList(ProdConstants.AboutModule.TYPE_1);
        reqAppList(ProdConstants.AboutModule.TYPE_2);
        reqAppList(ProdConstants.AboutModule.TYPE_3);
        reqAppList(ProdConstants.AboutModule.TYPE_4);
    }

    private void decrementCountAndCheck(){
        int newPendingCount = pendingPrepareCount.decrementAndGet();
        if(newPendingCount <= 0){
            if(moduleAppListMap.size() == 4 && LoadState.Success == mldArticleListLoadState.getValue()){
                mldInitDataLoadState.setValue(LoadState.Success);
            }else{
                mldInitDataLoadState.setValue(LoadState.Failed);
            }
        }
    }

    public void reqArticleList() {
        mldArticleListLoadState.setValue(LoadState.Loading);
        dataRepository.getArticleList(new ArticleListReq(ProdConstants.ModuleCode.ABOUT_ASSOCIATION_TOP, 5))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<ArticleListResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ArticleListResp> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            articleList = resp.data.records;
                            mldArticleListLoadState.setValue(LoadState.Success);
                        }else {
                            mldArticleListLoadState.setValue(LoadState.Failed);
                        }
                        decrementCountAndCheck();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldArticleListLoadState.setValue(LoadState.Failed);
                        decrementCountAndCheck();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reqAppList(String moduleCode) {
        dataRepository.getAppList(new AppListReq(moduleCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<List<AppInfoResp>>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<List<AppInfoResp>> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            moduleAppListMap.put(moduleCode, resp.data);
                        }
                        decrementCountAndCheck();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        decrementCountAndCheck();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}