package com.zee.club.home.ui.activities.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ActivityRankingResp;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zeewain.base.utils.GlideApp;

import java.util.List;

public class SportsMeetingRankingAdapter extends RecyclerView.Adapter<SportsMeetingRankingAdapter.RankingViewHolder> {
    private List<ActivityRankingResp.RankingInfo> dataList;

    public SportsMeetingRankingAdapter(List<ActivityRankingResp.RankingInfo> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sports_meeting_ranking, parent, false);
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
    public void setDataList(List<ActivityRankingResp.RankingInfo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView ivItemRankingImg, ivItemHeadImg;
        private final TextView tvItemUid;
        private final TextView tvItemScore;
        private final TextView tvItemDate;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemRankingImg = itemView.findViewById(R.id.iv_sports_meeting_ranking);
            ivItemHeadImg = itemView.findViewById(R.id.iv_sports_meeting_head);

            tvItemUid = itemView.findViewById(R.id.tv_sports_meeting_uid);
            tvItemScore = itemView.findViewById(R.id.tv_sports_meeting_score);
            tvItemDate = itemView.findViewById(R.id.tv_sports_meeting_date);
            itemView.setOnClickListener(this);
        }

        public void bind(ActivityRankingResp.RankingInfo data){
            GlideApp.with(ivItemHeadImg.getContext())
                    .load(data.pic)
                    .into(ivItemHeadImg);

            if(data.userRank == 1){
                ivItemRankingImg.setImageResource(R.mipmap.ic_gold_medal);
            }else if(data.userRank == 2){
                ivItemRankingImg.setImageResource(R.mipmap.ic_silver_medal);
            }else{
                ivItemRankingImg.setImageResource(R.mipmap.ic_bronze_medal);
            }

            tvItemUid.setText(data.userName);
            tvItemScore.setText(String.valueOf(data.score));
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
