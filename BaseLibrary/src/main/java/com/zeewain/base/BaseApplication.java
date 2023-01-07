package com.zeewain.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.utils.ApkUtil;
import com.zeewain.base.utils.DiskCacheManager;
import com.zeewain.base.utils.SPUtils;
import com.zeewain.base.utils.ToastUtils;

public class BaseApplication extends Application {
    public static Context applicationContext;
    public static String platformInfo = null;
    public static String xAssociation ;
    public static String userToken;

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
        DiskCacheManager.init(this);
        platformInfo = buildPlatformInfo();
        userToken = SPUtils.getInstance().getString(SharePrefer.userToken, "");
        xAssociation = String.valueOf(SPUtils.getInstance().getInt(SharePrefer.associationId));
        xAssociation = "3";
        userToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ6ZWV3YWluIiwiYXBwQ29kZSI6Im1ldGFfYW1fYXBwIiwidXNlcklkIjoxNjY5ODQ3OTEyNzM3NzUxMTMsInVzZXJDb2RlIjoienduXzE2Njk4NDc5MTMxMTUyMzg0MCIsImV4cGlyZVRpbWUiOjE2NzQxMTkwNDc0NzMsInVzZXJUeXBlIjoyLCJleHAiOjE2NzQxMTkwNDd9.20H3XKoPezsGTVZXI-5c-UG5NwEV_B68TwYazMYLBc0";
    }

    public String buildPlatformInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("AndroidPhoneAIIP/").
                append(ApkUtil.getAppVersionName(getApplicationContext())).
                append(" (ZWN_AIIP_001 1.0; Android ").append(Build.VERSION.RELEASE).append(")");
        return stringBuffer.toString();
    }

    public static synchronized void handleUnauthorized() {

    }

}
