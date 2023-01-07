package com.zee.club.home.ui.information;

import static com.zee.club.home.config.ProdConstants.ModuleCode.INDUSTRY_LATEST_INFORMATION;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.information.adapter.BannerAdapter;
import com.zee.club.home.ui.information.adapter.OnlyTextCardListViewAdapter;
import com.zee.club.home.widget.CardListView;
import com.zeewain.base.ui.BaseFragment;
import com.zeewain.base.utils.DisplayUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class InformationFragment extends BaseFragment {

    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

    private final String[] tabTitles = {"技术", "产品", "方案", "活动", "会议"};
    private List<InformationCategoryFragment> fragments = new ArrayList<>();
    private Banner banner;
    private CardListView cardListView;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private InformationViewModel mViewModel;
    private NestedScrollView nestedScrollView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    protected void initView(View view) {
        mViewModel = new ViewModelProvider(this).get(InformationViewModel.class);

        banner = view.findViewById(R.id.banner_about_club);
        cardListView = view.findViewById(R.id.card_list_common);
        viewPager2 = view.findViewById(R.id.view_pager_info);
        tabLayout = view.findViewById(R.id.tab_layout_category);
        nestedScrollView = view.findViewById(R.id.nsv_about_club);
        ConstraintLayout layoutFooter = view.findViewById(R.id.footer_view);
        layoutFooter.setOnClickListener(v -> ((NestedScrollView)view.findViewById(R.id.nsv_about_club)).fullScroll(View.FOCUS_UP));
        banner.addBannerLifecycleObserver(getViewLifecycleOwner());

        for (int i = 0; i < tabTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(InformationCategoryFragment.TYPE_PAGE, tabTitles[i]);
            InformationCategoryFragment fragment = InformationCategoryFragment.newInstance();
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        banner.setIndicator(new CircleIndicator(view.getContext()));
        banner.setIndicatorWidth(DisplayUtil.dip2px(view.getContext(), 8), DisplayUtil.dip2px(view.getContext(), 8));
        banner.setIndicatorHeight(DisplayUtil.dip2px(view.getContext(), 8));
        banner.setIndicatorNormalColor(0xFFE1E1E1);
        banner.setIndicatorSelectedColor(0xFFFFC524);
        banner.setIndicatorMargins(new IndicatorConfig.Margins(DisplayUtil.dip2px(view.getContext(), 8)));
        banner.setBannerGalleryEffect(8, 8, 8, 1f);

        viewPager2.setAdapter(new PageAdapter(fragments, getChildFragmentManager(), getLifecycle()));
        new TabLayoutMediator(tabLayout, viewPager2, true, true, ((tab, position) -> {
            tab.setText(tabTitles[position]);
        })).attach();
    }

    @Override
    protected void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null) {
                    String title = Objects.requireNonNull(tab.getText()).toString();
                    SpannableString spannableString = new SpannableString(title);
                    spannableString.setSpan(new AbsoluteSizeSpan(16, true), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tab.setText(spannableString);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab != null) {
                    String title = Objects.requireNonNull(tab.getText()).toString();
                    SpannableString spannableString = new SpannableString(title);
                    spannableString.setSpan(new AbsoluteSizeSpan(12, true), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tab.setText(spannableString);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        cardListView.setOnMoreClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(INDUSTRY_LATEST_INFORMATION, (Serializable) mViewModel.newInfoList.getValue());
            intent.putExtra(INDUSTRY_LATEST_INFORMATION, bundle);
            intent.putExtra(LevelTwoActivity.TYPE_PAGE_FROM, INDUSTRY_LATEST_INFORMATION);
            intent.setClass(v.getContext(), LevelTwoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        mViewModel.initData();
        mViewModel.bannerList.observe(getViewLifecycleOwner(), list -> {
            banner.setAdapter(new BannerAdapter(list));
        });
        mViewModel.newInfoList.observe(getViewLifecycleOwner(), list -> {
            List<ArticleInfoResp> list1 = list.size() > 3 ? list.subList(0, 3) : list;
            cardListView.setAdapter(new OnlyTextCardListViewAdapter(list1));
        });
    }

    private static class PageAdapter extends FragmentStateAdapter {

        private final List<InformationCategoryFragment> fragments;
        private final HashSet<Long> createIds = new HashSet<>();
        private final long[] ids = new long[]{2001, 2002, 2003, 2004, 2005};

        public PageAdapter(List<InformationCategoryFragment> fragments, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            createIds.add(ids[position]);
            return fragments.get(position);
        }

        @Override
        public boolean containsItem(long itemId) {
            return createIds.contains(itemId);
        }

        @Override
        public long getItemId(int position) {
            return ids[position];
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}
