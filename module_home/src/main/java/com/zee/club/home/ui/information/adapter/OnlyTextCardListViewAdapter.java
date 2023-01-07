package com.zee.club.home.ui.information.adapter;

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


public class OnlyTextCardListViewAdapter extends CardListViewAdapter<ArticleInfoResp, OnlyTextCardListViewAdapter.CardListViewHolder> {

    private int listCount = 0;

    public OnlyTextCardListViewAdapter(List<ArticleInfoResp> dataList) {
        super(dataList);
        listCount = dataList.size();
    }

    @Override
    public CardListViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_list_message, parent, false);
        return new CardListViewHolder(view);
    }

    @Override
    public void onBindView(CardListViewHolder holder, ArticleInfoResp data, int position) {
        holder.bind(data, position);
    }

    class CardListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivItemClassImg;
        private final TextView tvItemClassTop;
        private final TextView tvItemClassTitle;
        private final ConstraintLayout layoutItemInfo;
        private final View viewLine;

        public CardListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemClassImg = itemView.findViewById(R.id.iv_new_message);
            tvItemClassTop = itemView.findViewById(R.id.tv_top_number);
            tvItemClassTitle = itemView.findViewById(R.id.tv_information_title);
            layoutItemInfo = itemView.findViewById(R.id.layout_item_info);
            viewLine = itemView.findViewById(R.id.view_line);
        }

        public void bind(ArticleInfoResp data, int position) {
            itemView.setTag(data);
            GlideApp.with(ivItemClassImg.getContext())
                    .load(data.covers)
                    .into(ivItemClassImg);
            tvItemClassTop.setText( String.valueOf(position + 1));
            tvItemClassTitle.setText(data.title);
            if (position == listCount -1) {
                viewLine.setVisibility(View.GONE);
            }
            layoutItemInfo.setOnClickListener(view -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ArticleInfo", data);
                intent.putExtra("ArticleInfo", bundle);
                intent.setClass(view.getContext(), ArticleDetailActivity.class);
                view.getContext().startActivity(intent);
            });
        }
    }
}
