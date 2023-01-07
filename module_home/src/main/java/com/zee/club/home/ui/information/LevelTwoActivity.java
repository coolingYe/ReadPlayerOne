package com.zee.club.home.ui.information;


import static com.zee.club.home.config.ProdConstants.ModuleCode.COMPANY_THRILLING_INFORMATION;
import static com.zee.club.home.config.ProdConstants.ModuleCode.INDUSTRY_LATEST_INFORMATION;
import static com.zee.club.home.config.ProdConstants.ModuleCode.WIN_BID_INFORMATION;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.home.R;
import com.zee.club.home.ui.userstyle.CompanyRankingFragment;
import com.zee.club.home.ui.userstyle.PersonRankingFragment;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.widgets.CustomActionBar;

public class LevelTwoActivity extends BaseActivity {
    public static final String TYPE_PAGE_FROM = "type_page_from";
    public static final String TYPE_PAGE_POWER = "type_page_power";
    public static final String TYPE_PAGE_PERSON = "type_page_person";

    private LevelTwoViewModel userStyleSecondaryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_page);
        userStyleSecondaryViewModel = new ViewModelProvider(this).get(LevelTwoViewModel.class);
        CustomActionBar customActionBar = findViewById(R.id.cab_article_detail);
        String typePage = getIntent().getStringExtra(TYPE_PAGE_FROM);
        Bundle bundle = getIntent().getBundleExtra(typePage);
        String textTitle;
        Fragment fragment;
        switch (typePage) {
            case TYPE_PAGE_PERSON:
                textTitle = "优秀人物";
                PersonRankingFragment fragment1 = PersonRankingFragment.newInstance();
                fragment1.setArguments(bundle);
                fragment = fragment1;
                break;
            case COMPANY_THRILLING_INFORMATION:
                textTitle = "公司喜庆";
                SubInformationFragment fragment2 = SubInformationFragment.newInstance();
                fragment2.pageFrom = COMPANY_THRILLING_INFORMATION;
                fragment2.setArguments(bundle);
                fragment = fragment2;
                break;
            case WIN_BID_INFORMATION:
                textTitle = "中标喜庆";
                SubInformationFragment fragment3 = SubInformationFragment.newInstance();
                fragment3.pageFrom = WIN_BID_INFORMATION;
                fragment3.setArguments(bundle);
                fragment = fragment3;
                break;
            case INDUSTRY_LATEST_INFORMATION:
                textTitle = "最新资讯";
                SubInformationFragment fragment4 = SubInformationFragment.newInstance();
                fragment4.pageFrom = INDUSTRY_LATEST_INFORMATION;
                fragment4.setArguments(bundle);
                fragment = fragment4;
                break;
            default:
                textTitle = "能量态势图";
                CompanyRankingFragment fragment5 = CompanyRankingFragment.newInstance();
                fragment5.setArguments(bundle);
                fragment = fragment5;
        }
        customActionBar.setTitle(textTitle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home_content, fragment).commit();
    }
}