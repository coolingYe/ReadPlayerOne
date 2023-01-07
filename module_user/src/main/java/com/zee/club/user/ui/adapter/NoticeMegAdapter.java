package com.zee.club.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.protocol.response.NoticeResp;

import java.util.List;

public class NoticeMegAdapter extends RecyclerView.Adapter<NoticeMegAdapter.ViewHolder> {


    private List<NoticeResp> data;

    public NoticeMegAdapter(List<NoticeResp> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }


    @Override
    public int getItemCount() {
        //返回List的大小，使其全部显示
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvMessageTitle;
        private ImageView ivMessageBg;
        private TextView tvMessageContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //定位要设置的控件
            tvMessageTitle = (TextView) itemView.findViewById(R.id.tv_message_title);
            ivMessageBg = (ImageView) itemView.findViewById(R.id.iv_message_bg);
            tvMessageContent = (TextView) itemView.findViewById(R.id.tv_message_content);
        }

        public void onBind(NoticeResp itemData) {
            switch (itemData.getNoticeType()) {
                case UserConstants.NoticeMessageType.Application_Update:
                    ivMessageBg.setBackgroundResource(R.mipmap.image_noti_app_up);
                    break;
                case UserConstants.NoticeMessageType.Community_Residence:
                    ivMessageBg.setBackgroundResource(R.mipmap.image_noti_join_association);
                    break;
                case UserConstants.NoticeMessageType.League_Membership:
                    ivMessageBg.setBackgroundResource(R.mipmap.image_noti_join_league);
                    break;
            }

        }

        @Override
        public void onClick(View view) {

        }
    }
}