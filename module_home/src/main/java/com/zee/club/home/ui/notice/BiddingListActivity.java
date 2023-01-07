package com.zee.club.home.ui.notice;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.app.ViewModelFactory;
import com.zee.club.home.data.model.BiddingCategoryMo;
import com.zee.club.home.ui.notice.adapter.BiddingCategoryAdapter;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.model.DataLoadState;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BiddingListActivity extends BaseActivity {
    private BiddingListViewModel mViewModel;
    private int currentType = 0;
    private RecyclerView recyclerViewBiddingCategory;
    private RecyclerView recyclerViewBiddingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(BiddingListViewModel.class);
        setContentView(R.layout.activity_bidding_list);


        recyclerViewBiddingCategory = findViewById(R.id.recycler_view_bidding_category);
        recyclerViewBiddingCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBiddingList = findViewById(R.id.recycler_view_bidding_list);

        currentType = getIntent().getIntExtra(BaseConstants.EXTRA_BIDDING_LIST_TYPE, 0);

        if(currentType > 0){
            recyclerViewBiddingCategory.setVisibility(View.GONE);
        }else{
            List<BiddingCategoryMo> biddingCategoryMoList = new ArrayList<>();
            biddingCategoryMoList.add(new BiddingCategoryMo(R.mipmap.bidding_icon_notice, "招标公告"));
            biddingCategoryMoList.add(new BiddingCategoryMo(R.mipmap.bidding_icon_preview, "招标预告"));
            biddingCategoryMoList.add(new BiddingCategoryMo(R.mipmap.bidding_icon_flow, "招标流程"));
            biddingCategoryMoList.add(new BiddingCategoryMo(R.mipmap.bidding_icon_manage, "招标管理"));
            biddingCategoryMoList.add(new BiddingCategoryMo(R.mipmap.bidding_icon_info, "招标信息"));
            BiddingCategoryAdapter biddingCategoryAdapter = new BiddingCategoryAdapter(biddingCategoryMoList);
            biddingCategoryAdapter.setOnItemSelectedListener(new BiddingCategoryAdapter.OnItemSelectedListener() {
                @Override
                public void onItemSelected(BiddingCategoryMo data, int position) {
                    biddingCategoryAdapter.setSelectedIndex(position);
                }
            });
            recyclerViewBiddingCategory.setAdapter(biddingCategoryAdapter);

        }

        initViewObservable();
        mViewModel.reqArticleList(1005);
    }

    private void initViewObservable() {
        /*mViewModel.mldArticleListLoadState.observe(this, new Observer<DataLoadState<Integer>>() {
            @Override
            public void onChanged(DataLoadState<Integer> dataLoadState) {
                if(LoadState.Success == dataLoadState.loadState && currentModuleCode.equals(dataLoadState.data)){
                    classicAppListAdapter.setDataList(mViewModel.getAppInfoListFromCache(currentModuleCode));
                }
            }
        });*/
    }
}