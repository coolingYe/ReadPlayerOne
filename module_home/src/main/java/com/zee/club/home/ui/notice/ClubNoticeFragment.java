package com.zee.club.home.ui.notice;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.zee.club.home.R;
import com.zee.club.home.app.ViewModelFactory;
import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.adapter.ClassicBannerAdapter;
import com.zee.club.home.ui.notice.adapter.BiddingCardListViewAdapter;
import com.zee.club.home.ui.notice.adapter.IndustryCardListViewAdapter;
import com.zee.club.home.ui.notice.adapter.OwnerCardListViewAdapter;
import com.zee.club.home.widget.CardListView;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.utils.DisplayUtil;

import java.io.Serializable;


public class ClubNoticeFragment extends Fragment {
    private Banner<ArticleInfoResp, ClassicBannerAdapter> banner;
    private ClubNoticeViewModel mViewModel;
    private CardListView<ArticleInfoResp, IndustryCardListViewAdapter> cardListViewIndustry;
    private CardListView<ArticleInfoResp, OwnerCardListViewAdapter> cardListViewOwner;
    private CardListView<ArticleInfoResp, BiddingCardListViewAdapter> cardListViewBidding;

    public static ClubNoticeFragment newInstance() {
        return new ClubNoticeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_club_notice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ClubNoticeViewModel.class);
        initData(view);
        initViewObservable();

        mViewModel.initData();
    }

    private void initData(View view){
        banner = view.findViewById(R.id.banner_about_club);
        banner.addBannerLifecycleObserver(getViewLifecycleOwner());

        banner.setIndicator(new CircleIndicator(view.getContext()));
        banner.setIndicatorWidth(DisplayUtil.dip2px(view.getContext(), 8), DisplayUtil.dip2px(view.getContext(), 8));
        banner.setIndicatorHeight(DisplayUtil.dip2px(view.getContext(), 8));
        banner.setIndicatorNormalColor(0xFFE1E1E1);
        banner.setIndicatorSelectedColor(0xFFFFC524);
        banner.setIndicatorMargins(new IndicatorConfig.Margins(DisplayUtil.dip2px(view.getContext(), 8)));
        banner.setBannerGalleryEffect(8, 8, 8, 1f);

        cardListViewIndustry = view.findViewById(R.id.card_list_notice_industry);
        cardListViewIndustry.setRecyclerViewHorizontal();

        cardListViewOwner = view.findViewById(R.id.card_list_notice_owner);
        cardListViewOwner.setItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        cardListViewOwner.setOnMoreClickListener(new CardListView.OnMoreClickListener() {
            @Override
            public void onMoreClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BaseConstants.EXTRA_OWNER_NOTICE_LIST, (Serializable)mViewModel.getArticleInfoListFromCache(ProdConstants.ModuleCode.ASSOCIATION_INFORM));
                intent.putExtra(BaseConstants.EXTRA_OWNER_NOTICE_LIST, bundle);
                intent.setClass(v.getContext(), OwnerNoticeActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        cardListViewBidding = view.findViewById(R.id.card_list_notice_bidding);
        cardListViewBidding.setItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        cardListViewBidding.setOnMoreClickListener(new CardListView.OnMoreClickListener() {
            @Override
            public void onMoreClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), BiddingListActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void initViewObservable() {
        mViewModel.mldInitDataLoadState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Success == loadState){
                    banner.setAdapter(new ClassicBannerAdapter(mViewModel.getArticleInfoListFromCache(ProdConstants.ModuleCode.ASSOCIATION_MESSAGE_TOP)));
                    cardListViewIndustry.setAdapter(new IndustryCardListViewAdapter(mViewModel.getArticleInfoListFromCache(ProdConstants.ModuleCode.INDUSTRY_ASSOCIATION)));
                    cardListViewOwner.setAdapter(new OwnerCardListViewAdapter(mViewModel.getArticleInfoListFromCache(ProdConstants.ModuleCode.ASSOCIATION_INFORM)));
                    cardListViewBidding.setAdapter(new BiddingCardListViewAdapter(mViewModel.getArticleInfoListFromCache(ProdConstants.ModuleCode.BID_INFORMATION)));
                }
            }
        });
    }

}