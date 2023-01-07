package com.zee.club.user.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.user.R;
import com.zee.club.user.data.UserRepository;
import com.zeewain.base.ui.BaseActivity;

public class CommunitySetlActivity extends BaseActivity {

    private CommunitySetlFragment setlFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_settl);
        initView();
    }


    private void initView() {
        findViewById(R.id.btn_show_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setlFragment == null) {
                    setlFragment = CommunitySetlFragment.newInstance();
                }
                setlFragment.show(getSupportFragmentManager(), "dialog_fragment");
            }
        });
    }
}