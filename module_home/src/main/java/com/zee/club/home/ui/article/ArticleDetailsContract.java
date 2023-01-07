package com.zee.club.home.ui.article;

import com.zee.club.home.data.protocol.request.EnergyAddReq;

public class ArticleDetailsContract {
    public interface View {
        void hasChecked();
        void showArticleToast(String text);
        void setHasBottom(boolean b);
    }

    public interface Presenter {
        void reqDetailInfo(String articleId);
        void requestEnergyAdd(EnergyAddReq req);
        void reqCheckPraiseAndFavorites(String objId);
        void clickPraiseView(String articleId);
        void clickFavoriteView(String articleId);
    }
}
