package com.zee.club.home.ui.about;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zee.club.home.R;
import com.zee.club.home.app.ViewModelFactory;
import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.ui.about.adapter.ClassicAppListAdapter;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.model.DataLoadState;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.widgets.CustomActionBar;

import java.util.ArrayList;

public class ClassicAppListActivity extends BaseActivity {

    private ClassicAppListViewModel mViewModel;
    private RecyclerView recyclerView;
    private TextView tvActionBarTitle;
    private ConstraintLayout clAppListSelectLayout;
    private TextView tvHealthSelect, tvTechnologySelect, tvOtherSelect;
    private String currentModuleCode;
    private ClassicAppListAdapter classicAppListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ClassicAppListViewModel.class);
        setContentView(R.layout.activity_classic_app_list);

        currentModuleCode = getIntent().getStringExtra(BaseConstants.EXTRA_MODULE_CODE);

        if(currentModuleCode == null){
            finish();
            return;
        }
        initView();
        initViewObservable();

        mViewModel.reqAppList(currentModuleCode);
    }

    private void initView(){
        CustomActionBar customActionBar = findViewById(R.id.cab_classic_app_list);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_classic_app_list_bar, null, false);
        tvActionBarTitle = view.findViewById(R.id.tv_app_list_bar_title);
        ImageView ivActionBarSelected = view.findViewById(R.id.iv_app_list_bar_select);
        ivActionBarSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clAppListSelectLayout.setVisibility(View.VISIBLE);
            }
        });
        customActionBar.addContentView(view);
        updateActionBarTitle();

        clAppListSelectLayout = findViewById(R.id.cl_classic_app_list_select);
        clAppListSelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clAppListSelectLayout.setVisibility(View.GONE);
            }
        });
        tvHealthSelect = findViewById(R.id.tv_app_list_select_health);
        tvHealthSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentModuleCode = ProdConstants.AboutModule.TYPE_1;
                classicAppListAdapter.setDataList(new ArrayList<>());
                mViewModel.reqAppList(currentModuleCode);
                clAppListSelectLayout.setVisibility(View.GONE);
                updateActionBarTitle();
            }
        });
        tvTechnologySelect = findViewById(R.id.tv_app_list_select_technology);
        tvTechnologySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentModuleCode = ProdConstants.AboutModule.TYPE_3;
                classicAppListAdapter.setDataList(new ArrayList<>());
                mViewModel.reqAppList(currentModuleCode);
                clAppListSelectLayout.setVisibility(View.GONE);
                updateActionBarTitle();
            }
        });
        tvOtherSelect = findViewById(R.id.tv_app_list_select_other);
        tvOtherSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentModuleCode = ProdConstants.AboutModule.TYPE_4;
                classicAppListAdapter.setDataList(new ArrayList<>());
                mViewModel.reqAppList(currentModuleCode);
                clAppListSelectLayout.setVisibility(View.GONE);
                updateActionBarTitle();
            }
        });

        recyclerView = findViewById(R.id.recycler_view_classic_app_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        classicAppListAdapter = new ClassicAppListAdapter(new ArrayList<>());
        recyclerView.setAdapter(classicAppListAdapter);
    }

    private void updateActionBarTitle(){
        if(ProdConstants.AboutModule.TYPE_4.equals(currentModuleCode)){
            tvActionBarTitle.setText("其他");
        }else if(ProdConstants.AboutModule.TYPE_3.equals(currentModuleCode)){
            tvActionBarTitle.setText("科技文化");
        }else if(ProdConstants.AboutModule.TYPE_1.equals(currentModuleCode)){
            tvActionBarTitle.setText("健康体育");
        }
    }

    private void initViewObservable() {
        mViewModel.mldAppListLoadState.observe(this, new Observer<DataLoadState<String>>() {
            @Override
            public void onChanged(DataLoadState<String> dataLoadState) {
                if(LoadState.Success == dataLoadState.loadState && currentModuleCode.equals(dataLoadState.data)){
                    classicAppListAdapter.setDataList(mViewModel.getAppInfoListFromCache(currentModuleCode));
                }
            }
        });
    }
}