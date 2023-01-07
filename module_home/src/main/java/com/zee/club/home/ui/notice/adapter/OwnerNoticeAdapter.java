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
import com.zeewain.base.utils.GlideApp;

import java.util.List;


public class OwnerNoticeAdapter extends RecyclerView.Adapter<OwnerNoticeAdapter.NoticeViewHolder> {
    private final List<ArticleInfoResp> dataList;
    public OwnerNoticeAdapter(List<ArticleInfoResp> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_notice_layout, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class NoticeViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvItemDate;
        private final TextView tvItemTitle;
        private final TextView tvItemSummary;
        private final ImageView tvItemImg;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemDate = itemView.findViewById(R.id.tv_owner_notice_date);
            tvItemTitle = itemView.findViewById(R.id.tv_owner_notice_title);
            tvItemSummary = itemView.findViewById(R.id.tv_owner_notice_summary);
            tvItemImg = itemView.findViewById(R.id.iv_owner_notice);
        }

        public void bind(ArticleInfoResp data){
            itemView.setTag(data);
            tvItemDate.setText(data.publishTime);
            tvItemTitle.setText(data.title);
            GlideApp.with(tvItemImg.getContext())
                    .load(data.covers)
                    .into(tvItemImg);
        }
    }
}
