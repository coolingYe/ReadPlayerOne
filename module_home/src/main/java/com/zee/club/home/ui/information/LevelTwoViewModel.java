package com.zee.club.home.ui.information;

import static com.zee.club.home.config.ProdConstants.isMockData;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.ArticleListReq;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.data.protocol.response.ArticleListResp;
import com.zee.club.home.data.protocol.response.RankingCompanyResp;
import com.zee.club.home.data.protocol.response.RankingPeopleResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LevelTwoViewModel extends BaseViewModel {
    public List<ArticleInfoResp> dataList = new ArrayList<>();
    public MutableLiveData<LoadState> mldInitDataLoadState = new MutableLiveData<>();
    public MutableLiveData<RankingCompanyResp> rankingCompanyResp = new MutableLiveData<>();
    public MutableLiveData<RankingPeopleResp> rankingPeopleResp = new MutableLiveData<>();

    public void getArticleInfoData(String code, int pageSize) {
        DataRepository.getInstance().getArticleList(new ArticleListReq(code, pageSize))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResp<ArticleListResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ArticleListResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            if (resp.data.records.size() > 0) {
                                if (isMockData) {
                                    dataList = resp.data.records;
                                    for (int i = 0; i < 5; i++) {
                                        dataList.add(dataList.get(0));
                                    }
                                } else dataList = resp.data.records;
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

    public void getCompanyRankingData(String topN, boolean isShowSelf) {
        DataRepository.getInstance().getRankingEnterprise(topN, isShowSelf)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResp<RankingCompanyResp>>() {
                    @Override
                    public void onNext(BaseResp<RankingCompanyResp> t) {
                        if (BaseConstants.API_HANDLE_SUCCESS == t.code) {
                            if (t.data.getRankingList().size() > 0) {
                                rankingCompanyResp.setValue(new RankingCompanyResp(t.data.getRankingList(), t.data.getSelfRanking()));
                            }
                        }
                        mldInitDataLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mldInitDataLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getPersonRankingData(String topN, boolean isShowSelf) {
        DataRepository.getInstance().getRankingPeople(topN, isShowSelf)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResp<RankingPeopleResp>>() {
                    @Override
                    public void onNext(BaseResp<RankingPeopleResp> t) {
                        if (BaseConstants.API_HANDLE_SUCCESS == t.code) {
                            if (t.data.getRankingList().size() > 0) {
                                rankingPeopleResp.setValue(new RankingPeopleResp(t.data.getRankingList(), t.data.getSelfRanking()));
                            }
                        }
                        mldInitDataLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mldInitDataLoadState.setValue(LoadState.Failed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
