package com.zee.club.user.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.data.protocol.response.UserAppInfoResp;

import java.util.List;

public class UserAppInfoAdapter extends RecyclerView.Adapter<UserAppInfoAdapter.ViewHolder> {
    private List<UserAppInfoResp> dataList;

    public UserAppInfoAdapter(List<UserAppInfoResp> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_info_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ivItemImg;
        private final TextView tvItemTitle;
        private final TextView tvItemDuration;
        private final TextView tvItemLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_activities);
            tvItemTitle = itemView.findViewById(R.id.tv_activities_title);
            tvItemDuration = itemView.findViewById(R.id.tv_activities_duration);
            tvItemLevel = itemView.findViewById(R.id.tv_activities_level);
            itemView.setOnClickListener(this);
        }

        public void bind(UserAppInfoResp data) {
//            GlideApp.with(ivItemImg.getContext())
//                    .load(data.getProductImg())
//                    .into(ivItemImg);
//
//            tvItemTitle.setText(data.getProductTitle());
//            itemView.setTag(data);
        }

        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                UserAppInfoResp appInfoResp = (UserAppInfoResp) v.getTag();
            }
        }
    }
}
