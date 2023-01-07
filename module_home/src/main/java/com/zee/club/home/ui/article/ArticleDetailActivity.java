package com.zee.club.home.ui.article;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.home.R;
import com.zee.club.home.app.ViewModelFactory;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.utils.ToastUtils;
import com.zeewain.base.widgets.CustomActionBar;


public class ArticleDetailActivity extends BaseActivity implements ArticleDetailsContract.View {
    private int oldY = 0;
    private boolean hasBottom = false;
    private boolean hasFromUserCenter = false;
    private boolean hasFirstShow = false;
    private ArticleInfoResp articleInfoResp;

    private ArticleDetailViewModel mViewModel;
    private ArticleDetailsPresenter mPresenter;

    private WebView webView;
    private ConstraintLayout layoutFooter;
    private ImageView ivSave, ivLike;
    private TextView tvSaveNum, tvLikeNum;
    private boolean hasCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ArticleDetailViewModel.class);
        mPresenter = new ArticleDetailsPresenter(mViewModel, this);
        setContentView(R.layout.activity_article_detail);
        Bundle bundle = getIntent().getBundleExtra(BaseConstants.EXTRA_ARTICLE_INFO);
        if (bundle != null) {
            articleInfoResp = (ArticleInfoResp) bundle.getSerializable(BaseConstants.EXTRA_ARTICLE_INFO);
        } else {
            String[] strings = getIntent().getStringArrayExtra("UserCenter");
            if (strings.length == 3) {
                hasFromUserCenter = true;
                articleInfoResp = new ArticleInfoResp();
                articleInfoResp.articleId = strings[0];
                articleInfoResp.articleType = Integer.valueOf(strings[1]);
                articleInfoResp.title = strings[2];
            }
        }
        initView();
        initListener();
        initData();
        initObserve();
    }

    private void initView() {
        layoutFooter = findViewById(R.id.layout_footer);
        webView = findViewById(R.id.web_view_article_detail);
        ivSave = findViewById(R.id.iv_save);
        ivLike = findViewById(R.id.iv_like);
        tvLikeNum = findViewById(R.id.tv_liked_count);
        tvSaveNum = findViewById(R.id.tv_saved_count);
        CustomActionBar customActionBar = findViewById(R.id.cab_article_detail);
        customActionBar.setTitle(articleInfoResp.title);
    }

    private void initListener() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("Test", "WebView has been done");
                hasFirstShow = true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

        });

        setGestureListener(webView);

//        webView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (mPresenter.isScrollToBottom((WebView) v)) {
//                Log.d("Test", "WebView has been bottom");
//                if (!hasBottom && !hasFromUserCenter) {
//                    mPresenter.requestEnergyAdd(new EnergyAddReq(
//                            ProdConstants.EnergySourceType.INFO,
//                            articleInfoResp.articleId,
//                            articleInfoResp.title,
//                            articleInfoResp.articleType));
//                }
//                layoutFooter.setVisibility(View.GONE);
//            } else if (oldY < scrollY) {
//                layoutFooter.setVisibility(View.GONE);
//            } else {
//                layoutFooter.setVisibility(View.VISIBLE);
//            }
//            oldY = scrollY;
//        });

        ivLike.setOnClickListener(v -> {
            mPresenter.clickPraiseView(articleInfoResp.articleId);
        });

        ivSave.setOnClickListener(v -> {
            mPresenter.clickFavoriteView(articleInfoResp.articleId);
        });
    }

    private void initData() {
        mPresenter.reqDetailInfo(articleInfoResp.articleId);
        mPresenter.reqCheckPraiseAndFavorites(articleInfoResp.articleId);
    }

    private void initObserve() {
        mViewModel.articleData.observe(this, articleInfoResp1 -> {
            tvSaveNum.setText(String.valueOf(articleInfoResp1.favoriteNum));
            tvLikeNum.setText(String.valueOf(articleInfoResp1.praiseNum));
            if (hasFirstShow) return;
            webView.loadData(articleInfoResp1.content, "text/html", "utf-8");
        });
        mViewModel.mldEnergyAddLoadState.observe(this, loadState -> {
            if (loadState == LoadState.Success) {
                setHasBottom(true);
                if (mViewModel.energyValue != 0) {
                    showToast("能量+" + mViewModel.energyValue);
                }
            }
        });
        mViewModel.hasPraise.observe(this, hasPraise -> {
            if (hasCheck) mPresenter.reqDetailInfo(articleInfoResp.articleId);
        });
        mViewModel.hasFavorites.observe(this, hasFavorites -> {
            if (hasCheck) mPresenter.reqDetailInfo(articleInfoResp.articleId);
        });
    }


    private float mPosX, mPosY, mCurPosX, mCurPosY;

    @SuppressLint("ClickableViewAccessibility")
    private void setGestureListener(View view) {

        view.setOnTouchListener((v, event) -> {
            new Handler().post(() -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mPresenter.isScrollToBottom((WebView) v)) {
                            layoutFooter.setVisibility(View.GONE);
                        } else if (mCurPosY - mPosY > 0
                                && (Math.abs(mCurPosY - mPosY) > 30)) {
                            layoutFooter.setVisibility(View.GONE);
                            ToastUtils.showLong("scroll down");
                        } else if (mCurPosY - mPosY < 0
                                && (Math.abs(mCurPosY - mPosY) > 30)) {
                            ToastUtils.showLong("scroll up");
                            layoutFooter.setVisibility(View.VISIBLE);
                        }

                        break;
                }
            });
            return true;
        });
    }

    @Override
    public void hasChecked() {
        hasCheck = true;
    }

    @Override
    public void showArticleToast(String text) {
        showToast("能量+" + text);
    }

    @Override
    public void setHasBottom(boolean b) {
        hasBottom = b;
    }
}