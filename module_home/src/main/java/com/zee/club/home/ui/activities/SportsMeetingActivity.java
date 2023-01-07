package com.zee.club.home.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zee.club.home.R;
import com.zee.club.home.app.ViewModelFactory;
import com.zee.club.home.data.protocol.request.ActivityRankingReq;
import com.zee.club.home.data.protocol.request.AppInfoSoftwareCodeReq;
import com.zee.club.home.data.protocol.response.ActivityInfoResp;
import com.zee.club.home.ui.activities.adapter.SportsMeetingAppAdapter;
import com.zee.club.home.ui.activities.adapter.SportsMeetingRankingAdapter;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class SportsMeetingActivity extends BaseActivity {

    private SportsMeetingViewModel mViewModel;
    private ActivityInfoResp activityInfoResp;
    private RecyclerView recyclerViewRanking;
    private RecyclerView recyclerViewApp;
    private SportsMeetingRankingAdapter sportsMeetingRankingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(SportsMeetingViewModel.class);
        setContentView(R.layout.activity_sports_meeting);

        Bundle bundle = getIntent().getBundleExtra(BaseConstants.EXTRA_ACTIVITIES_INFO);
        if(bundle != null){
            activityInfoResp = (ActivityInfoResp) bundle.getSerializable(BaseConstants.EXTRA_ACTIVITIES_INFO);
        }else{
            finish();
            return;
        }

        initView();
        initViewObservable();
        mViewModel.reqActivityRanking(new ActivityRankingReq(activityInfoResp.getActivityId(), 3));
        List<String> softwareCodesList = new ArrayList<>();
        for(int i=0; i<activityInfoResp.getActivityAppJson().size(); i++){
            softwareCodesList.add(activityInfoResp.getActivityAppJson().get(i).getSoftwareCode());
        }
        AppInfoSoftwareCodeReq appInfoSoftwareCodeReq = new AppInfoSoftwareCodeReq(softwareCodesList);
        mViewModel.reqAppInfoListBySoftwareCodes(appInfoSoftwareCodeReq);
    }

    private void initView(){
        recyclerViewRanking = findViewById(R.id.recycler_view_sports_meeting_ranking);
        sportsMeetingRankingAdapter = new SportsMeetingRankingAdapter(new ArrayList<>());
        recyclerViewRanking.setAdapter(sportsMeetingRankingAdapter);

        recyclerViewApp = findViewById(R.id.recycler_view_sports_meeting_app);
    }

    private void initViewObservable() {
        mViewModel.mldActivityRankingLoadState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Success == loadState) {
                    if(mViewModel.activityRankingResp.getRankingTopN() != null)
                        sportsMeetingRankingAdapter.setDataList(mViewModel.activityRankingResp.getRankingTopN());
                }
            }
        });

        mViewModel.mldSportsAppInfoLoadState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Success == loadState) {
                    recyclerViewApp.setAdapter(new SportsMeetingAppAdapter(mViewModel.sportsAppInfoList));
                }
            }
        });
    }
}