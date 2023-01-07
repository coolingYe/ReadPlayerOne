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
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zeewain.base.utils.ActivityHelper;
import com.zeewain.base.utils.GlideApp;

import java.util.List;

public class SportsMeetingAppAdapter extends RecyclerView.Adapter<SportsMeetingAppAdapter.AppInfoViewHolder> {
    private List<AppInfoResp> dataList;

    public SportsMeetingAppAdapter(List<AppInfoResp> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AppInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sports_meeting_app, parent, false);
        return new AppInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppInfoViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataList(List<AppInfoResp> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class AppInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView ivItemImg;
        private final TextView tvItemTitle;
        private final TextView tvItemEnter;

        public AppInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_sports_meeting_app);
            tvItemTitle = itemView.findViewById(R.id.tv_sports_meeting_app_title);
            tvItemEnter = itemView.findViewById(R.id.tv_sports_meeting_app_enter);
            tvItemEnter.setOnClickListener(this);
        }

        public void bind(AppInfoResp data){
            GlideApp.with(ivItemImg.getContext())
                    .load(data.getProductImg())
                    .into(ivItemImg);

            tvItemTitle.setText(data.getProductTitle());
            tvItemEnter.setText("进入活动");
            tvItemEnter.setTag(data);
        }

        @Override
        public void onClick(View v) {
            if(v.getTag() != null){
                AppInfoResp appInfoResp = (AppInfoResp) v.getTag();
                ActivityHelper.gotoDetailActivity(v.getContext(), appInfoResp.getSkuId());
            }
        }
    }
}
