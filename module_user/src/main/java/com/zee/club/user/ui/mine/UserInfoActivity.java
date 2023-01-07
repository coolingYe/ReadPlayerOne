package com.zee.club.user.ui.mine;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.data.UserRepository;
import com.zee.club.user.ui.login.LoginViewModel;
import com.zee.club.user.ui.login.LoginViewModelProvider;
import com.zeewain.base.ui.BaseActivity;

public class UserInfoActivity extends BaseActivity {
    private UserInfoViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(UserInfoViewModel.class);
        setContentView(R.layout.activity_user_info);
        initView();
        initViewListener();
        initViewObservable();
        initData();

    }


    private void initView() {

    }


    private void initViewListener() {



    }

    public void initData() {
        viewModel.reqImageCaptcha();
    }

    public void initViewObservable() {
        viewModel.mErrStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast("ErrString" + s);
            }
        });

    }
}