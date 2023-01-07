package com.zee.club.user.ui.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.ui.view.CommonPopup;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.utils.SPUtils;
import com.zeewain.base.widgets.CustomActionBar;

import java.util.ArrayList;

public class ExamineActivity extends BaseActivity {


    private ExamineViewModel viewModel;
    private CommonPopup commonPopup;
    private TextView tvSelIde;
    private TextView tvExamineTip;
    private int userType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(ExamineViewModel.class);
        setContentView(R.layout.activity_examine);
        initView();
        initViewObservable();
        initData();

    }


    private void initView() {
        CustomActionBar examineStation = (CustomActionBar) findViewById(R.id.examine_station);
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(getIntent().getStringExtra(UserConstants.Approve_Order_Name));

        TextView tvExamineName = (TextView) findViewById(R.id.tv_examine_name);
        tvExamineName.setText(getIntent().getStringExtra(UserConstants.Approve_Order_Name));

        tvSelIde = (TextView) findViewById(R.id.tv_sel_ide);
        ImageView ivSelIde = (ImageView) findViewById(R.id.iv_sel_ide);
        ivSelIde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonPopup.show();
            }
        });
        tvExamineTip = (TextView) findViewById(R.id.tv_examine_tip);
        Button btnExamineDown = (Button) findViewById(R.id.btn_examine_down);

        btnExamineDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ide = tvSelIde.getText().toString();
                if (ide.isEmpty()) {
                    showToast("请选择身份");
                    return;
                }
                viewModel.reqExamineApprove(SPUtils.getInstance().getInt(SharePrefer.associationId),
                        getIntent().getStringExtra(UserConstants.Approve_Order_Id), UserConstants.Approve_Agree, userType);
            }
        });

        LinearLayout llExamReject = (LinearLayout) findViewById(R.id.ll_exam_reject);
        llExamReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.reqExamineApprove(SPUtils.getInstance().getInt(SharePrefer.associationId),
                        getIntent().getStringExtra(UserConstants.Approve_Order_Id), UserConstants.Approve_Reject, userType);
            }
        });
    }


    public void initData() {
        ArrayList<CommonPopup.PopwindowsData> data = new ArrayList<>();
        data.add(new CommonPopup.PopwindowsData("管理园", "3"));
        data.add(new CommonPopup.PopwindowsData("运营人员", "2"));
        data.add(new CommonPopup.PopwindowsData("普通员工", "1"));
        commonPopup = new CommonPopup();
        commonPopup.setmContext(this);
        commonPopup.showChoose(true);
        commonPopup.setItemBackground(R.drawable.shape_title_bg);
        commonPopup.setmSelectTv(tvSelIde);
        commonPopup.setItemClickListener(new CommonPopup.OnItemClickListener() {
            @Override
            public void OnItemClick(CommonPopup.PopwindowsData value) {
                switch (value.getItemName()) {
                    case "管理园":
                        tvExamineTip.setText("账号管理、权限管理、活动组织运营等所有权限");
                        break;
                    case "运营人员":
                        tvExamineTip.setText("活动组织发起、日常运营等权限");
                        break;
                    case "普通员工":
                        tvExamineTip.setText("参与活动、互动及查看公开信息等相关的权限");
                        break;
                }
                userType = Integer.parseInt(value.getItemId());
            }


        });
        commonPopup.setSelectList(data);
    }

    public void initViewObservable() {
        viewModel.mErrStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast("ErrString" + s);
            }
        });
        viewModel.mLoadState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    showToast("分配成功");
                    finish();
                }
            }
        });

    }
}