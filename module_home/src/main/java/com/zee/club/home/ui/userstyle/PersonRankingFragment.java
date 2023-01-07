package com.zee.club.home.ui.userstyle;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.RankingPeopleResp;
import com.zee.club.home.ui.information.LevelTwoActivity;
import com.zee.club.home.ui.information.LevelTwoViewModel;
import com.zee.club.home.ui.userstyle.adapter.PersonRankingAdapter;
import com.zee.club.home.widget.GreatPersonView;
import com.zeewain.base.ui.BaseFragment;

import java.util.Collections;
import java.util.List;

public class PersonRankingFragment extends BaseFragment {
    private LevelTwoViewModel mViewModel;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private ImageView ivAvatar;
    private TextView tvPersonName, tvCompanyName, tvPowerValue, tvTopNumber;
    private GreatPersonView greatPersonView1, greatPersonView2, greatPersonView3;

    private Boolean isShowSelf = false;

    private RankingPeopleResp rankingPeopleResp = null;

    public static PersonRankingFragment newInstance() {
        return new PersonRankingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_character_ranking;
    }

    @SuppressLint("CutPasteId")
    @Override
    protected void initView(View view) {
        mViewModel = new ViewModelProvider(requireActivity()).get(LevelTwoViewModel.class);
        recyclerView = view.findViewById(R.id.rv_person);
        greatPersonView1 = view.findViewById(R.id.gp_view_top_one);
        greatPersonView2 = view.findViewById(R.id.gp_view_top_two);
        greatPersonView3 = view.findViewById(R.id.gp_view_top_three);
        linearLayout = view.findViewById(R.id.layout_footer);
        ivAvatar = view.findViewById(R.id.item_footer).findViewById(R.id.iv_avatar);
        tvPersonName = view.findViewById(R.id.item_footer).findViewById(R.id.tv_person_name);
        tvCompanyName = view.findViewById(R.id.item_footer).findViewById(R.id.tv_company_name);
        tvPowerValue = view.findViewById(R.id.item_footer).findViewById(R.id.tv_power_value);
        tvTopNumber = view.findViewById(R.id.item_footer).findViewById(R.id.tv_top_number);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void initListener() {
        if (isShowSelf) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (isSlideToBottom(recyclerView)) {
                        linearLayout.setVisibility(View.GONE);
                    } else if (dy > 5) {
                        linearLayout.setVisibility(View.GONE);
                    } else if (dy < -5) {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    protected void initData() {
        assert getArguments() != null;
        rankingPeopleResp = (RankingPeopleResp) getArguments().get(LevelTwoActivity.TYPE_PAGE_PERSON);
        if (rankingPeopleResp != null) {
            for (int i = 0; i < rankingPeopleResp.getRankingList().size(); i++) {
                if (i > 2) return;
                switch (i) {
                    case 0:
                        greatPersonView1.setVisibility(View.VISIBLE);
                        greatPersonView1.setData(rankingPeopleResp.getRankingList().get(i).getUserName(), String.valueOf(rankingPeopleResp.getRankingList().get(i).getEnergyScore()), R.mipmap.ic_user_head);
                        break;
                    case 1:
                        greatPersonView2.setVisibility(View.VISIBLE);
                        greatPersonView2.setData(rankingPeopleResp.getRankingList().get(i).getUserName(), String.valueOf(rankingPeopleResp.getRankingList().get(i).getEnergyScore()), R.mipmap.ic_user_head);
                        break;
                    case 2:
                        greatPersonView3.setVisibility(View.VISIBLE);
                        greatPersonView3.setData(rankingPeopleResp.getRankingList().get(i).getUserName(), String.valueOf(rankingPeopleResp.getRankingList().get(i).getEnergyScore()), R.mipmap.ic_user_head);
                        break;
                }

            }
            List<RankingPeopleResp.RankingPeople> list = rankingPeopleResp.getRankingList().size() > 3 ? rankingPeopleResp.getRankingList().subList(3, rankingPeopleResp.getRankingList().size()) : Collections.emptyList();
            recyclerView.setAdapter(new PersonRankingAdapter(list, PersonRankingAdapter.TYPE_PERSON_TWO));
            if (rankingPeopleResp.getSelfRanking() != null) {
                isShowSelf = true;
                linearLayout.setVisibility(View.VISIBLE);
                tvPersonName.setText(rankingPeopleResp.getSelfRanking().getUserName());
                tvCompanyName.setText(rankingPeopleResp.getSelfRanking().getEnterpriseName().replace(",","/"));
                tvPowerValue.setText(String.valueOf(rankingPeopleResp.getSelfRanking().getEnergyScore()));
                tvTopNumber.setText(String.valueOf(rankingPeopleResp.getSelfRanking().getRank()));
                ivAvatar.setBackgroundResource(R.mipmap.ic_user_head);
            }
        }
    }
}
