package com.zee.club.home.ui.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.zee.club.home.ui.about.AboutClubFragment;
import com.zee.club.home.ui.activities.ClubActivitiesFragment;
import com.zee.club.home.ui.information.InformationFragment;
import com.zee.club.home.ui.notice.ClubNoticeFragment;
import com.zee.club.home.ui.userstyle.UserStyleFragment;

import java.util.List;

public class HomeViewPagerAdapter extends FragmentStateAdapter {

    private final List<String> categoryList;

    public HomeViewPagerAdapter(List<String> categoryList, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return ClubNoticeFragment.newInstance();
        }else if(position == 2){
            return InformationFragment.newInstance();
        }else if(position == 3){
            return UserStyleFragment.newInstance();
        }else if(position == 4){
            return ClubActivitiesFragment.newInstance();
        }
        return AboutClubFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
