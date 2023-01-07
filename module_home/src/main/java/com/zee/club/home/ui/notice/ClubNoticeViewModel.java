package com.zee.club.home.ui.notice;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.data.DataRepository;
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

public class ClubNoticeViewModel extends BaseViewModel {

    private final DataRepository dataRepository;
    public MutableLiveData<LoadState> mldInitDataLoadState = new MutableLiveData<>();
    private final HashMap<String, List<ArticleInfoResp>> articleInfoListMap = new HashMap<>();
    private final AtomicInteger pendingPrepareCount = new AtomicInteger();

    public ClubNoticeViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<ArticleInfoResp> getArticleInfoListFromCache(String moduleCode){
        return articleInfoListMap.get(moduleCode);
    }

    public void initData(){
        mldInitDataLoadState.setValue(LoadState.Loading);
        articleInfoListMap.clear();
        pendingPrepareCount.set(4);
        reqArticleList(ProdConstants.ModuleCode.ASSOCIATION_MESSAGE_TOP);
        reqArticleList(ProdConstants.ModuleCode.INDUSTRY_ASSOCIATION);
        reqArticleList(ProdConstants.ModuleCode.ASSOCIATION_INFORM);
        reqArticleList(ProdConstants.ModuleCode.BID_INFORMATION);
    }

    private void decrementCountAndCheck(){
        int newPendingCount = pendingPrepareCount.decrementAndGet();
        if(newPendingCount <= 0){
            if(articleInfoListMap.size() == 4){
                mldInitDataLoadState.setValue(LoadState.Success);
            }else{
                mldInitDataLoadState.setValue(LoadState.Failed);
            }
        }
    }

    public void reqArticleList(final String moduleCode) {
        dataRepository.getArticleList(new ArticleListReq(moduleCode, 5))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<ArticleListResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ArticleListResp> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            if(ProdConstants.ModuleCode.BID_INFORMATION.equals(moduleCode)) {
                                resp.data.records.add(0, new ArticleInfoResp());//add an item;
                                articleInfoListMap.put(moduleCode, resp.data.records);
                            }else{
                                articleInfoListMap.put(moduleCode, resp.data.records);
                            }
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