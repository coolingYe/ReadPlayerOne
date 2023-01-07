package com.zee.club.home.ui.information;

import static com.zee.club.home.config.ProdConstants.InfoCategoryMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.ui.article.ArticleDetailActivity;
import com.zee.club.home.ui.information.adapter.InformationCategoryAdapter;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseFragment;

import java.util.ArrayList;

public class InformationCategoryFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private InformationViewModel mViewModel;
    private InformationCategoryAdapter mAdapter;

    public static final String TYPE_PAGE = "type_page";

    public static InformationCategoryFragment newInstance() {
        return new InformationCategoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_information;
    }

    @Override
    protected void initView(View view) {
        mViewModel = new ViewModelProvider(requireActivity()).get(InformationViewModel.class);
        recyclerView = view.findViewById(R.id.rv_card_list);
        mAdapter = new InformationCategoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickItemListener((view1, data) -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("ArticleInfo", data);
            intent.putExtra("ArticleInfo", bundle);
            intent.setClass(view.getContext(), ArticleDetailActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        assert getArguments() != null;
        String column = getArguments().getString(TYPE_PAGE);
        mViewModel.getRelatedInformationData(InfoCategoryMap.get(column));
        mViewModel.mldInitDataLoadStateColumn.observe(getViewLifecycleOwner(), loadState -> {
            if (loadState == LoadState.Success) {
                mAdapter.setData(mViewModel.columnListMap.get(String.valueOf(InfoCategoryMap.get(column))));
                mAdapter.isShowFooterView(false);
            }
        });
    }
}
