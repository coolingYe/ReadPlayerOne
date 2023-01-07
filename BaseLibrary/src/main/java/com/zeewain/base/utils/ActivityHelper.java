package com.zeewain.base.utils;

import android.content.Context;
import android.content.Intent;

import com.zeewain.base.BaseApplication;

public class ActivityHelper {

    public static void gotoDetailActivity(Context context, String skuId){
        try {
            Intent intent = new Intent(context, Class.forName("com.zee.club.ui.detail.DetailActivity"));
            intent.putExtra("skuId", skuId);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void navigateToArticleDetailsPage(Context context, String[] arrays){
        try {
            Intent intent = new Intent(context, Class.forName("com.zee.club.home.ui.article.ArticleDetailActivity"));
            intent.putExtra("UserCenter", arrays);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void navigateToLogOnPage(Context context){
        try {
            Intent intent = new Intent(context, Class.forName("com.zee.club.user.ui.login.LoginActivity"));
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void navigateToMainActivity(Context context){
        try {
            Intent intent = new Intent(context, Class.forName("com.zee.club.ui.main.MainActivity"));
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Navigate to the MainPage if you are logged on else LogOnPage.
     */
    public static void navigateToLogOnPageOrMainPage(Context context) {
        String userToken = BaseApplication.userToken;
        if (userToken.isEmpty()) {
            navigateToLogOnPage(context);
        } else {
            navigateToMainActivity(context);
        }
    }
}
