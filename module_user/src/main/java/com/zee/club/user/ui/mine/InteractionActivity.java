package com.zee.club.user.ui.mine;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.config.UserConstants;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.utils.SPUtils;

import java.util.ArrayList;

public class InteractionActivity extends BaseActivity {


    private MessageCenterViewModel viewModel;
    final String[] tabs = new String[]{"互动", "活动"};
    private ArrayList<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(MessageCenterViewModel.class);
        setContentView(R.layout.activity_interaction);
        initView();
        initViewObservable();
        initData();
    }


    private void initView() {
        fragments = new ArrayList<>();
        fragments.add(InteractionAppFragment.newInstance());
        fragments.add(ActivitiesFragment.newInstance());
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(view -> finish());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == null || tab.getText() == null) {
                    return;
                }
                scaleAnima(1.0f, 1.3f, tab.view);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.view == null || tab.getText() == null) {
                    return;
                }
                scaleAnima(1.3f, 1.0f, tab.view);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.view_pager);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //这里可以自定义TabView
                tab.setText(tabs[position]);
            }
        }).attach();
        scaleAnima(1.0f, 1.3f, tabLayout.getTabAt(0).view);
    }



    public void initData() {
    }

    public void initViewObservable() {
        viewModel.mErrStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast("ErrString" + s);
            }
        });

    }


    private void scaleAnima(float v, float v2, TabLayout.TabView title) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                v, v2, v, v2,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(100);//动画持续时间
        animationSet.addAnimation(scaleAnimation);//保存动画效果到。。
        animationSet.setFillAfter(true);//结束后保存状态
        title.startAnimation(animationSet);//设置给控件
    }
}