package com.zee.club.home.ui.notice;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.ArticleListReq;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.data.protocol.response.ArticleListResp;
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

public class BiddingListViewModel extends BaseViewModel {

    private final DataRepository dataRepository;
    public MutableLiveData<DataLoadState<Integer>> mldArticleListLoadState = new MutableLiveData<>();
    private final HashMap<Integer, List<ArticleInfoResp>> articleInfoListMap = new HashMap<>();

    public BiddingListViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<ArticleInfoResp> getArticleInfoListFromCache(int columnId){
        return articleInfoListMap.get(columnId);
    }
    public void reqArticleList(final int columnId) {
        mldArticleListLoadState.setValue(new DataLoadState<>(LoadState.Loading));
        dataRepository.getArticleList(new ArticleListReq(ProdConstants.ModuleCode.BID_INFORMATION, 5, columnId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResp<ArticleListResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ArticleListResp> resp) {
                        if(BaseConstants.API_HANDLE_SUCCESS == resp.code){
                            articleInfoListMap.put(columnId, resp.data.records);
                            mldArticleListLoadState.setValue(new DataLoadState<>(LoadState.Success, columnId));
                        }else {
                            mldArticleListLoadState.setValue(new DataLoadState<>(LoadState.Failed));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldArticleListLoadState.setValue(new DataLoadState<>(LoadState.Failed));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}