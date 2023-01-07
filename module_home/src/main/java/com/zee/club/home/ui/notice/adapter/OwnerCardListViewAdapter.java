package com.zee.club.home.ui.notice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.widget.CardListViewAdapter;

import java.util.List;


public class OwnerCardListViewAdapter extends CardListViewAdapter<ArticleInfoResp, OwnerCardListViewAdapter.CardListViewHolder> {
    public OwnerCardListViewAdapter(List<ArticleInfoResp> dataList) {
        super(dataList);
    }

    @Override
    public CardListViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item_owner_layout, parent, false);
        return new CardListViewHolder(view);
    }

    @Override
    public void onBindView(CardListViewHolder holder, ArticleInfoResp data, int position) {
        holder.bind(data, position);
    }

    static class CardListViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvItemIndex;
        private final TextView tvItemTitle;
        private final TextView tvItemSummary;

        public CardListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tv_item_owner_title);
            tvItemIndex = itemView.findViewById(R.id.tv_item_owner_index);
            tvItemSummary = itemView.findViewById(R.id.tv_item_owner_summary);
        }

        public void bind(ArticleInfoResp data, int position){
            itemView.setTag(data);
            tvItemTitle.setText(data.title);
            tvItemIndex.setText(String.valueOf(position + 1));
        }
    }
}
