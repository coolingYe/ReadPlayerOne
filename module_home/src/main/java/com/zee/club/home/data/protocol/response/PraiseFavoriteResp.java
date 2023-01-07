package com.zee.club.home.data.protocol.response;

import com.zeewain.base.data.protocol.response.BaseResp;

public class PraiseFavoriteResp {
    private BaseResp<Favorites> favoritesBody;
    private BaseResp<Praise> praiseBody;

    public PraiseFavoriteResp(BaseResp<Praise> praiseBody, BaseResp<Favorites> favoritesBody) {
        this.praiseBody = praiseBody;
        this.favoritesBody = favoritesBody;
    }

    public BaseResp<Favorites> getFavoritesBody() {
        return favoritesBody;
    }

    public void setFavoritesBody(BaseResp<Favorites> favoritesBody) {
        this.favoritesBody = favoritesBody;
    }

    public BaseResp<Praise> getPraiseBody() {
        return praiseBody;
    }

    public void setPraiseBody(BaseResp<Praise> praiseBody) {
        this.praiseBody = praiseBody;
    }

    public static class addResponseBody {
        public String praiseId;
        public String favoriteId;
    }
}
