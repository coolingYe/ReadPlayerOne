package com.zee.club.user.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.data.UserRepository;
//import com.zee.club.user.ui.adapter.SelectIdeAdapter;
import com.zee.club.user.data.protocol.response.AssociationResp;
import com.zee.club.user.ui.adapter.SelectIdeAdapter;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectIdeActivity extends BaseActivity {


    private ImageView ivBack;
    private TextView tvTitle;
    private RecyclerView rvSelIde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_ide);
        initView();
        initViewListener();
    }


    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rvSelIde = (RecyclerView) findViewById(R.id.rv_sel_ide);
        List<AssociationResp> asso = getIntent().getParcelableArrayListExtra(BaseConstants.EXTRA_IDE_INFO);
        SelectIdeAdapter adapter = new SelectIdeAdapter(asso, this, new SelectIdeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos, AssociationResp data) {
                SPUtils.getInstance().put(SharePrefer.associationId, data.getAssociationId());
                goToMain();
            }

            @Override
            public void onLongClick(int pos, AssociationResp data) {

            }
        });
        //设置布局管理器
        rvSelIde.setLayoutManager(new LinearLayoutManager(this));
        rvSelIde.setAdapter(adapter);
    }


    private void initViewListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void goToMain() {
        try {
            Class<?> clazz = Class.forName("com.zee.club.ui.main.MainActivity");
            Intent intent = new Intent(SelectIdeActivity.this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}