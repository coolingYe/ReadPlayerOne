package com.zee.club.home.ui.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zee.club.home.R;
import com.zee.club.home.app.ViewModelFactory;
import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.ui.activities.adapter.ActivitiesSkillsAdapter;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseFragment;
import com.zeewain.base.utils.CommonUtils;

import java.io.Serializable;

public class ClubActivitiesFragment extends BaseFragment {

    private ClubActivitiesViewModel mViewModel;
    private RecyclerView recyclerViewSkills;
    private ImageView ivSports, ivCultural, ivSkills, ivSportsInProgress;

    public static ClubActivitiesFragment newInstance() {
        return new ClubActivitiesFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_club_activities;
    }

    @Override
    protected void initView(View view) {
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ClubActivitiesViewModel.class);

        recyclerViewSkills = view.findViewById(R.id.recycler_view_activities_skills);
        recyclerViewSkills.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        ivSports = view.findViewById(R.id.iv_activities_sports);
        ivSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.checkActivityInfo();
            }
        });
        ivSportsInProgress = view.findViewById(R.id.iv_activities_sports_in_progress);

        ivCultural = view.findViewById(R.id.iv_activities_cultural);
        ivCultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivSkills = view.findViewById(R.id.iv_activities_skills);
        ivSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.initNetData();
    }

    @Override
    protected void initData() {
        mViewModel.mldInitDataLoadState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Success == loadState){
                    recyclerViewSkills.setAdapter(new ActivitiesSkillsAdapter(mViewModel.skillsAppInfoList));
                    if(mViewModel.activityInfoResp != null){
                        if(mViewModel.activityInfoResp.getStatus() == ProdConstants.ActivitiesStatus.IN_PROGRESS){
                            ivSportsInProgress.setVisibility(View.VISIBLE);
                        }else{
                            ivSportsInProgress.setVisibility(View.GONE);
                        }
                    }
                }else if(LoadState.Loading == loadState){

                }
            }
        });

        mViewModel.mldActivityInfoCheckLoadState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Success == loadState){
                    handleActivityInfoCheck();
                }
            }
        });
    }


    private void handleActivityInfoCheck(){
        if(mViewModel.activityInfoResp != null){
            if(mViewModel.activityInfoResp.getStatus() == ProdConstants.ActivitiesStatus.IN_PROGRESS){
                nextToSportsMeetingActivity();
                //nextToPosterActivity();
            }else if(mViewModel.activityInfoResp.getStatus() == ProdConstants.ActivitiesStatus.NOT_STARTED){
                nextToPosterActivity();
            }else{
                nextToSportsMeetingActivity();
            }
        }else{
            if(CommonUtils.isCommonUser()){

            }else{
                nextToPublishActivity();
            }
        }
    }

    private void nextToSportsMeetingActivity(){
        if(getActivity() != null && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), SportsMeetingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(BaseConstants.EXTRA_ACTIVITIES_INFO, (Serializable)mViewModel.activityInfoResp);
            intent.putExtra(BaseConstants.EXTRA_ACTIVITIES_INFO, bundle);
            getActivity().startActivity(intent);
        }
    }

    private void nextToPosterActivity(){
        if(getActivity() != null && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ActivitiesPosterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(BaseConstants.EXTRA_ACTIVITIES_INFO, (Serializable)mViewModel.activityInfoResp);
            intent.putExtra(BaseConstants.EXTRA_ACTIVITIES_INFO, bundle);
            getActivity().startActivity(intent);
        }
    }

    private void nextToPublishActivity(){
        if(getActivity() != null && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ActivitiesPublishActivity.class);
            getActivity().startActivity(intent);
        }
    }

}


