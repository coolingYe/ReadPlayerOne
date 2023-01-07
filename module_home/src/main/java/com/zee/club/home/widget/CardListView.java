package com.zee.club.home.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.zee.club.home.R;
import com.zeewain.base.utils.DisplayUtil;

import java.util.List;

public class CardListView<T, CA extends CardListViewAdapter<T, ? extends RecyclerView.ViewHolder>> extends CardView {
    private RecyclerView recyclerView;
    private TextView tvCardTitle, tvCardListMore;
    private OnMoreClickListener onMoreClickListener;
    private OnScrollTopListener onScrollTopListener;
    private OnScrollDownListener onScrollDownListener;
    private OnScrollBottomListener onScrollBottomListener;
    private CA cardListViewAdapter;
    private String cardTitle;
    private final int cardListLeftPadding;
    private final int cardListRightPadding;
    private final boolean cardShowMore;

    public CardListView(@NonNull Context context) {
        this(context, null);
    }

    public CardListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardListView);
        String cardTitle = typedArray.getString(R.styleable.CardListView_cardTitle);
        cardListLeftPadding = (int)typedArray.getDimension(R.styleable.CardListView_cardListLeftPadding, DisplayUtil.dip2px(context, 13));
        cardListRightPadding = (int)typedArray.getDimension(R.styleable.CardListView_cardListRightPadding, DisplayUtil.dip2px(context, 13));
        cardShowMore = typedArray.getBoolean(R.styleable.CardListView_cardShowMore, true);
        typedArray.recycle();
        initView(context, cardTitle);
    }

    private void initView(Context context, String cardTitle){
        LayoutInflater.from(context).inflate(R.layout.layout_card_list_view, this);
        recyclerView = findViewById(R.id.recycler_view_card_list);
        recyclerView.setAdapter(cardListViewAdapter);
        recyclerView.setPadding(cardListLeftPadding, 0, cardListRightPadding, 0);
        tvCardTitle = findViewById(R.id.tv_card_list_title);
        tvCardListMore = findViewById(R.id.tv_card_list_more);
        tvCardListMore.setVisibility(cardShowMore ? View.VISIBLE : View.GONE);

        if(cardTitle != null){
            tvCardTitle.setText(cardTitle);
        }

        tvCardListMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onMoreClickListener != null){
                    onMoreClickListener.onMoreClick(v);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    if(onScrollBottomListener != null){
                        onScrollBottomListener.onScrollBottom(recyclerView);
                    }
                } else if (dy > 0) {
                    if(onScrollDownListener != null){
                        onScrollDownListener.onScrollDown(recyclerView, dy);
                    }
                } else if (dy < 0) {
                    if(onScrollTopListener != null){
                        onScrollTopListener.onScrollTop(recyclerView, dy);
                    }
                }
            }
        });
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public void setPaddingRecyclerView() {
        recyclerView.setPadding(-10, 0,0,0);
    }

    public void hideMoreText() {
        tvCardListMore.setVisibility(View.INVISIBLE);
    }

    public void setDataList(List<T> dataList){
        if (getAdapter() != null) {
            getAdapter().setDataList(dataList);
        }
    }

    public void setAdapter(CA adapter) {
        this.cardListViewAdapter = adapter;
        recyclerView.setAdapter(cardListViewAdapter);
    }

    public void setAdapter(CA adapter, int type) {
        this.cardListViewAdapter = adapter;
        recyclerView.setAdapter(cardListViewAdapter);
    }

    public CardListViewAdapter getAdapter() {
        return cardListViewAdapter;
    }

    public void setOnCardListItemClickListener(OnCardListItemClickListener onCardListItemClickListener) {
        if (getAdapter() != null) {
            getAdapter().setOnCardListItemClickListener(onCardListItemClickListener);
        }
    }

    public void setTitle(String title) {
        if(tvCardTitle != null) {
            tvCardTitle.setText(title);
        }
    }

    public void setItemDecoration(RecyclerView.ItemDecoration decoration){
        recyclerView.addItemDecoration(decoration);
    }

    public void setMoreText(String moreText) {
        if(tvCardListMore != null) {
            tvCardListMore.setText(moreText);
        }
    }

    public void setOnMoreClickListener(OnMoreClickListener onMoreClickListener) {
        this.onMoreClickListener = onMoreClickListener;
    }

    public void setOnScrollTopListener(OnScrollTopListener onScrollTopListener) {
        this.onScrollTopListener = onScrollTopListener;
    }

    public void setOnScrollDownListener(OnScrollDownListener onScrollDownListener) {
        this.onScrollDownListener = onScrollDownListener;
    }

    public void setOnScrollBottomListener(OnScrollBottomListener onScrollBottomListener) {
        this.onScrollBottomListener = onScrollBottomListener;
    }


    public void setRecyclerViewHorizontal(){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    public interface OnMoreClickListener{
        void onMoreClick(View v);
    }
    public interface OnScrollTopListener{
        void onScrollTop(View v, int dy);
    }
    public interface OnScrollDownListener{
        void onScrollDown(View v, int dy);
    }
    public interface OnScrollBottomListener {
        void onScrollBottom(View v);
    }
}
