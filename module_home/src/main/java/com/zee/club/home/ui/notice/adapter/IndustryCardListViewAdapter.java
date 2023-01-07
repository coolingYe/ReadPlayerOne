package com.zee.club.home.ui.notice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.widget.CardListViewAdapter;
import com.zeewain.base.utils.GlideApp;

import java.util.List;


public class IndustryCardListViewAdapter extends CardListViewAdapter<ArticleInfoResp, IndustryCardListViewAdapter.CardListViewHolder> {
    public IndustryCardListViewAdapter(List<ArticleInfoResp> dataList) {
        super(dataList);
    }

    @Override
    public CardListViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item_industry_layout, parent, false);
        return new CardListViewHolder(view);
    }

    @Override
    public void onBindView(CardListViewHolder holder, ArticleInfoResp data, int position) {
        holder.bind(data, position);
    }

    static class CardListViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ivItemImg;

        public CardListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_card_list_industry);
        }

        public void bind(ArticleInfoResp data, int position){
            itemView.setTag(data);
            GlideApp.with(ivItemImg.getContext())
                    .load(data.covers)
                    .into(ivItemImg);
        }
    }
}
