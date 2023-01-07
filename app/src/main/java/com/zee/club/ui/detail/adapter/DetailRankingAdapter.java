package com.zee.club.ui.detail.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.R;
import com.zee.club.data.protocol.response.AppRankingResp;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zeewain.base.utils.GlideApp;

import java.util.List;

public class DetailRankingAdapter extends RecyclerView.Adapter<DetailRankingAdapter.RankingViewHolder> {
    private List<AppRankingResp.RankingInfo> dataList;

    public DetailRankingAdapter(List<AppRankingResp.RankingInfo> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_ranking, parent, false);
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataList(List<AppRankingResp.RankingInfo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView ivItemRankingImg, ivItemHeadImg;
        private final TextView tvItemRanking;
        private final TextView tvItemUid;
        private final TextView tvItemScore;
        private final TextView tvItemDate;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemRankingImg = itemView.findViewById(R.id.iv_detail_app_ranking);
            ivItemHeadImg = itemView.findViewById(R.id.iv_detail_app_head);
            tvItemRanking = itemView.findViewById(R.id.tv_detail_app_ranking);
            tvItemUid = itemView.findViewById(R.id.tv_detail_app_uid);
            tvItemScore = itemView.findViewById(R.id.tv_detail_app_score);
            tvItemDate = itemView.findViewById(R.id.tv_detail_app_date);
            itemView.setOnClickListener(this);
        }

        public void bind(AppRankingResp.RankingInfo data){
            GlideApp.with(ivItemHeadImg.getContext())
                    .load(data.pic)
                    .into(ivItemHeadImg);

            if(data.userRank == 1){
                ivItemRankingImg.setVisibility(View.VISIBLE);
                tvItemRanking.setVisibility(View.GONE);
                ivItemRankingImg.setImageResource(R.mipmap.ic_gold_medal);
            }else if(data.userRank == 2){
                ivItemRankingImg.setVisibility(View.VISIBLE);
                tvItemRanking.setVisibility(View.GONE);
                ivItemRankingImg.setImageResource(R.mipmap.ic_silver_medal);
            }else if(data.userRank == 3){
                ivItemRankingImg.setVisibility(View.VISIBLE);
                tvItemRanking.setVisibility(View.GONE);
                ivItemRankingImg.setImageResource(R.mipmap.ic_bronze_medal);
            }else{
                ivItemRankingImg.setVisibility(View.GONE);
                tvItemRanking.setVisibility(View.VISIBLE);
                tvItemRanking.setText(String.valueOf(data.userRank));
            }

            tvItemUid.setText(data.userName);
            tvItemScore.setText(String.valueOf(data.score));
            tvItemDate.setText(data.getPlayDate());
            itemView.setTag(data);
        }

        @Override
        public void onClick(View v) {
            if(v.getTag() != null){
                AppInfoResp appInfoResp = (AppInfoResp) v.getTag();

            }
        }
    }
}
