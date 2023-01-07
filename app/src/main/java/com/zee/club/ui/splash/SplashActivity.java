package com.zee.club.ui.splash;

import static com.zeewain.base.utils.ActivityHelper.navigateToLogOnPageOrMainPage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zee.club.R;
import com.zee.club.ui.guide.GuideActivity;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.utils.AppVersionUtil;
import com.zeewain.base.utils.SPUtils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String VERSION_KEY = "VERSION_KEY";
    private int time = BaseConstants.waitingTime;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            time--;
            if (time == 0) {
                hasFirstRun();
                handler.removeMessages(0);
            }
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBlackBar(this);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    private static void hideBlackBar(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(lp);
        }
    }


    public void hasFirstRun() {
        String currentVersion = AppVersionUtil.getAppVersionName(this);
        String lastVersion = SPUtils.getInstance().getString(VERSION_KEY, "");
        if (!lastVersion.equals(currentVersion)) {
            SPUtils.getInstance().put(VERSION_KEY, currentVersion);
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            navigateToLogOnPageOrMainPage(this);
        }
        finish();
    }


}