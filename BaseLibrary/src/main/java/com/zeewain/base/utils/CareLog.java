package com.zeewain.base.utils;

import android.util.Log;

public class CareLog {
    private static final String TAG = "ZeeLog";

    public static void w(String tag, String msg){
        if(tag.isEmpty()){
            Log.w(TAG, msg);
        }else{
            Log.w(TAG, "["+tag+"]"+msg);
        }
    }

    public static void d(String tag, String msg){
        if(tag.isEmpty()){
            Log.d(TAG, msg);
        }else{
            Log.d(TAG, "["+tag+"]"+msg);
        }
    }

    public static void e(String tag, String msg){
        if(tag.isEmpty()){
            Log.e(TAG, msg);
        }else{
            Log.e(TAG, "["+tag+"]"+msg);
        }
    }

    public static void w(String msg){
        Log.w(TAG, msg);
    }

    public static void d(String msg){
        Log.d(TAG, msg);
    }

    public static void e(String msg){
        Log.e(TAG, msg);
    }

    public static void i(String tag, String msg){
        if(tag.isEmpty()){
            Log.i(TAG, msg);
        }else{
            Log.i(TAG, "["+tag+"]"+msg);
        }
    }
}
