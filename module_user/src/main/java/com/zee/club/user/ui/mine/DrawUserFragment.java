package com.zee.club.user.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.data.protocol.response.AssociationResp;
import com.zee.club.user.data.protocol.response.UserInfoResp;
import com.zee.club.user.ui.adapter.DrawCommAdapter;
import com.zee.club.user.ui.bind.BindCommunityActivity;
import com.zee.club.user.ui.login.CommunitySetlActivity;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.OnDrawerInteractionListener;
import com.zeewain.base.utils.GlideApp;
import com.zeewain.base.utils.SPUtils;
import com.zeewain.base.utils.ToastUtils;

public class DrawUserFragment extends Fragment {

    private DrawUserViewModel mViewModel;
    private OnDrawerInteractionListener onDrawerInteractionListener;
    private ImageView ivUserHead;
    private TextView tvUserName;
    private TextView tvDrawAutograph;
    private RecyclerView rlDrawCommunity;
    private LinearLayout llDrawCreateComm;
    private LinearLayout llDrawAddComm;
    private LinearLayout llDrawHelp;
    private DrawCommAdapter adapter;

    public static DrawUserFragment newInstance() {
        return new DrawUserFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDrawerInteractionListener) {
            onDrawerInteractionListener = (OnDrawerInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDrawerInteractionListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_draw_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(DrawUserViewModel.class);
        initView(view);
        initViewObservable();
        mViewModel.reqGetUserInfo();
        mViewModel.reqGetAssociationList();
    }


    private void initView(View view) {
        tvUserName = view.findViewById(R.id.tv_user_name);
        ivUserHead = view.findViewById(R.id.iv_user_head);
        tvDrawAutograph = (TextView) view.findViewById(R.id.tv_draw_autograph);
        rlDrawCommunity = (RecyclerView) view.findViewById(R.id.rl_draw_community);
        llDrawCreateComm = (LinearLayout) view.findViewById(R.id.ll_draw_create_comm);
        llDrawCreateComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommunitySetlActivity.class);
                startActivity(intent);
            }
        });
        llDrawAddComm = (LinearLayout) view.findViewById(R.id.ll_draw_add_comm);
        llDrawAddComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BindCommunityActivity.class);
                startActivity(intent);
            }
        });
        llDrawHelp = (LinearLayout) view.findViewById(R.id.ll_draw_help);
        llDrawHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("敬请期待");
            }
        });
    }

    public void initViewObservable() {
        mViewModel.mUserInfoResp.observe(getViewLifecycleOwner(), new Observer<UserInfoResp>() {
            @Override
            public void onChanged(UserInfoResp userInfoResp) {
                tvUserName.setText(userInfoResp.getUserCode());
                GlideApp.with(DrawUserFragment.this)
                        .load(userInfoResp.getPic())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .error(R.mipmap.image_examime_head)
                        .into(ivUserHead);
            }
        });
        mViewModel.mAssociationLoadState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (mViewModel.mAssociationRespList.size() > 0) {
                    int communityId = SPUtils.getInstance().getInt(SharePrefer.associationId);
                    for (AssociationResp item : mViewModel.mAssociationRespList) {
                        if (item.getAssociationId() == communityId) {
                            item.setSelected(true);
                            break;
                        }
                    }
                }
                adapter = new DrawCommAdapter(mViewModel.mAssociationRespList, new DrawCommAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int pos, AssociationResp data) {
                        if (data.isSelected()) return;
                        for (AssociationResp item : adapter.getData()) {
                            item.setSelected(false);
                        }
                        data.setSelected(true);
                        adapter.notifyDataSetChanged();
                        SPUtils.getInstance().put(SharePrefer.associationId, data.getAssociationId());
                        onDrawerInteractionListener.drawerOpen();
                    }
                });
                rlDrawCommunity.setAdapter(adapter);
            }
        });
    }
}