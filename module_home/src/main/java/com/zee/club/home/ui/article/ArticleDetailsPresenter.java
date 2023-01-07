package com.zee.club.home.ui.article;

import android.webkit.WebView;

import com.zee.club.home.data.protocol.request.EnergyAddReq;
import com.zee.club.home.data.protocol.request.PraiseFavoriteReq;
import com.zee.club.home.data.protocol.response.PraiseFavoriteResp;
import com.zee.club.home.data.source.http.service.RestfulCallback;
import com.zeewain.base.config.BaseConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ArticleDetailsPresenter implements ArticleDetailsContract.Presenter {
    private final ArticleDetailViewModel viewModel;
    private final ArticleDetailsContract.View view;

    ArticleDetailsPresenter(
            ArticleDetailViewModel viewModel,
            ArticleDetailsContract.View view
    ) {
        this.viewModel = viewModel;
        this.view = view;
    }

    @Override
    public void reqDetailInfo(String articleId) {
        viewModel.reqDetailInfo(articleId);
    }

    @Override
    public void requestEnergyAdd(EnergyAddReq req) {
        viewModel.requestEnergyAdd(req);
    }

    @Override
    public void reqCheckPraiseAndFavorites(String objId) {
        viewModel.requestMergePraiseFavorite(objId, new RestfulCallback<PraiseFavoriteResp>() {
            @Override
            public void onSuccess(PraiseFavoriteResp praiseFavoriteResp) {
                if (praiseFavoriteResp.getPraiseBody().code == BaseConstants.API_HANDLE_SUCCESS) {
                    viewModel.hasPraise.setValue(Objects.equals(objId, praiseFavoriteResp.getPraiseBody().data.getObjId()));
                }
                if (praiseFavoriteResp.getFavoritesBody().code == BaseConstants.API_HANDLE_SUCCESS) {
                    viewModel.hasFavorites.setValue(Objects.equals(objId, praiseFavoriteResp.getFavoritesBody().data.getObjId()));
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }

            @Override
            public void onComplete() {
                view.hasChecked();
            }
        });
    }

    @Override
    public void clickPraiseView(String articleId) {
        if (articleId != null) {
            List<String> list = new ArrayList<>();
            list.add(articleId);
            if (Boolean.TRUE.equals(viewModel.hasPraise.getValue())) {
                viewModel.delInformationPraised(new PraiseFavoriteReq.delRequestBody(list));
            } else {
                addInformationPraised();
            }
        }
    }

    @Override
    public void clickFavoriteView(String articleId) {
        if (articleId != null) {
            List<String> list = new ArrayList<>();
            list.add(articleId);
            if (Boolean.TRUE.equals(viewModel.hasFavorites.getValue())) {
                viewModel.delInformationFavorite(new PraiseFavoriteReq.delRequestBody(list));
            } else {
                addInformationFavorite();
            }
        }
    }

    public void addInformationPraised() {
        if (viewModel.articleData.getValue() != null) {
            viewModel.addInformationPraised(new PraiseFavoriteReq(
                    viewModel.articleData.getValue().articleId,
                    viewModel.articleData.getValue().title,
                    viewModel.articleData.getValue().covers));
        }
    }

    public void addInformationFavorite() {
        if (viewModel.articleData.getValue() != null) {
            viewModel.addInformationFavorite(new PraiseFavoriteReq(
                    viewModel.articleData.getValue().articleId,
                    viewModel.articleData.getValue().title,
                    viewModel.articleData.getValue().covers));
        }
    }

    public boolean isScrollToBottom(WebView webView) {
        if (webView == null) return false;
        float webViewRootHeight = webView.getContentHeight() * webView.getScale();
        float webViewCurrentHeight = webView.getHeight() + webView.getScrollY();
        return (webViewRootHeight - webViewCurrentHeight) < 5;
    }
}
