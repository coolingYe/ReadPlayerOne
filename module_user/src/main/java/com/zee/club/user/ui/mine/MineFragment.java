package com.zee.club.user.ui.mine;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.protocol.response.PersonEnergyResp;
import com.zee.club.user.data.protocol.response.UserInfoResp;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.OnDrawerInteractionListener;
import com.zeewain.base.utils.GlideApp;
import com.zeewain.base.utils.SPUtils;

public class MineFragment extends Fragment {

    private MineViewModel mViewModel;
    private OnDrawerInteractionListener onDrawerInteractionListener;
    private TextView tvUserName;
    private TextView tvMineTodayContribution;
    private TextView tvMineWeekContribution;
    private TextView tvMineMonthContribution;
    private TextView tvInteractionMore;
    private ImageView ivUserHead;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDrawerInteractionListener) {
            onDrawerInteractionListener = (OnDrawerInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDrawerInteractionListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(MineViewModel.class);
        initView(view);
        initViewObservable();
        mViewModel.reqGetUserInfo();
        mViewModel.reqGetPersonEnergy();
    }


    private void initView(View view) {
        tvMineTodayContribution = view.findViewById(R.id.tv_mine_today_con);
        tvMineWeekContribution = view.findViewById(R.id.tv_mine_week_con);
        tvMineMonthContribution = view.findViewById(R.id.tv_mine_month_con);
        ivUserHead = view.findViewById(R.id.iv_user_head);
        ivUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDrawerInteractionListener.drawerOpen();
            }
        });
        tvInteractionMore = view.findViewById(R.id.tv_interaction_more);
        LinearLayout llMineUserInfo = view.findViewById(R.id.ll_mine_user_info);
        llMineUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });


        tvInteractionMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InteractionActivity.class);
                startActivity(intent);
            }
        });


        LinearLayout linearLayout = view.findViewById(R.id.ll_liulan);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GatherActivity.class);
                startActivity(intent);
            }
        });

        ImageView ivMessage = view.findViewById(R.id.iv_message);
        tvUserName = view.findViewById(R.id.tv_user_name);
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessageCenterActivity.class);
                intent.putExtra(UserConstants.USER_TYPE, mViewModel.mUserInfoResp.getValue().getUserType());
                startActivity(intent);
            }
        });


    }

    public void initViewObservable() {
        mViewModel.mldImageCaptchaState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
            }
        });
        mViewModel.mUserInfoResp.observe(getViewLifecycleOwner(), new Observer<UserInfoResp>() {
            @Override
            public void onChanged(UserInfoResp userInfoResp) {
                tvUserName.setText(userInfoResp.getUserCode());
                GlideApp.with(MineFragment.this)
                        .load(userInfoResp.getPic())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .error(R.mipmap.image_examime_head)
                        .into(ivUserHead);
                SPUtils.getInstance().put(SharePrefer.UserType,userInfoResp.getUserType());
            }
        });
        mViewModel.mPersonEnergyResp.observe(getViewLifecycleOwner(), new Observer<PersonEnergyResp>() {
            @Override
            public void onChanged(PersonEnergyResp personEnergyResp) {
                tvMineMonthContribution.setText(String.valueOf(personEnergyResp.getMonthScore()));
                tvMineWeekContribution.setText(String.valueOf(personEnergyResp.getWeekScore()));
                tvMineTodayContribution.setText(String.valueOf(personEnergyResp.getTodayScore()));
            }
        });
    }

    public void reLoadData() {
        mViewModel.reqGetPersonEnergy();
    }

}