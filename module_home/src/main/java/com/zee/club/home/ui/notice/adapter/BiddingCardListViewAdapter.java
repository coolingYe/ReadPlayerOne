package com.zee.club.home.ui.notice.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.zee.club.home.R;
import com.zee.club.home.config.ProdConstants;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.notice.BiddingListActivity;
import com.zee.club.home.widget.CardListViewAdapter;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.utils.GlideApp;

import java.util.List;


public class BiddingCardListViewAdapter extends CardListViewAdapter<ArticleInfoResp, RecyclerView.ViewHolder> {
    public BiddingCardListViewAdapter(List<ArticleInfoResp> dataList) {
        super(dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item_bidding_category_layout, parent, false);
            return new CategoryViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item_bidding_layout, parent, false);
            return new CardListViewHolder(view);
        }
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, ArticleInfoResp data, int position) {
        if(holder instanceof CardListViewHolder){
            ((CardListViewHolder)holder).bind(data);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            LinearLayout biddingNoticeLayout = itemView.findViewById(R.id.ll_card_list_bidding_notice);
            biddingNoticeLayout.setTag(ProdConstants.BiddingType.NOTICE);
            biddingNoticeLayout.setOnClickListener(this);

            LinearLayout biddingPreviewLayout = itemView.findViewById(R.id.ll_card_list_bidding_preview);
            biddingPreviewLayout.setTag(ProdConstants.BiddingType.PREVIEW);
            biddingPreviewLayout.setOnClickListener(this);

            LinearLayout biddingFlowLayout = itemView.findViewById(R.id.ll_card_list_bidding_flow);
            biddingFlowLayout.setTag(ProdConstants.BiddingType.FLOW);
            biddingFlowLayout.setOnClickListener(this);

            LinearLayout biddingManageLayout = itemView.findViewById(R.id.ll_card_list_bidding_manage);
            biddingManageLayout.setTag(ProdConstants.BiddingType.MANAGE);
            biddingManageLayout.setOnClickListener(this);

            LinearLayout biddingInfoLayout = itemView.findViewById(R.id.ll_card_list_bidding_info);
            biddingInfoLayout.setTag(ProdConstants.BiddingType.INFO);
            biddingInfoLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getTag() != null){
                Intent intent = new Intent();
                intent.putExtra(BaseConstants.EXTRA_BIDDING_LIST_TYPE, (int)v.getTag());
                intent.setClass(v.getContext(), BiddingListActivity.class);
                v.getContext().startActivity(intent);
            }
        }
    }

    static class CardListViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ivItemImg;
        private final TextView tvItemTitle, tvItemRead, tvItemDate;

        public CardListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_card_list_bidding);
            tvItemTitle = itemView.findViewById(R.id.iv_card_list_bidding_title);
            tvItemRead = itemView.findViewById(R.id.iv_card_list_bidding_read);
            tvItemDate = itemView.findViewById(R.id.iv_card_list_bidding_date);
        }

        public void bind(ArticleInfoResp data){
            GlideApp.with(ivItemImg.getContext())
                    .load(data.covers)
                    .into(ivItemImg);
            tvItemTitle.setText(data.title);
            tvItemRead.setText("");
            tvItemDate.setText(data.getPublishDate());
        }
    }
}
