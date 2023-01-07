package com.zeewain.base.utils;

import android.content.Context;
import android.view.View;

import androidx.core.view.ViewCompat;

import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.config.SharePrefer;

import java.io.File;

public class CommonUtils {

    public static String getFileUsePath(String fileId, String version, int type, Context context){
        if(type == BaseConstants.DownloadFileType.HOST_APP || type == BaseConstants.DownloadFileType.PLUGIN_APP){
            return context.getExternalCacheDir().getPath() + "/" + fileId + "_" + version + ".apk";
        }else if(type == BaseConstants.DownloadFileType.SHARE_LIB){
            return context.getFilesDir().getPath() + "/" + fileId + "_" + version + ".zip";
        }else{
            return fileId;
        }
    }

    public static String getModelStorePath(String modelFileName, Context context){
        String path = context.getFilesDir().getPath() + "/models";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path + "/" + modelFileName;
    }

    public static boolean createOrClearPluginModelDir(){
        File file = new File(BaseConstants.PLUGIN_MODEL_PATH);
        if(!file.exists()){
            return file.mkdirs();
        }else {
            File[] files = file.listFiles();
            for(File tmpFile : files){
                if (tmpFile.isFile()){
                    tmpFile.delete();
                }
            }
        }
        return true;
    }

    public static boolean isUserLogin(){
        String userToken = SPUtils.getInstance().getString(SharePrefer.userToken);
        if(userToken != null && !userToken.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isCommonUser(){
        int userType = SPUtils.getInstance().getInt(SharePrefer.UserType, 1);
        return userType == 1;
    }

    public static String getUserInfo(){
        return SPUtils.getInstance().getString(SharePrefer.userAccount);
    }

    public static void scaleView(View view, float scale){
        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(scale)
                .scaleY(scale)
                .start();
    }

}
