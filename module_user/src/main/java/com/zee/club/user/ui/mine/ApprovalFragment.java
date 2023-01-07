package com.zee.club.user.ui.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.zee.club.user.ui.adapter.ApprovalAdapter;
import com.zee.club.user.ui.adapter.GatherAdapter;
import com.zee.club.user.ui.view.TypeSelectWindow;
import com.zeewain.base.model.LoadState;

public class ApprovalFragment extends Fragment {

    private MessageCenterViewModel mViewModel;
    TypeSelectWindow joinTypeSelectWindow;
    private RecyclerView rvApproval;
    private String approvalStatue = "0";

    public static ApprovalFragment newInstance() {
        return new ApprovalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_approval, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity(), UserViewModelFactory.getInstance()).get(MessageCenterViewModel.class);
        initViewObservable();
        initView(view);
        mViewModel.reqGetApprovalList(approvalStatue);
    }


    private void initView(View view) {
        rvApproval = view.findViewById(R.id.rv_approval);
        LinearLayout llSelect = view.findViewById(R.id.ll_select);
        TextView tvType = view.findViewById(R.id.tv_type);
        joinTypeSelectWindow = new TypeSelectWindow(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = (String) view.getTag();
                String[] itemData = data.split(",");
                tvType.setText(itemData[0]);
                approvalStatue = itemData[1];
                joinTypeSelectWindow.dismissPopup();
                mViewModel.reqGetApprovalList(approvalStatue);
            }
        });
        llSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinTypeSelectWindow.showPopupWindow(llSelect);
            }
        });

        rvApproval.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void initViewObservable() {
        mViewModel.mApprovalState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if (loadState == LoadState.Success) {
                    rvApproval.setAdapter(new ApprovalAdapter(mViewModel.approvalRespList));
                }
            }
        });
    }

}