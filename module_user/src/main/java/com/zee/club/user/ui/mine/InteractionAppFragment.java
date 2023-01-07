package com.zee.club.user.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.data.protocol.response.UserAppInfoResp;
import com.zee.club.user.ui.adapter.UserAppInfoAdapter;
import com.zeewain.base.model.LoadState;

public class InteractionAppFragment extends Fragment {

    private InteractionViewModel mViewModel;
    private RecyclerView rvApp;

    public static InteractionAppFragment newInstance() {
        return new InteractionAppFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity(), UserViewModelFactory.getInstance()).get(InteractionViewModel.class);
        initViewObservable();
        initView(view);
        rvApp.setBackgroundResource(R.drawable.shape_interaction_bg);
        mViewModel.userAppInfoRespList.add(new UserAppInfoResp());
        mViewModel.userAppInfoRespList.add(new UserAppInfoResp());
        mViewModel.userAppInfoRespList.add(new UserAppInfoResp());
        mViewModel.userAppInfoRespList.add(new UserAppInfoResp());
        mViewModel.mInteractState.setValue(LoadState.Success);
    }

    private void initView(View view) {
        rvApp = view.findViewById(R.id.rv_active);
        rvApp.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
    }

    public void initViewObservable() {
        mViewModel.mInteractState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                rvApp.setAdapter(new UserAppInfoAdapter(mViewModel.userAppInfoRespList));
            }
        });
    }

}