package com.zee.club.ui.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.zee.club.R;
import com.zee.club.data.DataRepository;
import com.zee.club.data.protocol.request.AppRankingReq;
import com.zee.club.data.protocol.request.PublishReq;
import com.zee.club.data.protocol.request.UpgradeReq;
import com.zee.club.data.protocol.response.AlgorithmInfoResp;
import com.zee.club.data.protocol.response.AppRankingResp;
import com.zee.club.data.protocol.response.ModelInfoResp;
import com.zee.club.data.protocol.response.ProDetailResp;
import com.zee.club.data.protocol.response.PublishResp;
import com.zee.club.ui.detail.adapter.DetailRankingAdapter;
import com.zee.club.utils.DownloadHelper;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.utils.FileUtils;
import com.zeewain.base.utils.GlideApp;
import com.zeewain.base.utils.NetworkUtil;
import com.zeewain.base.widgets.CustomActionBar;
import com.zwn.lib_download.DownloadListener;
import com.zwn.lib_download.DownloadService;
import com.zwn.lib_download.db.CareController;
import com.zwn.lib_download.model.DownloadInfo;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity {
    private DetailViewModel viewModel;
    private MaterialButton btnDetailDownload;
    private VideoView videoView;
    private ImageView ivProductDetailImg, ivVideoPlay;
    private TextView tvProductTitle, tvProductSummary;
    private String videoUrl;
    private String skuId;
    private DownloadService.DownloadBinder downloadBinder = null;
    private boolean isProductRelease = true;
    private String currentFileId;
    private String lastVersion;
    private boolean isClickEnable = false;
    private boolean isAddCollected;
    private boolean isRequest=false;
    private static final int MSG_DOWNLOAD_ON_PROGRESS = 1;
    private static final int MSG_DOWNLOAD_ON_FAILED = 2;
    private static final int MSG_DOWNLOAD_ON_UPDATE = 3;
    private static final String KEY_PROGRESS = "Progress";
    private final MyHandler handler = new MyHandler(Looper.myLooper(),this);
    private DetailRankingAdapter detailRankingAdapter;

    private final DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(String fileId, int progress, long loadedSize, long fileSize) {
            if (fileId.equals(currentFileId)) {
                Message message = Message.obtain(handler);
                message.what = MSG_DOWNLOAD_ON_PROGRESS;
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_PROGRESS, progress);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }

        @Override
        public void onSuccess(String fileId, int type, File file) {}

        @Override
        public void onFailed(String fileId, int type, int code) {
            if (fileId.equals(currentFileId)) {
                handler.sendEmptyMessage(MSG_DOWNLOAD_ON_FAILED);
                runOnUiThread(() -> {
                    if(!NetworkUtil.isNetworkAvailable(DetailActivity.this)){
                        showToast("网络连接异常！");
                    }
                });
            }
        }

        @Override
        public void onPaused(String fileId) {}

        @Override
        public void onCancelled(String fileId) {}

        @Override
        public void onUpdate(String fileId) {
            if(fileId.equals(currentFileId)){
                handler.sendEmptyMessage(MSG_DOWNLOAD_ON_UPDATE);
            }
        }
    };

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
            if (downloadBinder != null) {
                downloadBinder.registerDownloadListener(downloadListener);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) { }
    };

    private void bindService() {
        Intent bindIntent = new Intent(this.getApplicationContext(), DownloadService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new DetailViewModelFactory(DataRepository.getInstance())).get(DetailViewModel.class);
        setContentView(R.layout.activity_detail);

        skuId = getIntent().getStringExtra("skuId");
        if (TextUtils.isEmpty(skuId)){
            finish();
            return;
        }

        bindService();

        initData();
        initViewObservable();
        viewModel.reqDetailInfo(skuId);
    }

    public void initData() {
        videoView = findViewById(R.id.video_view_product_detail);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    ivVideoPlay.setVisibility(View.VISIBLE);
                }else{
                    videoView.start();
                    ivVideoPlay.setVisibility(View.GONE);
                }
            }
        });

        ivVideoPlay = findViewById(R.id.iv_video_play);
        ivVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    ivVideoPlay.setVisibility(View.VISIBLE);
                }else{
                    videoView.start();
                    ivVideoPlay.setVisibility(View.GONE);
                }
            }
        });

        ivProductDetailImg = findViewById(R.id.iv_product_detail_img);
        tvProductTitle = findViewById(R.id.tv_product_title);
        tvProductSummary = findViewById(R.id.tv_product_summary);

        btnDetailDownload = findViewById(R.id.btn_detail_download);
        btnDetailDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleAlgorithmLib()) {
                    if (viewModel.upgradeResp != null) {//处理升级
                        handleUpgrade();
                    } else {
                        handleDownload();
                    }
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_detail_ranking);
        detailRankingAdapter = new DetailRankingAdapter(new ArrayList<>());
        recyclerView.setAdapter(detailRankingAdapter);
    }

    public void initViewObservable() {
        viewModel.mldDetailLoadState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Loading == loadState) {
                    /*binding.networkErrViewDetail.setVisibility(View.GONE);
                    binding.clDetailLayout.setVisibility(View.GONE);
                    binding.loadingViewDetail.setVisibility(View.VISIBLE);
                    binding.loadingViewDetail.startAnim();*/
                } else if (LoadState.Success == loadState) {
                    if (viewModel.proDetailResp.getPutawayStatus() == 1) {
                        viewModel.reqPublishVersionInfo(new PublishReq(viewModel.proDetailResp.getSoftwareCode()));
                        //viewModel.reqFavoriteState(skuId);
                        viewModel.reqAppRanking(new AppRankingReq("ZWN_SW_UNITY_KLHX_001"/*viewModel.proDetailResp.getSoftwareCode()*/, 10));
                    }else{
                        /*binding.loadingViewDetail.stopAnim();
                        binding.loadingViewDetail.setVisibility(View.GONE);*/
                        if(viewModel.proDetailResp.getPutawayStatus() == 2){
                            /*binding.layoutDownloadDetail.setVisibility(View.GONE);
                            binding.clCollectShareLayout.setVisibility(View.GONE);
                            binding.txtOffTheShelf.setVisibility(View.VISIBLE);*/
                        }else{
                            /*binding.layoutDownloadDetail.setVisibility(View.VISIBLE);
                            binding.clCollectShareLayout.setVisibility(View.VISIBLE);
                            binding.txtOffTheShelf.setVisibility(View.GONE);*/

                        }
                        //binding.clDetailLayout.setVisibility(View.VISIBLE);
                        handleDetailUiUpdate(viewModel.proDetailResp);
                    }

                } else if (LoadState.Failed == loadState) {
                    /*binding.loadingViewDetail.stopAnim();
                    binding.loadingViewDetail.setVisibility(View.GONE);
                    binding.networkErrViewDetail.setVisibility(View.VISIBLE);*/
                }
            }
        });

        viewModel.mldPublishLoadState.observe(this, loadState -> {
            if(LoadState.Loading == loadState) {
                //binding.loadingViewDetail.setVisibility(View.VISIBLE);
            } else if (LoadState.Success == loadState) {
                /*binding.loadingViewDetail.stopAnim();
                binding.loadingViewDetail.setVisibility(View.GONE);
                binding.clDetailLayout.setVisibility(View.VISIBLE);*/

                handleDetailUiUpdate(viewModel.proDetailResp);

                PublishResp publishResp = viewModel.publishResp;
                if ((publishResp != null) && (publishResp.getSoftwareInfo() != null)) {
                    isProductRelease = true;
                    currentFileId = publishResp.getSoftwareInfo().getSoftwareCode();
                    lastVersion = publishResp.getSoftwareVersion();

                    String fileSize = FileUtils.FormatFileSizeNoUnit(Long.parseLong(publishResp.getPackageSize()));
                    btnDetailDownload.setText(String.format("下载(%sM)", fileSize));

                    DownloadInfo dbDownloadInfo = CareController.instance.getDownloadInfoByFileId(currentFileId);
                    if (dbDownloadInfo != null) {
                        //binding.downloadIcon.setVisibility(View.VISIBLE);
                        if (dbDownloadInfo.status == DownloadInfo.STATUS_SUCCESS) {
                            //binding.downloadIcon.setVisibility(View.GONE);
                            btnDetailDownload.setText("立即体验");
                            if (!dbDownloadInfo.version.equals(lastVersion)) {
                                viewModel.reqUpgradeVersionInfo(new UpgradeReq(dbDownloadInfo.version, publishResp.getSoftwareInfo().getSoftwareCode()));
                            }
                        } else if (dbDownloadInfo.status == DownloadInfo.STATUS_STOPPED) {
                            //binding.downloadIcon.setImageResource(R.mipmap.icon_download_stop);
                            btnDetailDownload.setText("继续");
                            if (!dbDownloadInfo.version.equals(lastVersion)) {
                                viewModel.reqUpgradeVersionInfo(new UpgradeReq(dbDownloadInfo.version, publishResp.getSoftwareInfo().getSoftwareCode()));
                            }
                        } else if (dbDownloadInfo.status == DownloadInfo.STATUS_LOADING) {
                            //binding.downloadIcon.setImageResource(R.mipmap.icon_download_loading);
                            btnDetailDownload.setText("下载中");
                        }else if (dbDownloadInfo.status == DownloadInfo.STATUS_PENDING) {
                            //binding.downloadIcon.setImageResource(R.mipmap.icon_download_waiting);
                            btnDetailDownload.setText("等待中");
                        }
                        if(dbDownloadInfo.loadedSize > 0){
                            /*int progress = (int)((dbDownloadInfo.loadedSize * 1.0f / dbDownloadInfo.fileSize) * 100);
                            binding.gradientProgressViewDetail.setProgress(progress);*/
                        }
                    }
                } else {
                    isProductRelease = false;
                }
            } else if (LoadState.Failed == loadState) {
                /*binding.loadingViewDetail.stopAnim();
                binding.loadingViewDetail.setVisibility(View.GONE);
                binding.networkErrViewDetail.setVisibility(View.VISIBLE);*/
            }
        });

        viewModel.mldUpgradeLoadState.observe(this, loadState -> {
            if (LoadState.Success == loadState) {
                if(viewModel.upgradeResp != null){
                    if(viewModel.upgradeResp.isForcible()){
                        String fileSize = FileUtils.FormatFileSizeNoUnit(Long.parseLong(viewModel.upgradeResp.getPackageSize()));
                        /*binding.downloadIcon.setImageResource(R.mipmap.icon_download);
                        binding.downloadIcon.setVisibility(View.VISIBLE);*/
                        btnDetailDownload.setText(String.format("更新(%sM)", fileSize));
                    }
                }
            }
        });

        viewModel.mldAppRankingLoadState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if (LoadState.Success == loadState) {
                    detailRankingAdapter.setDataList(viewModel.appRankingResp.getRankingTopN());
                    handleSelfRanking();
                }
            }
        });
    }

    private void handleSelfRanking(){
        FrameLayout frameLayout = findViewById(R.id.fl_detail_self_ranking);
        AppRankingResp.RankingInfo rankingInfo = viewModel.appRankingResp.getSelf();
        if(rankingInfo != null && rankingInfo.userRank > 0){
            frameLayout.setVisibility(View.VISIBLE);
            ImageView ivItemRankingImg = findViewById(R.id.iv_detail_app_ranking);
            ImageView ivItemHeadImg = findViewById(R.id.iv_detail_app_head);
            GlideApp.with(ivItemHeadImg.getContext())
                    .load(rankingInfo.pic)
                    .into(ivItemHeadImg);

            TextView tvItemRanking = findViewById(R.id.tv_detail_app_ranking);
            TextView tvItemUid = findViewById(R.id.tv_detail_app_uid);
            TextView tvItemScore = findViewById(R.id.tv_detail_app_score);
            TextView tvItemDate = findViewById(R.id.tv_detail_app_date);

            if(rankingInfo.userRank == 1){
                ivItemRankingImg.setVisibility(View.VISIBLE);
                tvItemRanking.setVisibility(View.GONE);
                ivItemRankingImg.setImageResource(R.mipmap.ic_gold_medal);
            }else if(rankingInfo.userRank == 2){
                ivItemRankingImg.setVisibility(View.VISIBLE);
                tvItemRanking.setVisibility(View.GONE);
                ivItemRankingImg.setImageResource(R.mipmap.ic_silver_medal);
            }else if(rankingInfo.userRank == 3){
                ivItemRankingImg.setVisibility(View.VISIBLE);
                tvItemRanking.setVisibility(View.GONE);
                ivItemRankingImg.setImageResource(R.mipmap.ic_bronze_medal);
            }else{
                ivItemRankingImg.setVisibility(View.GONE);
                tvItemRanking.setVisibility(View.VISIBLE);
                tvItemRanking.setText(String.valueOf(rankingInfo.userRank));
            }
            tvItemUid.setText(rankingInfo.userName);
            tvItemScore.setText(String.valueOf(rankingInfo.score));
            tvItemDate.setText(rankingInfo.getPlayDate());
        }else{
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void handleDetailUiUpdate(ProDetailResp proDetailResp){
        CustomActionBar customActionBar = findViewById(R.id.cab_detail);
        customActionBar.setTitle(proDetailResp.getProductTitle());

        tvProductTitle.setText(proDetailResp.getProductTitle());
        tvProductSummary.setText(proDetailResp.getSlogan());


        GlideApp.with(this)
                .load(proDetailResp.getUseImgUrl())
                //.apply(new RequestOptions().placeholder(R.drawable.bg_shape_default))
                .into(ivProductDetailImg);

        videoUrl = proDetailResp.getTutorial().getVideoUrl();

        readyPlayVideo();
    }

    private void readyPlayVideo() {
        if (!TextUtils.isEmpty(videoUrl)) {
            /*HttpProxyCacheServer proxy = HomeApplication.getProxy(this);
            if(!NetworkUtil.isNetworkAvailable(this) && !proxy.isCached(videoUrl)) {
                return;
            }
            rlVideoRoot.setVisibility(View.VISIBLE);
            loadProgress.setVisibility(View.VISIBLE);

            String proxyUrl = proxy.getProxyUrl(videoUrl);

            rlVideoRoot.setVisibility(View.VISIBLE);
            loadProgress.setVisibility(View.VISIBLE);*/
            videoView.setVisibility(View.VISIBLE);
            initVideo(videoUrl);
        }
    }

    private void initVideo(final String url) {
        //timeSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        videoView.setOnCompletionListener(mp -> { });

        videoView.setOnErrorListener((mp, what, extra) -> {
            showToast("播放视频出错");
            //loadProgress.setVisibility(View.GONE);
            return true;
        });

        videoView.setVideoPath(url);
        videoView.setDrawingCacheEnabled(true);
        //isVideoInited = true;
        //TextView tvTotalTime = findViewById(R.id.tv_total_time);
        videoView.setOnPreparedListener(mp -> {
            //if(isWindowOnFocus) {
                mp.setLooping(true);
                int totalTime = videoView.getDuration();//获取视频的总时长
                //tvTotalTime.setText(stringForTime(totalTime));
                // 开始线程，更新进度条的刻度
                /*handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 0);
                timeSeekBar.setMax(videoView.getDuration());*/
                //视频加载完成,准备好播放视频的回调
                videoView.start();
                /*if(btnPlayOrPause.getVisibility() == View.VISIBLE) {
                    timeGone();
                }*/
                mp.setOnInfoListener((mp1, what, extra) -> {
                    //第一帧正式渲染
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        videoView.setBackgroundColor(Color.TRANSPARENT);
                        //loadProgress.setVisibility(View.GONE);
                        ivProductDetailImg.setVisibility(View.GONE);
                    }
                    return true;
                });
            //}
        });
    }

    private boolean handleAlgorithmLib(){
        if (downloadBinder != null) {
            List<AlgorithmInfoResp> algorithmInfoList = viewModel.publishResp.getRelevancyAlgorithmVersions();
            if(viewModel.upgradeResp != null ){
                algorithmInfoList = viewModel.upgradeResp.getRelevancyAlgorithmVersions();//是否应该并集？
            }
            if(algorithmInfoList != null) {
                for (int i = 0; i < algorithmInfoList.size(); i++) {
                    AlgorithmInfoResp algorithmInfoResp = algorithmInfoList.get(i);
                    boolean successHandle = handleModelBin(algorithmInfoResp.relevancyModelVersions);
                    if(!successHandle){
                        return false;
                    }
                    DownloadInfo downloadAlgorithm = CareController.instance.getDownloadInfoByFileId(algorithmInfoResp.versionId);
                    if (downloadAlgorithm == null) {
                        boolean addOk = downloadBinder.startDownload(DownloadHelper.buildAlgorithmDownloadInfo(this, algorithmInfoResp));
                        if (!addOk) {
                            showToast("添加失败");
                            return false;
                        }
                    } else if (downloadAlgorithm.status == DownloadInfo.STATUS_STOPPED) {
                        downloadBinder.startDownload(downloadAlgorithm.fileId);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean handleModelBin(List<ModelInfoResp> relatedModelList){
        if(relatedModelList != null){
            for (int i = 0; i < relatedModelList.size(); i++) {
                ModelInfoResp modelInfoResp = relatedModelList.get(i);
                DownloadInfo downloadModel = CareController.instance.getDownloadInfoByFileId(modelInfoResp.versionId);
                if (downloadModel == null) {
                    boolean addOk = downloadBinder.startDownload(DownloadHelper.buildModelDownloadInfo(this, modelInfoResp));
                    if (!addOk) {
                        showToast("添加失败");
                        return false;
                    }
                } else if (downloadModel.status == DownloadInfo.STATUS_STOPPED) {
                    downloadBinder.startDownload(downloadModel.fileId);
                }
            }
        }
        return true;
    }

    private void handleUpgrade() {
        if (viewModel.upgradeResp.isForcible()) {
            DownloadInfo downloadInfo = DownloadHelper.buildUpgradeDownloadInfo(this, viewModel.proDetailResp, viewModel.publishResp, viewModel.upgradeResp);
            boolean success = downloadBinder.startDownload(downloadInfo);
            if(success){
                viewModel.upgradeResp = null;
            }
        } else {
            showUpgradeDialog();
        }
    }

    private void handleDownload() {
        if (downloadBinder != null) {
            DownloadInfo dbDownloadInfo = CareController.instance.getDownloadInfoByFileId(currentFileId);
            if (dbDownloadInfo == null) {
                DownloadInfo downloadInfo = DownloadHelper.buildDownloadInfo(this, viewModel.proDetailResp, viewModel.publishResp);
                downloadBinder.startDownload(downloadInfo);
            } else {
                if (dbDownloadInfo.status == DownloadInfo.STATUS_LOADING || dbDownloadInfo.status == DownloadInfo.STATUS_PENDING) {
                    downloadBinder.pauseDownload(dbDownloadInfo.fileId);
                } else if (dbDownloadInfo.status == DownloadInfo.STATUS_STOPPED) {
                    downloadBinder.startDownload(dbDownloadInfo);
                } else {
                    /*if(LoadingPluginActivity.lastPluginPackageName != null
                            && !dbDownloadInfo.mainClassPath.equals(LoadingPluginActivity.lastPluginPackageName)){
                        removeRecentTask(LoadingPluginActivity.lastPluginPackageName);
                    }
                    startLoadingApplication();*/
                    startLoadingPlugin(dbDownloadInfo.fileId);
                }
            }
        }
    }

    private void updateDownloadTip(){
        DownloadInfo dbDownloadInfo = CareController.instance.getDownloadInfoByFileId(currentFileId);
        if (dbDownloadInfo == null) {
            /*binding.downloadIcon.setVisibility(View.VISIBLE);
            binding.downloadIcon.setImageResource(R.mipmap.icon_download);
            binding.downloadPro.setText("下载");*/
        }else{
            if (dbDownloadInfo.status == DownloadInfo.STATUS_LOADING) {
                /*binding.downloadIcon.setVisibility(View.VISIBLE);
                binding.downloadIcon.setImageResource(R.mipmap.icon_download_loading);*/
                btnDetailDownload.setText("下载中");
            }else if(dbDownloadInfo.status == DownloadInfo.STATUS_PENDING){
                /*binding.downloadIcon.setVisibility(View.VISIBLE);
                binding.downloadIcon.setImageResource(R.mipmap.icon_download_waiting);*/
                btnDetailDownload.setText("等待中");
            }else if(dbDownloadInfo.status == DownloadInfo.STATUS_STOPPED){
                /*binding.downloadIcon.setVisibility(View.VISIBLE);
                binding.downloadIcon.setImageResource(R.mipmap.icon_download_stop);*/
                btnDetailDownload.setText("继续");
            }else if(dbDownloadInfo.status == DownloadInfo.STATUS_SUCCESS){
                //binding.downloadIcon.setVisibility(View.GONE);
                btnDetailDownload.setText("立即体验");
            }
        }
    }

    private void updateDownloadTipOnFailed(){
        //binding.downloadIcon.setImageResource(R.mipmap.icon_download_stop);
        btnDetailDownload.setText("继续");
    }

    private void updateDownloadTip(int progress){
        if (progress == 100) {
            //binding.downloadIcon.setVisibility(View.GONE);
            btnDetailDownload.setText("立即体验");
        } else {
            /*binding.downloadIcon.setVisibility(View.VISIBLE);
            binding.downloadIcon.setImageResource(R.mipmap.icon_download_loading);*/
            btnDetailDownload.setText(String.format("下载中(%s%%)", progress));
        }
        //binding.gradientProgressViewDetail.setProgress(progress);
    }

    private void showUpgradeDialog() {
        /*final UpgradeTipDialog upgradeDialog = new UpgradeTipDialog(this);
        upgradeDialog.show();
        upgradeDialog.setMessageText("检测到新版本，快来体验吧！");
        upgradeDialog.setPositiveText("立即更新");
        upgradeDialog.setCancelText("继续");
        upgradeDialog.setOnClickListener(new UpgradeTipDialog.OnClickListener() {
            @Override
            public void onConfirm(View v) {}

            @Override
            public void onPositive(View v) {
                upgradeDialog.cancel();
                DownloadInfo downloadInfo = DownloadHelper.buildUpgradeDownloadInfo(DetailActivity.this, viewModel.proDetailResp, viewModel.publishResp, viewModel.upgradeResp);
                boolean success = downloadBinder.startDownload(downloadInfo);
                if(success){
                    viewModel.upgradeResp = null;
                }
            }

            @Override
            public void onCancel(View v) {
                upgradeDialog.cancel();
                handleDownload();
            }
        });*/
    }

    private void startLoadingPlugin(String pluginName) {
        /*Intent intent = new Intent(this, LoadingPluginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(BaseConstants.EXTRA_PLUGIN_NAME, pluginName);
        intent.putExtra(BaseConstants.EXTRA_AUTH_AK_CODE, "akCode");
        intent.putExtra(BaseConstants.EXTRA_AUTH_SK_CODE, "skCode");
        startActivity(intent);*/
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        if (downloadBinder != null) {
            downloadBinder.unRegisterDownloadListener(downloadListener);
            unbindService(serviceConnection);
        }
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
                if (msg.what == MSG_DOWNLOAD_ON_PROGRESS) {
                    int progress = msg.getData().getInt(KEY_PROGRESS);
                    ((DetailActivity) activity).updateDownloadTip(progress);
                }else if(msg.what == MSG_DOWNLOAD_ON_FAILED){
                    ((DetailActivity) activity).updateDownloadTipOnFailed();
                }else if(msg.what == MSG_DOWNLOAD_ON_UPDATE){
                    ((DetailActivity) activity).updateDownloadTip();
                }
            }
        }
    }
}