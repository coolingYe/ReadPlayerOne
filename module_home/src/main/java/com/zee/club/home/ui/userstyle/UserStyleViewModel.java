package com.zee.club.home.ui.userstyle;

import static com.zee.club.home.config.ProdConstants.ModuleCode.COMPANY_THRILLING_INFORMATION;
import static com.zee.club.home.config.ProdConstants.ModuleCode.MEMBER_MIEN_TOP;
import static com.zee.club.home.config.ProdConstants.ModuleCode.WIN_BID_INFORMATION;
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
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserStyleViewModel extends BaseViewModel {
    public MutableLiveData<List<ArticleInfoResp>> bannerList = new MutableLiveData<>();
    public MutableLiveData<List<ArticleInfoResp>> winBidList = new MutableLiveData<>();
    public MutableLiveData<List<ArticleInfoResp>> thrillingList = new MutableLiveData<>();
    public MutableLiveData<RankingCompanyResp> rankingCompanyResp = new MutableLiveData<>();
    public MutableLiveData<RankingPeopleResp> rankingPeopleResp = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldInitDataLoadState = new MutableLiveData<>();

    public HashMap<String, List<ArticleInfoResp>> listHashMap = new HashMap<>();

    public void initData() {
        mldInitDataLoadState.setValue(LoadState.Loading);
        geArticleInfoData(MEMBER_MIEN_TOP, 5);
        getCompanyRankingData();
        getPersonRankingData();
        geArticleInfoData(WIN_BID_INFORMATION, 5);
        geArticleInfoData(COMPANY_THRILLING_INFORMATION, 5);
    }

    public void geArticleInfoData(String code, int pageSize) {
        DataRepository.getInstance().getArticleList(new ArticleListReq(code, pageSize))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResp<ArticleListResp>>() {
                    @Override
                    public void onNext(@NonNull BaseResp<ArticleListResp> resp) {
                        if (BaseConstants.API_HANDLE_SUCCESS == resp.code) {
                            if (resp.data.records.size() > 0) {
                                if (isMockData) {
                                    List<ArticleInfoResp> list = resp.data.records;
                                    for (int i = 0; i < 5; i++) {
                                        list.add(list.get(0));
                                    }
                                    setDataMapping(code, list);
                                } else setDataMapping(code, resp.data.records);
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

    private void setDataMapping(String code, List<ArticleInfoResp> list) {
        switch (code) {
            case MEMBER_MIEN_TOP:
                bannerList.setValue(list);
                break;
            case WIN_BID_INFORMATION:
                winBidList.setValue(list);
                break;
            case COMPANY_THRILLING_INFORMATION:
                thrillingList.setValue(list);
                break;
        }
    }

    public List<ArticleInfoResp> getDataMapping(String code) {
        switch (code) {
            case WIN_BID_INFORMATION:
                return winBidList.getValue();
            case COMPANY_THRILLING_INFORMATION:
                return thrillingList.getValue();
        }
        return new ArrayList<>();
    }

    public void getCompanyRankingData() {
        DataRepository.getInstance().getRankingEnterprise("10", true)
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

    public void getPersonRankingData() {
        DataRepository.getInstance().getRankingPeople("10", true)
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
