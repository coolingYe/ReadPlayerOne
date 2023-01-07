package com.zee.club.home.ui.about.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zee.club.home.widget.CardListViewAdapter;
import com.zeewain.base.utils.GlideApp;

import java.util.List;


public class ClassicCardListViewAdapter extends CardListViewAdapter<AppInfoResp, ClassicCardListViewAdapter.CardListViewHolder> {
    public ClassicCardListViewAdapter(List<AppInfoResp> dataList) {
        super(dataList);
    }

    @Override
    public CardListViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item_classic_layout, parent, false);
        return new CardListViewHolder(view);
    }

    @Override
    public void onBindView(CardListViewHolder holder, AppInfoResp data, int position) {
        holder.bind(data);
    }

    static class CardListViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ivItemClassImg;
        private final TextView tvItemClassTitle;

        public CardListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemClassImg = itemView.findViewById(R.id.iv_item_classic_img);
            tvItemClassTitle = itemView.findViewById(R.id.tv_item_classic_title);
        }

        public void bind(AppInfoResp data){
            itemView.setTag(data);
            GlideApp.with(ivItemClassImg.getContext())
                    .load(data.getProductImg())
                    .into(ivItemClassImg);

            tvItemClassTitle.setText(data.getProductTitle());
        }
    }
}
