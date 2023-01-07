package com.zee.club.home.ui.article;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.zee.club.home.data.DataRepository;
import com.zee.club.home.data.protocol.request.EnergyAddReq;
import com.zee.club.home.data.protocol.request.PraiseFavoriteReq;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.data.protocol.response.Favorites;
import com.zee.club.home.data.protocol.response.Praise;
import com.zee.club.home.data.protocol.response.PraiseFavoriteResp;
import com.zee.club.home.data.source.http.service.RestfulCallback;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.data.protocol.response.BaseResp;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleDetailViewModel extends BaseViewModel {

    public int energyValue = 0;
    public MutableLiveData<Boolean> hasFavorites = new MutableLiveData<>();
    public MutableLiveData<Boolean> hasPraise = new MutableLiveData<>();
    private final DataRepository dataRepository;
    public MutableLiveData<ArticleInfoResp> articleData = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldArticleInfoLoadState = new MutableLiveData<>();
    public MutableLiveData<LoadState> mldEnergyAddLoadState = new MutableLiveData<>();

    public ArticleDetailViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void reqDetailInfo(String articleId) {
        mldArticleInfoLoadState.setValue(LoadState.Loading);
        dataRepository.getArticleInfo(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RestfulCallback<BaseResp<ArticleInfoResp>>() {
                    @Override
                    public void onSuccess(BaseResp<ArticleInfoResp> t) {
                        if (t.code == BaseConstants.API_HANDLE_SUCCESS) {
                            mldArticleInfoLoadState.setValue(LoadState.Success);
                            articleData.setValue(t.data);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mldArticleInfoLoadState.setValue(LoadState.Failed);
                    }
                });
    }

    public void requestEnergyAdd(EnergyAddReq req) {
        mldEnergyAddLoadState.setValue(LoadState.Loading);
        dataRepository.requestEnergyAdd(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RestfulCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject t) {
                        energyValue = t.getAsJsonObject("data")
                                .get("energyScore").getAsInt();
                        mldEnergyAddLoadState.setValue(LoadState.Success);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mldEnergyAddLoadState.setValue(LoadState.Failed);
                    }
                });
    }

    public Observable<BaseResp<Praise>> reqCheckPraised(String objId) {
        return dataRepository.checkInformationPraised(objId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BaseResp<Favorites>> reqCheckFavorites(String objId) {
        return dataRepository.checkInformationFavorites(objId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void requestMergePraiseFavorite(String objId, RestfulCallback<PraiseFavoriteResp> callback) {
        Observable.zip(reqCheckPraised(objId),reqCheckFavorites(objId), PraiseFavoriteResp::new)
                .subscribe(callback);
    }

    public void delInformationPraised(PraiseFavoriteReq.delRequestBody objIdList) {
        dataRepository.delInformationPraised(objIdList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RestfulCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject t) {
                        int code = t.get("code").getAsInt();
                        if (code == BaseConstants.API_HANDLE_SUCCESS) {
                            hasPraise.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }

    public void delInformationFavorite(PraiseFavoriteReq.delRequestBody objIdList) {
        dataRepository.delInformationFavorite(objIdList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RestfulCallback<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject t) {
                        int code = t.get("code").getAsInt();
                        if (code == BaseConstants.API_HANDLE_SUCCESS) {
                            hasFavorites.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }

    public void addInformationPraised(PraiseFavoriteReq praiseFavoriteReq) {
        dataRepository.addInformationPraised(praiseFavoriteReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RestfulCallback<BaseResp<PraiseFavoriteResp.addResponseBody>>() {
                    @Override
                    public void onSuccess(BaseResp<PraiseFavoriteResp.addResponseBody> t) {
                        if (t.code == BaseConstants.API_HANDLE_SUCCESS) {
                            hasPraise.setValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }

    public void addInformationFavorite(PraiseFavoriteReq praiseFavoriteReq) {
        dataRepository.addInformationFavorite(praiseFavoriteReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RestfulCallback<BaseResp<PraiseFavoriteResp.addResponseBody>>() {
                    @Override
                    public void onSuccess(BaseResp<PraiseFavoriteResp.addResponseBody> t) {
                        if (t.code == BaseConstants.API_HANDLE_SUCCESS) {
                            hasFavorites.setValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }

}
