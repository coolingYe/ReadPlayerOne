package com.zee.club.home.ui.notice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;

import java.util.List;


public class BiddingListAdapter extends RecyclerView.Adapter<BiddingListAdapter.BiddingInfoViewHolder> {
    private List<ArticleInfoResp> dataList;
    public BiddingListAdapter(List<ArticleInfoResp> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public BiddingInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bidding_list_layout, parent, false);
        return new BiddingInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiddingInfoViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    static class BiddingInfoViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ivItemImg;
        private final TextView tvItemTitle, tvItemRead, tvItemDate;

        public BiddingInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_bidding_list);
            tvItemTitle = itemView.findViewById(R.id.iv_bidding_list_title);
            tvItemRead = itemView.findViewById(R.id.iv_bidding_list_read);
            tvItemDate = itemView.findViewById(R.id.iv_bidding_list_date);
        }

        public void bind(ArticleInfoResp data){
            ivItemImg.setImageResource(R.mipmap.home_img_energy_noon);
            tvItemTitle.setText("金鹏电子签约十亿元高密市生命安全防护工程PPP项目");
            tvItemRead.setText("96位朋友已看过");
            tvItemDate.setText("2017-07-21");
        }
    }
}
