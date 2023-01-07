package com.zee.club.home.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.zee.club.home.R;
import com.zee.club.home.app.ViewModelFactory;
import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.adapter.ClassicBannerAdapter;
import com.zee.club.home.ui.about.adapter.ClassicCardListViewAdapter;
import com.zee.club.home.ui.article.ArticleDetailActivity;
import com.zee.club.home.widget.CardListView;
import com.zee.club.home.widget.OnCardListItemClickListener;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.utils.ActivityHelper;
import com.zeewain.base.utils.DisplayUtil;

import java.io.Serializable;

public class AboutClubFragment extends Fragment {

    private AboutClubViewModel mViewModel;
    private Banner<ArticleInfoResp, ClassicBannerAdapter> banner;
    private CardListView<AppInfoResp, ClassicCardListViewAdapter> cardListViewHealth;
    private CardListView<AppInfoResp, ClassicCardListViewAdapter> cardListViewLucky;
    private CardListView<AppInfoResp, ClassicCardListViewAdapter> cardListViewTechnology;
    private CardListView<AppInfoResp, ClassicCardListViewAdapter> cardListViewOther;
    private APPInfoCardListItemClickListener appInfoCardListItemClickListener;

    public static AboutClubFragment newInstance() {
        return new AboutClubFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_club, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AboutClubViewModel.class);

        initData(view);
        initViewObservable();
        mViewModel.initData();
    }

    private void initData(View view){
        banner = view.findViewById(R.id.banner_about_club);
        banner.addBannerLifecycleObserver(getViewLifecycleOwner());

        banner.setIndicator(new CircleIndicator(view.getContext()));
        banner.setIndicatorWidth(DisplayUtil.dip2px(view.getContext(), 8), DisplayUtil.dip2px(view.getContext(), 8));
        banner.setIndicatorHeight(DisplayUtil.dip2px(view.getContext(), 8));
        banner.setIndicatorNormalColor(0xFFE1E1E1);
        banner.setIndicatorSelectedColor(0xFFFFC524);
        banner.setIndicatorMargins(new IndicatorConfig.Margins(DisplayUtil.dip2px(view.getContext(), 8)));
        banner.setBannerGalleryEffect(8, 8, 8, 1f);

        CardView cardView = view.findViewById(R.id.card_about_association);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewModel.articleList.size() > 0) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BaseConstants.EXTRA_ARTICLE_INFO, mViewModel.articleList.get(0));
                    intent.putExtra(BaseConstants.EXTRA_ARTICLE_INFO, bundle);
                    intent.setClass(v.getContext(), ArticleDetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });

        ImageView imgEnergyStation = view.findViewById(R.id.img_about_energy_station);
        imgEnergyStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), EnergyStationActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        cardListViewHealth = view.findViewById(R.id.card_list_about_health);
        cardListViewHealth.setRecyclerViewHorizontal();
        cardListViewHealth.setOnMoreClickListener(new CardListView.OnMoreClickListener() {
            @Override
            public void onMoreClick(View v) {
                nextToAppList(v.getContext(), ProdConstants.AboutModule.TYPE_1);
            }
        });

        cardListViewLucky = view.findViewById(R.id.card_list_about_lucky);
        cardListViewLucky.setRecyclerViewHorizontal();
        cardListViewLucky.setOnMoreClickListener(new CardListView.OnMoreClickListener() {
            @Override
            public void onMoreClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BaseConstants.EXTRA_APP_INFO_LIST, (Serializable)mViewModel.getAppInfoListFromCache(ProdConstants.AboutModule.TYPE_2));
                intent.putExtra(BaseConstants.EXTRA_APP_INFO_LIST, bundle);
                intent.setClass(v.getContext(), LuckyMetaActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        cardListViewTechnology = view.findViewById(R.id.card_list_about_technology);
        cardListViewTechnology.setRecyclerViewHorizontal();
        cardListViewTechnology.setOnMoreClickListener(new CardListView.OnMoreClickListener() {
            @Override
            public void onMoreClick(View v) {
                nextToAppList(v.getContext(), ProdConstants.AboutModule.TYPE_3);
            }
        });

        cardListViewOther = view.findViewById(R.id.card_list_about_other);
        cardListViewOther.setRecyclerViewHorizontal();
        cardListViewOther.setOnMoreClickListener(new CardListView.OnMoreClickListener() {
            @Override
            public void onMoreClick(View v) {
                nextToAppList(v.getContext(), ProdConstants.AboutModule.TYPE_4);
            }
        });

        appInfoCardListItemClickListener = new APPInfoCardListItemClickListener();
    }

    private void initViewObservable() {
        mViewModel.mldInitDataLoadState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if(LoadState.Success == loadState){
                    banner.setAdapter(new ClassicBannerAdapter(mViewModel.articleList));

                    cardListViewHealth.setAdapter(new ClassicCardListViewAdapter(mViewModel.getAppInfoListFromCache(ProdConstants.AboutModule.TYPE_1)));
                    cardListViewHealth.setOnCardListItemClickListener(appInfoCardListItemClickListener);

                    cardListViewLucky.setAdapter(new ClassicCardListViewAdapter(mViewModel.getAppInfoListFromCache(ProdConstants.AboutModule.TYPE_2)));
                    cardListViewLucky.setOnCardListItemClickListener(appInfoCardListItemClickListener);

                    cardListViewTechnology.setAdapter(new ClassicCardListViewAdapter(mViewModel.getAppInfoListFromCache(ProdConstants.AboutModule.TYPE_3)));
                    cardListViewTechnology.setOnCardListItemClickListener(appInfoCardListItemClickListener);

                    cardListViewOther.setAdapter(new ClassicCardListViewAdapter(mViewModel.getAppInfoListFromCache(ProdConstants.AboutModule.TYPE_4)));
                    cardListViewOther.setOnCardListItemClickListener(appInfoCardListItemClickListener);
                }
            }
        });
    }

    private void nextToAppList(Context context, String moduleCode){
        Intent intent = new Intent();
        intent.putExtra(BaseConstants.EXTRA_MODULE_CODE, moduleCode);
        intent.setClass(context, ClassicAppListActivity.class);
        startActivity(intent);
    }

    static class APPInfoCardListItemClickListener implements OnCardListItemClickListener{

        @Override
        public void onItemClick(View view) {
            if(view.getTag() != null){
                AppInfoResp appInfoResp = (AppInfoResp) view.getTag();
                ActivityHelper.gotoDetailActivity(view.getContext(), appInfoResp.getSkuId());
            }
        }
    }
}