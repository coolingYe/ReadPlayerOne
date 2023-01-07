package com.zee.club.home.ui.userstyle.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.article.ArticleDetailActivity;
import com.zee.club.home.widget.CardListViewAdapter;
import com.zeewain.base.utils.GlideApp;

import java.util.List;


public class UserStyleArticleAdapter extends CardListViewAdapter<ArticleInfoResp, RecyclerView.ViewHolder> {

    private int listCount = 0;
    public OnClickItemListener onClickItemListener;
    public int TYPE_KEY = 0;
    public static final int TYPE_INFO_ONE = 0;
    public static final int TYPE_INFO_TWO = 1;

    public UserStyleArticleAdapter(List<ArticleInfoResp> dataList, int type) {
        super(dataList);
        this.TYPE_KEY = type;
        listCount = dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (TYPE_KEY == TYPE_INFO_ONE) {
            return TYPE_INFO_ONE;
        } else {
            return TYPE_INFO_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_INFO_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_list_message, parent, false);
            return new InfoOnlyTextCardListViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_information_text, parent, false);
            return new InfoCardListViewHolder(view);
        }
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, ArticleInfoResp data, int position) {
        if (holder instanceof InfoOnlyTextCardListViewHolder) {
            ((InfoOnlyTextCardListViewHolder) holder).bind(data, position);
        } else {
            ((InfoCardListViewHolder) holder).bind(data, position);
        }
    }


    class InfoOnlyTextCardListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivItemClassImg;
        private final TextView tvItemClassTop;
        private final TextView tvItemClassTitle;
        private final ConstraintLayout layoutItem;
        private final View viewLine;

        public InfoOnlyTextCardListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemClassImg = itemView.findViewById(R.id.iv_new_message);
            tvItemClassTitle = itemView.findViewById(R.id.tv_information_title);
            tvItemClassTop = itemView.findViewById(R.id.tv_top_number);
            layoutItem = itemView.findViewById(R.id.layout_information_view);
            viewLine = itemView.findViewById(R.id.view_line);
        }

        public void bind(ArticleInfoResp data, int position) {
            itemView.setTag(data);
            tvItemClassTop.setText(String.valueOf(position + 1));
            tvItemClassTitle.setText(data.title);
            if (position == listCount -1) {
                viewLine.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ArticleInfo", data);
                intent.putExtra("ArticleInfo", bundle);
                intent.setClass(v.getContext(), ArticleDetailActivity.class);
                v.getContext().startActivity(intent);
            });
        }
    }

    class InfoCardListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivItemClassImg;
        private final TextView tvItemClassTitle;
        private final TextView tvItemClassDate;
        private final TextView tvItemClassRead;
        private final ConstraintLayout layoutItem;
        private final View viewLine;

        public InfoCardListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemClassImg = itemView.findViewById(R.id.iv_info_img);
            tvItemClassTitle = itemView.findViewById(R.id.tv_information_title);
            tvItemClassDate = itemView.findViewById(R.id.tv_information_date);
            tvItemClassRead = itemView.findViewById(R.id.tv_have_read);
            layoutItem = itemView.findViewById(R.id.layout_information_view);
            viewLine = itemView.findViewById(R.id.view_line);
        }

        public void bind(ArticleInfoResp data, int position) {
            itemView.setTag(data);
            GlideApp.with(ivItemClassImg.getContext())
                    .load(data.covers)
                    .into(ivItemClassImg);
            tvItemClassTitle.setText(data.title);
            tvItemClassDate.setText(data.updateTime);
            tvItemClassRead.setText("");
            if (position == listCount -1) {
                viewLine.setVisibility(View.GONE);
            }
            layoutItem.setOnClickListener(view -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ArticleInfo", data);
                intent.putExtra("ArticleInfo", bundle);
                intent.setClass(view.getContext(), ArticleDetailActivity.class);
                view.getContext().startActivity(intent);
            });
        }
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClick(View view, ArticleInfoResp data);
    }
}
