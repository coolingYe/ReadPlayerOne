package com.zee.club.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zee.club.home.R;
import com.zee.club.home.ui.home.adapter.HomeViewPagerAdapter;
import com.zee.club.home.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private final List<String> categoryList = new ArrayList<>(7);

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout_category);
        ViewPager2 viewPagerClubMeta = view.findViewById(R.id.view_pager_club_meta);
        TextView tvSearch = view.findViewById(R.id.tv_home_search);
        tvSearch.setOnClickListener(v -> startActivity(new Intent(requireActivity(), SearchActivity.class)));
        categoryList.add("关于社团");
        categoryList.add("社团通知");
        categoryList.add("行业资讯");
        categoryList.add("会员风采");
        categoryList.add("社团活动");
        categoryList.add("行业法规");
        categoryList.add("社团公益");

        viewPagerClubMeta.setAdapter(new HomeViewPagerAdapter(categoryList, getParentFragmentManager(), getLifecycle()));

        new TabLayoutMediator(tabLayout, viewPagerClubMeta, true, true, (tab, position) -> {
            //tab.setText(categoryList.get(position));
            View tabView = LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.layout_home_tab_view, null, false);
            TextView textViewSelected = tabView.findViewById(R.id.tv_tab_view_title_selected);
            TextView textView = tabView.findViewById(R.id.tv_tab_view_title);
            textViewSelected.setText(categoryList.get(position));
            textView.setText(categoryList.get(position));
            tab.setCustomView(tabView);
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getCustomView() != null){
                    TextView textViewSelected = tab.getCustomView().findViewById(R.id.tv_tab_view_title_selected);
                    TextView textView = tab.getCustomView().findViewById(R.id.tv_tab_view_title);
                    textViewSelected.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getCustomView() != null){
                    TextView textViewSelected = tab.getCustomView().findViewById(R.id.tv_tab_view_title_selected);
                    TextView textView = tab.getCustomView().findViewById(R.id.tv_tab_view_title);
                    textViewSelected.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}