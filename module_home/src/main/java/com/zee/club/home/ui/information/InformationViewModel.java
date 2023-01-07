package com.zee.club.home.ui.information;

import static com.zee.club.home.config.ProdConstants.ModuleCode.INDUSTRY_INFORMATION_TOP;
import static com.zee.club.home.config.ProdConstants.ModuleCode.INDUSTRY_LATEST_INFORMATION;
import static com.zee.club.home.config.ProdConstants.ModuleCode.INDUSTRY_RELATED_INFORMATION;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.ArticleListReq;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.data.protocol.response.ArticleListResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InformationViewModel extends BaseViewModel {
    public MutableLiveData<List<ArticleInfoResp>> bannerList = new MutableLiveData<>();
    public MutableLiveData<List<ArticleInfoResp>> newInfoList = new MutableLiveData<>();
    public MutableLiveData<List<ArticleInfoResp>> columnList = new MutableLiveData<>();
    public HashMap<String, List<ArticleInfoResp>> columnListMap = new HashMap<>();
    public MutableLiveData<LoadState> mldInitDataLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldInitDataLoadStateColumn = new MutableLiveData<>();

    public void initData() {
        mldInitDataLoadState.setValue(LoadState.Loading);
        getInformationBannerData(5);
        getNewInformationData(5);
    }

    public void getInformationBannerData(int pageSize) {
        DataRepository.getInstance().getArticleList(new ArticleListReq(INDUSTRY_INFORMATION_TOP, pageSize))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResp<ArticleListResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ArticleListResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            if (resp.data.records.size() > 0) {
                                bannerList.setValue(resp.data.records);
                            }
                        }
                        mldInitDataLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldInitDataLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getNewInformationData(int pageSize) {
        DataRepository.getInstance().getArticleList(new ArticleListReq(INDUSTRY_LATEST_INFORMATION, pageSize))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResp<ArticleListResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ArticleListResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            if (resp.data.records.size() > 0) {
                                newInfoList.setValue(resp.data.records);
                            }
                        }
                        mldInitDataLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldInitDataLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getRelatedInformationData(int columnId) {
        mldInitDataLoadStateColumn.setValue(LoadState.Loading);
        DataRepository.getInstance().getArticleList(new ArticleListReq(INDUSTRY_RELATED_INFORMATION, 5, columnId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResp<ArticleListResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ArticleListResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            if (resp.data.records.size() > 0) {
                                columnListMap.put(String.valueOf(columnId), resp.data.records);
                            }
                        }
                        mldInitDataLoadStateColumn.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mldInitDataLoadStateColumn.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
