package com.zee.club.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RadioGroup;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zee.club.R;
import com.zee.club.home.ui.home.HomeFragment;
import com.zee.club.user.ui.mine.DrawUserFragment;
import com.zee.club.user.ui.mine.MineFragment;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.ui.OnDrawerInteractionListener;
import com.zeewain.base.utils.SPUtils;

public class MainActivity extends BaseActivity implements OnDrawerInteractionListener {
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private Fragment currentFragment;
    private DrawUserFragment drawUserFragment;
    private DrawerLayout drawerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerMain = findViewById(R.id.drawer_main);
        RadioGroup radioGroupMain = findViewById(R.id.radio_group_main);
        radioGroupMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_main_meta) {
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.newInstance();
                    }
                    showFragment(homeFragment);
                } else if (checkedId == R.id.rb_main_mine) {
                    if (mineFragment == null) {
                        mineFragment = MineFragment.newInstance();
                    }
                    showFragment(mineFragment);
                }
            }
        });
        radioGroupMain.check(R.id.rb_main_meta);
    }

    private void showFragment(Fragment fragment) {
        if (currentFragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_content, fragment).commit();
            currentFragment = fragment;
        } else if (currentFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                transaction.hide(currentFragment).show(fragment).commit();
            } else {
                transaction.hide(currentFragment).add(R.id.fl_main_content, fragment).commit();
            }
            currentFragment = fragment;
        }
    }

    @Override
    public void drawerOpen() {
        if (drawUserFragment == null) {
            drawUserFragment = DrawUserFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_left, drawUserFragment).commit();
        }
        drawerMain.openDrawer(Gravity.LEFT);
    }

    @Override
    public void reLoadMineData() {
        if (mineFragment != null) {
            mineFragment.reLoadData();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}