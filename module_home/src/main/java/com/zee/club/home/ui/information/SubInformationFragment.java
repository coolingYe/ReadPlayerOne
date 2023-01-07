package com.zee.club.home.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.article.ArticleDetailActivity;
import com.zee.club.home.ui.information.adapter.InformationCategoryAdapter;
import com.zeewain.base.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class SubInformationFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private InformationCategoryAdapter mAdapter;
    private LevelTwoViewModel mViewModel;

    public String pageFrom = "";
    private List<ArticleInfoResp> list = new ArrayList<>();

    public static SubInformationFragment newInstance() {
        return new SubInformationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_information;
    }

    @Override
    protected void initView(View view) {
        mViewModel = new ViewModelProvider(requireActivity()).get(LevelTwoViewModel.class);
        recyclerView = view.findViewById(R.id.rv_card_list);
        mAdapter = new InformationCategoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnClickItemListener((view, data) -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("ArticleInfo", data);
            intent.putExtra("ArticleInfo", bundle);
            intent.setClass(view.getContext(), ArticleDetailActivity.class);
            view.getContext().startActivity(intent);
        });

        mAdapter.setOnClickFooterItemListener(view -> recyclerView.scrollToPosition(0));
    }

    @Override
    protected void initData() {
        list = (List<ArticleInfoResp>) getArguments().get(pageFrom);
        if (list != null && list.size() > 0) {
            mAdapter.setData(list);
            mAdapter.isShowFooterView(true);
        }
    }
}
