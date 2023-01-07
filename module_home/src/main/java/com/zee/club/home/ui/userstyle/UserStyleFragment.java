package com.zee.club.home.ui.userstyle;

import static com.zee.club.home.config.ProdConstants.ModuleCode.COMPANY_THRILLING_INFORMATION;
import static com.zee.club.home.config.ProdConstants.ModuleCode.WIN_BID_INFORMATION;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.data.protocol.response.RankingCompanyResp;
import com.zee.club.home.ui.information.LevelTwoActivity;
import com.zee.club.home.ui.information.adapter.BannerAdapter;
import com.zee.club.home.ui.userstyle.adapter.CompanyRankingAdapter;
import com.zee.club.home.ui.userstyle.adapter.PersonRankingAdapter;
import com.zee.club.home.ui.userstyle.adapter.UserStyleArticleAdapter;
import com.zee.club.home.widget.CardListView;
import com.zeewain.base.ui.BaseFragment;
import com.zeewain.base.utils.DisplayUtil;

import java.io.Serializable;
import java.util.List;

public class UserStyleFragment extends BaseFragment {

    private Banner banner;
    private CardListView cardPower, cardPerson, cardWinBid, cardCompanyThrilling;
    private UserStyleViewModel mViewModel;

    public static UserStyleFragment newInstance() {
        return new UserStyleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_style;
    }

    @Override
    protected void initView(View view) {
        mViewModel = new ViewModelProvider(this).get(UserStyleViewModel.class);

        banner = view.findViewById(R.id.banner_user_style);
        cardPower = view.findViewById(R.id.card_list_power);
        cardPerson = view.findViewById(R.id.card_list_person);
        cardWinBid = view.findViewById(R.id.card_list_win_bid);
        cardCompanyThrilling = view.findViewById(R.id.card_list_company_thrilling);
        ConstraintLayout layoutFooter = view.findViewById(R.id.footer_view);
        layoutFooter.setOnClickListener(v -> ((NestedScrollView)view.findViewById(R.id.nsv_about_club)).fullScroll(View.FOCUS_UP));

        banner.setIndicator(new CircleIndicator(view.getContext()));
        banner.setIndicatorWidth(DisplayUtil.dip2px(view.getContext(), 8), DisplayUtil.dip2px(view.getContext(), 8));
        banner.setIndicatorHeight(DisplayUtil.dip2px(view.getContext(), 8));
        banner.setIndicatorNormalColor(0xFFE1E1E1);
        banner.setIndicatorSelectedColor(0xFFFFC524);
        banner.setIndicatorMargins(new IndicatorConfig.Margins(DisplayUtil.dip2px(view.getContext(), 8)));
        banner.setBannerGalleryEffect(8, 8, 8, 1f);

        cardPerson.setRecyclerViewHorizontal();
        cardPerson.setPaddingRecyclerView();
    }

    @Override
    protected void initListener() {
        cardPower.setOnMoreClickListener(v -> {
            navigationToLevelTwoPage(v, LevelTwoActivity.TYPE_PAGE_POWER, true);
        });
        cardPerson.setOnMoreClickListener(v -> {
            navigationToLevelTwoPage(v, LevelTwoActivity.TYPE_PAGE_PERSON, false);
        });
        cardWinBid.setOnMoreClickListener(v -> {
            navigationToLevelTwoPage(v, WIN_BID_INFORMATION);
        });
        cardCompanyThrilling.setOnMoreClickListener(v -> {
            navigationToLevelTwoPage(v, COMPANY_THRILLING_INFORMATION);
        });
    }

    @Override
    protected void initData() {
        mViewModel.initData();
        mViewModel.bannerList.observe(getViewLifecycleOwner(), articleInfoResps -> {
            banner.setAdapter(new BannerAdapter(articleInfoResps));
        });
        mViewModel.rankingCompanyResp.observe(getViewLifecycleOwner(), rankingCompanyResp -> {
            List<RankingCompanyResp.RankingCompany> list = rankingCompanyResp.getRankingList().size() >= 3 ? rankingCompanyResp.getRankingList().subList(0, 3) : rankingCompanyResp.getRankingList();
            cardPower.setAdapter(new CompanyRankingAdapter(list));
        });
        mViewModel.rankingPeopleResp.observe(getViewLifecycleOwner(), rankingPeopleResp -> {
            cardPerson.setAdapter(new PersonRankingAdapter(rankingPeopleResp.getRankingList(), PersonRankingAdapter.TYPE_PERSON_ONE));
        });
        mViewModel.winBidList.observe(getViewLifecycleOwner(), articleInfoResps -> {
            List<ArticleInfoResp> list = articleInfoResps.size() > 3 ? articleInfoResps.subList(0, 3) : articleInfoResps;
            cardWinBid.setAdapter(new UserStyleArticleAdapter(list, UserStyleArticleAdapter.TYPE_INFO_ONE));
        });
        mViewModel.thrillingList.observe(getViewLifecycleOwner(), articleInfoResps -> {
            List<ArticleInfoResp> list = articleInfoResps.size() > 5 ? articleInfoResps.subList(0, 5) : articleInfoResps;
            cardCompanyThrilling.setAdapter(new UserStyleArticleAdapter(list, UserStyleArticleAdapter.TYPE_INFO_TWO));
        });
    }

    private void navigationToLevelTwoPage(View view, String pageTo) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(pageTo, (Serializable) mViewModel.getDataMapping(pageTo));
        intent.putExtra(pageTo, bundle);
        intent.putExtra(LevelTwoActivity.TYPE_PAGE_FROM, pageTo);
        intent.setClass(view.getContext(), LevelTwoActivity.class);
        startActivity(intent);
    }

    private void navigationToLevelTwoPage(View view, String pageTo, Boolean isCompanyRanking) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (isCompanyRanking) {
            bundle.putSerializable(pageTo, mViewModel.rankingCompanyResp.getValue());
        } else bundle.putSerializable(pageTo, mViewModel.rankingPeopleResp.getValue());
        intent.putExtra(pageTo, bundle);
        intent.putExtra(LevelTwoActivity.TYPE_PAGE_FROM, pageTo);
        intent.setClass(view.getContext(), LevelTwoActivity.class);
        startActivity(intent);
    }
}
