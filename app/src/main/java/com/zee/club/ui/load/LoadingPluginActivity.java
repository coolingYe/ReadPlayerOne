package com.zee.club.ui.load;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.zee.club.R;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.utils.FileUtils;
import com.zeewain.base.utils.ZipUtils;
import com.zwn.lib_download.DownloadListener;
import com.zwn.lib_download.DownloadService;
import com.zwn.lib_download.db.CareController;
import com.zwn.lib_download.model.DownloadInfo;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadingPluginActivity extends Activity {

    private static final String TAG = "zwn_app";
    private String pluginName;
    private String akCode;
    private String skCode;
    private String userToken;
    private DownloadInfo downloadInfo;
    private AnimationDrawable animDrawable;
    private static final int MSG_START_PLUGIN = 1;
    private long startTime = 0;
    private final MyHandler mHandler = new MyHandler(Looper.myLooper(),this);
    private final AtomicInteger pendingPrepareCount = new AtomicInteger();
    private static final int REQUEST_CODE_PLUGIN = 1000;
    private final List<File> unzipFiles = new ArrayList<>();
    private final ExecutorService mFixedPool = Executors.newFixedThreadPool(2);
    private static final ConcurrentHashMap<String, String> downloadLibMap = new ConcurrentHashMap<>(5);
    private static volatile boolean isUnzipCalled = false;
    private static volatile boolean isPrepareStart = false;
    private int failedTryCount = 3;

    private DownloadService.DownloadBinder downloadBinder;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder)iBinder;
            if(downloadLibMap.size() > 0){
                downloadBinder.registerDownloadListener(downloadListener);
                checkRelyDownloadLib();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    void bindDownloadService(){
        Intent intent = new Intent();
        intent.setClass(this, DownloadService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    DownloadListener downloadListener = new DownloadListener(){

        @Override
        public void onProgress(String fileId, int progress, long loadedSize, long fileSize) {

        }

        @Override
        public void onSuccess(String fileId, int type, File file) {
            if(downloadLibMap.containsKey(fileId)){
                downloadLibMap.remove(fileId);
                checkToUnzip();
            }
        }

        @Override
        public void onFailed(String fileId, int type, int code) {
            if(downloadLibMap.containsKey(fileId)){
                failedTryCount--;
                if(failedTryCount > 0) {
                    downloadBinder.startDownload(fileId);
                }else{
                    finish();
                }
            }
        }

        @Override
        public void onPaused(String fileId) {}

        @Override
        public void onCancelled(String fileId) {}

        @Override
        public void onUpdate(String fileId) {
            checkRelyDownloadLib();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_plugin);

        bindDownloadService();

        /*ImageView imageView = findViewById(R.id.img_progress_loading);
        animDrawable = (AnimationDrawable)imageView.getBackground();
        animDrawable.start();*/

        pluginName = getIntent().getStringExtra(BaseConstants.EXTRA_PLUGIN_NAME);
        akCode = getIntent().getStringExtra(BaseConstants.EXTRA_AUTH_AK_CODE);
        skCode = getIntent().getStringExtra(BaseConstants.EXTRA_AUTH_SK_CODE);

        if(pluginName == null || pluginName.isEmpty()){
            finish();
            return;
        }

        downloadInfo = CareController.instance.getDownloadInfoByFileId(pluginName);
        if(downloadInfo == null){
            finish();
            return;
        }

        startTime = System.currentTimeMillis();

        Log.i(TAG, "onCreate() " + downloadInfo);

        isPrepareStart = false;
        isUnzipCalled = false;
        pendingPrepareCount.set(1);

        initDownloadLibMap(downloadInfo.relyIds);

        File pluginFile = new File(downloadInfo.filePath);
        if (pluginFile.exists()) {//new version
            installPlugin(pluginName, downloadInfo.filePath);
        }else{
            if(RePlugin.isPluginInstalled(pluginName)){
                prepareStartPlugin();
            }else{//in installing?
                finish();
            }
        }
    }


    private void initDownloadLibMap(String relyIds){
        if(relyIds != null && !relyIds.isEmpty()) {
            String[] relyIdArray = relyIds.split(",");
            for (String relyId : relyIdArray) {
                DownloadInfo downloadLib = CareController.instance.getDownloadInfoByFileId(relyId);
                if(downloadLib == null){
                    Log.e(TAG, "downloadLib null, relyId=" + relyId);
                    finish();
                    return;
                }else if (downloadLib.status != DownloadInfo.STATUS_SUCCESS) {
                    downloadLibMap.put(downloadLib.fileId, downloadLib.fileId);
                }
            }
        }
    }

    private void checkRelyDownloadLib(){
        Iterator<Map.Entry<String, String>> iterator = downloadLibMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            String fileId = entry.getKey();
            DownloadInfo downloadLib = CareController.instance.getDownloadInfoByFileId(fileId);
            if (downloadLib.status == DownloadInfo.STATUS_SUCCESS) {
                iterator.remove();
            }else if(downloadLib.status == DownloadInfo.STATUS_STOPPED) {
                downloadBinder.startDownload(fileId);
            }
        }
        checkToUnzip();
    }

    private void checkToUnzip(){
        if(downloadLibMap.size() == 0 && isPrepareStart){
            unzipShareLib();
        }
    }

    private void installPlugin(final String pluginName, final String filePath){
        mFixedPool.execute(() -> {
            PluginInfo info = RePlugin.install(filePath);
            if(info != null){
                prepareStartPlugin();
            }else{//安装插件失败 need retry?
                Log.e(TAG, "installPlugin() failed! ");
                if(RePlugin.isPluginInstalled(pluginName)){
                    prepareStartPlugin();
                }else{
                    finish();
                }
            }
        });
    }

    private void prepareStartPlugin(){
        Log.i(TAG, "prepareStartPlugin() cost time=" + (System.currentTimeMillis() - startTime));
        PluginInfo pluginInfo = RePlugin.getPluginInfo(pluginName);
        if(RePlugin.isPluginRunning(pluginName) && (pluginInfo.isNeedUpdate())){
            Log.i(TAG, "prepareStartPlugin() should restart to use new version");
        }
        Log.i(TAG, "pluginInfo.getNativeLibsDir()=" + pluginInfo.getNativeLibsDir());
        String [] fileName = pluginInfo.getNativeLibsDir().list();
        if(fileName != null) {
            for (String s : fileName) {
                Log.i(TAG, "pluginInfo.getNativeLibsDir() file=" + s);
            }
        }
        isPrepareStart = true;
        checkToUnzip();
        //userToken = SPUtils.getInstance("FlutterSharedPreferences").getString("flutter.UserToken");
        userToken = "userToken";
    }

    private synchronized void unzipShareLib(){
        if(!isUnzipCalled) {
            isUnzipCalled = true;
            mFixedPool.execute(() -> {
                try {
                    long currentTime = System.currentTimeMillis();
                    PluginInfo pluginInfo = RePlugin.getPluginInfo(pluginName);

                    if(downloadInfo.relyIds != null && !downloadInfo.relyIds.isEmpty()) {
                        String[] relyIdArray = downloadInfo.relyIds.split(",");
                        for (String relyId : relyIdArray) {
                            DownloadInfo downloadLib = CareController.instance.getDownloadInfoByFileId(relyId);
                            if (downloadLib.status == DownloadInfo.STATUS_SUCCESS) {
                                unzipFiles.addAll(ZipUtils.unzipFile(downloadLib.filePath, pluginInfo.getNativeLibsDir().getPath()));
                            }else{
                                Log.e(TAG, "something wrong!!! unzipShareLib() downloadLib not ready " + downloadLib);
                            }
                        }
                    }
                    Log.i(TAG, "unzipShareLib() cost time=" + (System.currentTimeMillis() - currentTime));
                    for(int i=0; i<unzipFiles.size(); i++){
                        Log.i(TAG, "unzipShareLib file=" + unzipFiles.get(i).getPath());
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    Log.e(TAG, "unzipShareLib() failed! " + exception.toString());
                } finally {
                    decrementCountAndCheck();
                }
            });
        }
    }

    private void decrementCountAndCheck(){
        int newPendingCount = pendingPrepareCount.decrementAndGet();
        if(newPendingCount <= 0){
            Log.i(TAG, "decrementCountAndCheck() Done");
            sendMsgToStartPlugin();
        }
    }

    private void sendMsgToStartPlugin(){
        long currentTime = System.currentTimeMillis();
        if(currentTime - startTime >= 1500){
            mHandler.sendEmptyMessage(MSG_START_PLUGIN);
        }else{
            long delayTime = 1500 - (currentTime - startTime);
            mHandler.sendEmptyMessageDelayed(MSG_START_PLUGIN, delayTime);
        }
    }

    private void startPluginActivity(){
        if(!isFinishing() && !isDestroyed()) {
            mFixedPool.execute(() -> {
                File file = getExternalFilesDir("zeewainpose");
                FileUtils.deleteDirectory(file.getAbsolutePath() + "/model");

                Intent intent = RePlugin.createIntent(pluginName, downloadInfo.mainClassPath);
                intent.putExtra(BaseConstants.EXTRA_AUTH_AK_CODE, "6369474072513871872"/*akCode*/);
                intent.putExtra(BaseConstants.EXTRA_AUTH_SK_CODE, "rrG9HKbVAj20pKnXeK3z+DKpQXa+omm3Fve1aKVHTwM="/*skCode*/);
                intent.putExtra(BaseConstants.EXTRA_HOST_PKG, getPackageName());
                intent.putExtra(BaseConstants.EXTRA_AUTH_URI, (BaseConstants.baseUrl + BaseConstants.baseAuthPath));
                intent.putExtra(BaseConstants.EXTRA_AUTH_TOKEN, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ6ZWV3YWluIiwiYXBwQ29kZSI6Inp3bl9haWlwIiwidXNlcklkIjo0NTk5MjQwOTQ3NzE1Mjc3NiwidXNlckNvZGUiOiJ6d25fNDU5OTI0MDk0NzcxNTI3NzciLCJleHBpcmVUaW1lIjoxNjcyMjE2OTE5NjU3LCJ1c2VyVHlwZSI6MiwiZXhwIjoxNjcyMjE2OTE5fQ.2-e0YSROzWLLJqh_ShWlnMAgpx0eWUXX91sMXgOzCjw"/*userToken*/);
                boolean isSuccess = RePlugin.startActivityForResult(LoadingPluginActivity.this, intent, REQUEST_CODE_PLUGIN);
                if(!isSuccess){
                    Log.e(TAG, "RePlugin.startActivityForResult failed! need restart APP");
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult() requestCode=" + requestCode + ", unzipFiles.size()=" + unzipFiles.size() + ", intent=" + data);
        if(requestCode == REQUEST_CODE_PLUGIN){
            long useTime = System.currentTimeMillis();
            for(int i=0; i<unzipFiles.size(); i++){
                File file = unzipFiles.get(i);
                Log.i(TAG, "unzipShareLib file=" + file.getPath());
                if(file.exists()){
                    file.delete();
                }
            }
            Log.i(TAG, "remove unzipShareLib files cost time=" + (System.currentTimeMillis() - useTime));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        /*animDrawable.stop();
        animDrawable = null;*/
        if(downloadBinder != null)
            downloadBinder.unRegisterDownloadListener(downloadListener);
        unbindService(serviceConnection);
        super.onDestroy();
    }

    public static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public MyHandler(Looper looper, Activity activity) {
            super(looper);
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == MSG_START_PLUGIN) {
                    ((LoadingPluginActivity) activity).startPluginActivity();
                }
            }
        }
    }
}
