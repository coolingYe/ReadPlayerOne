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
import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.response.GatherInfoResp;
import com.zee.club.user.ui.adapter.ActiveAdapter;
import com.zee.club.user.ui.adapter.NoticeMegAdapter;
import com.zeewain.base.model.LoadState;

public class MessageFragment extends Fragment {

    private MessageCenterViewModel mViewModel;
    private RecyclerView mMessageRv;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity(), UserViewModelFactory.getInstance()).get(MessageCenterViewModel.class);
        initView(view);
        initViewObservable();
        mViewModel.reqGetNoticeList();
    }


    private void initView(View view) {
        mMessageRv = view.findViewById(R.id.rv_active);
        mMessageRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void initViewObservable() {
        mViewModel.mNoticeState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if (loadState == LoadState.Success) {
                    mMessageRv.setAdapter(new NoticeMegAdapter(mViewModel.noticeRespList));
                }
            }
        });
    }

}