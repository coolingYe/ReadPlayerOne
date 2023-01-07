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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.ui.adapter.GatherAdapter;
import com.zeewain.base.model.LoadState;

public class GatherFragment extends Fragment {

    private GatherViewModel mViewModel;
    private RecyclerView rvGather;

    public static GatherFragment newInstance(String GatherType) {
        Bundle bundle = new Bundle();
        bundle.putString(UserConstants.Gather_Type, GatherType);
        GatherFragment fragment = new GatherFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(GatherViewModel.class);
        initViewObservable();
        initView(view);

        mViewModel.reqListData(getArguments().getString(UserConstants.Gather_Type));
    }


    private void initView(View view) {
        rvGather = view.findViewById(R.id.rv_gather);
        rvGather.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

    }

    public void initViewObservable() {
        mViewModel.mBrowseLoadState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    String key = getArguments().getString(UserConstants.Gather_Type);
                    rvGather.setAdapter(new GatherAdapter(mViewModel.gatherInfoListMap.get(key), key));
                }

            }
        });


        mViewModel.mThumbsState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    String key = getArguments().getString(UserConstants.Gather_Type);
                    rvGather.setAdapter(new GatherAdapter(mViewModel.gatherInfoListMap.get(key), key));
                }
            }
        });


        mViewModel.mCollectLoadState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    String key = getArguments().getString(UserConstants.Gather_Type);
                    rvGather.setAdapter(new GatherAdapter(mViewModel.gatherInfoListMap.get(key), key));
                }

            }
        });

    }

}