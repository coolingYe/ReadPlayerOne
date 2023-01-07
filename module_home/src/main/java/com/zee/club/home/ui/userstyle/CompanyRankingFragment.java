package com.zee.club.home.ui.userstyle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.RankingCompanyResp;
import com.zee.club.home.ui.information.LevelTwoActivity;
import com.zee.club.home.ui.information.LevelTwoViewModel;
import com.zee.club.home.ui.userstyle.adapter.CompanyRankingAdapter;
import com.zee.club.home.widget.CardListView;
import com.zeewain.base.ui.BaseFragment;

public class CompanyRankingFragment extends BaseFragment {
    private LevelTwoViewModel mViewModel;
    private CardListView cardListView;
    private LinearLayout layoutFooter;

    private RankingCompanyResp rankingCompanyResp;
    private Boolean isShowSelf = false;

    public static CompanyRankingFragment newInstance() {
        return new CompanyRankingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company_ranking;
    }

    @Override
    protected void initView(View view) {
        mViewModel = new ViewModelProvider(requireActivity()).get(LevelTwoViewModel.class);
        cardListView = view.findViewById(R.id.card_list_power);
        layoutFooter = view.findViewById(R.id.layout_footer);
        cardListView.hideMoreText();
    }

    @Override
    protected void initListener() {
        if (isShowSelf) {
            cardListView.setOnScrollDownListener((v, dy) -> {
//            layoutFooter.startAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.layout_out_anim));
                if (dy > 5)
                    layoutFooter.setVisibility(View.GONE);
            });
            cardListView.setOnScrollBottomListener((v) -> {
                layoutFooter.setVisibility(View.GONE);
            });
            cardListView.setOnScrollTopListener((v, dy) -> {
                if (dy < -5)
                    layoutFooter.setVisibility(View.VISIBLE);
            });
        }
    }

    @Override
    protected void initData() {
        rankingCompanyResp = (RankingCompanyResp) getArguments().getSerializable(LevelTwoActivity.TYPE_PAGE_POWER);
        if (rankingCompanyResp != null) {
            cardListView.setAdapter(new CompanyRankingAdapter(rankingCompanyResp.getRankingList()));
            if (rankingCompanyResp.getSelfRanking() != null && rankingCompanyResp.getSelfRanking().size() > 0) {
                isShowSelf = true;
                layoutFooter.setVisibility(View.VISIBLE);
                for (int i = 0; i < rankingCompanyResp.getSelfRanking().size(); i++) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_card_list_power_ranking, layoutFooter, false);
                    ImageView imageView = view.findViewById(R.id.iv_company_top);
                    TextView companyTopText = view.findViewById(R.id.tv_company_top);
                    TextView companyText = view.findViewById(R.id.tv_company_name);
                    TextView powerNumber = view.findViewById(R.id.tv_power_value);
                    ProgressBar progressBar = view.findViewById(R.id.progress_power);

                    companyTopText.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    companyTopText.setText(String.valueOf(rankingCompanyResp.getSelfRanking().get(i).getRank()));
                    companyText.setText(rankingCompanyResp.getSelfRanking().get(i).getEnterpriseName());
                    progressBar.setProgress(rankingCompanyResp.getSelfRanking().get(i).getEnergyScore());
                    powerNumber.setText(String.valueOf(rankingCompanyResp.getSelfRanking().get(i).getEnergyScore()));

                    layoutFooter.addView(view);
                }
            }
        }
    }
}
