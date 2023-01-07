package com.zee.club.user.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.user.R;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.UserRepository;
import com.zeewain.base.ui.BaseActivity;

public class BindStatusActivity extends BaseActivity {


    private LoginViewModel viewModel;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivStatue;
    private TextView tvStatue;
    private Button btnKnow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        initView();
    }
    private void initView() {
        ivStatue = (ImageView) findViewById(R.id.iv_statue);
        tvStatue = (TextView) findViewById(R.id.tv_statue);
        btnKnow = (Button) findViewById(R.id.btn_know);
        int statue = getIntent().getIntExtra(UserConstants.Bind_Statue, UserConstants.BindStatueType.Bind_Statue_wait);
        switch (statue) {
            case UserConstants.BindStatueType.Bind_Statue_allow:
                tvStatue.setText("绑定成功");
                ivStatue.setImageResource(R.mipmap.icon_sta_ok);
                btnKnow.setText("开始使用");
                btnKnow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToMain();
                        finish();
                    }
                });
                break;
            case UserConstants.BindStatueType.Bind_Statue_wait:
                tvStatue.setText("审核中，请等待\n" + "审核通过会发通知给你哦");
                ivStatue.setImageResource(R.mipmap.icon_sta_wait);
                btnKnow.setText("我知道啦");
                btnKnow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToMain();
                        finish();
                    }
                });
                break;
            case UserConstants.BindStatueType.Bind_Statue_refuse:
                tvStatue.setText("绑定失败，请与管理员联系");
                ivStatue.setImageResource(R.mipmap.icon_sta_rej);
                btnKnow.setText("我知道啦");
                btnKnow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                break;
        }
    }

    private void goToMain() {
        try {
            Class<?> clazz = Class.forName("com.zee.club.ui.main.MainActivity");
            Intent intent = new Intent(BindStatusActivity.this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}