package com.zee.club.ui.guide;

import static com.zeewain.base.utils.ActivityHelper.navigateToLogOnPageOrMainPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.card.MaterialCardView;
import com.zee.club.R;
import com.zeewain.base.ui.BaseActivity;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private final int pageCount = 3;
    private final int[] imageIds = {R.mipmap.ic_guide_illustration_1, R.mipmap.ic_guide_illustration_2, R.mipmap.ic_guide_illustration_3};
    private final String[] titleList = {"文化", "科技", "发展"};
    private final String[] descList = {"社团元宇宙 弘扬社团精神", "传播社团文化 见证社团荣誉", "凝聚社团活力 助力企业发展"};

    private ImageView[] dotViews;
    private final ArrayList<View> viewList = new ArrayList<>();
    private LinearLayout imgLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        imgLayout = findViewById(R.id.layout_point);
        ViewPager mViewPager = findViewById(R.id.vp_guide);
        mViewPager.addOnPageChangeListener(this);

        for (int i = 0; i < pageCount; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_view_guide_child, null);
            ImageView ivIllustration = view.findViewById(R.id.iv_illustration);
            TextView tvJump = view.findViewById(R.id.tv_guide_jump);
            TextView tvTitle = view.findViewById(R.id.tv_guide_title);
            TextView tvDesc = view.findViewById(R.id.tv_guide_desc);
            if (i == pageCount - 1) {
                MaterialCardView cardView = view.findViewById(R.id.card_start_text);
                cardView.setVisibility(View.VISIBLE);
                cardView.setOnClickListener(v -> {
                    navigateToLogOnPageOrMainPage(this);
                    finish();
                });
                tvJump.setVisibility(View.GONE);
            }
            tvJump.setOnClickListener(v -> {
                navigateToLogOnPageOrMainPage(this);
                finish();
            });
            ivIllustration.setBackgroundResource(imageIds[i]);
            tvTitle.setText(titleList[i]);
            tvDesc.setText(descList[i]);
            viewList.add(view);
        }

        GuidePagerAdapter mPagerAdapter = new GuidePagerAdapter(viewList);
        mViewPager.setAdapter(mPagerAdapter);

        initPointView();
    }

    private void initPointView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);
        dotViews = new ImageView[pageCount];
        for (int i = 0; i < pageCount; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.ic_unselect_background);
            if (i == 0) {
                imageView.setSelected(true);
            } else {
                imageView.setSelected(false);
            }
            dotViews[i] = imageView;
            dotViews[0].setImageResource(R.drawable.ic_selected_background);
            imgLayout.addView(imageView);
        }
    }

    private static class GuidePagerAdapter extends PagerAdapter {
        private final ArrayList<View> views;

        private GuidePagerAdapter(ArrayList<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, @NonNull Object object) {
            view.removeView(views.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(views.get(position));
            return views.get(position);
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotViews.length; i++) {
            if (position == i) {
                dotViews[i].setImageResource(R.drawable.ic_selected_background);
            } else {
                dotViews[i].setImageResource(R.drawable.ic_unselect_background);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}
