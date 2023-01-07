package com.zeewain.base.data.http;

import com.zeewain.base.BaseApplication;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.utils.GlideApp;
import com.zeewain.base.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class TokenInterceptor implements Interceptor {

    public TokenInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        if (BaseApplication.userToken != null && !BaseApplication.userToken.isEmpty()) {
            builder.addHeader("x_auth_token", BaseApplication.userToken);
        }
        if (BaseApplication.xAssociation != null && !BaseApplication.xAssociation.isEmpty()) {
            builder.addHeader("X-ASSOCIATION-INFO", BaseApplication.xAssociation);
        }
        if (BaseApplication.platformInfo != null) {
            builder.addHeader("Platform-Info", BaseApplication.platformInfo);
        }
        Response response = chain.proceed(builder.build());
        if (response.code() == 401) {
            BaseApplication.handleUnauthorized();
        }
        return response;
    }
}

