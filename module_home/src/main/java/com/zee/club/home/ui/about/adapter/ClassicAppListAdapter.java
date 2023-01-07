package com.zee.club.home.ui.about.adapter;

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

public class ClassicAppListAdapter extends RecyclerView.Adapter<ClassicAppListAdapter.LuckyMetaViewHolder> {
    private List<AppInfoResp> dataList;

    public ClassicAppListAdapter(List<AppInfoResp> dataList) {
        this.dataList = dataList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataList(List<AppInfoResp> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LuckyMetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classic_app_list_layout, parent, false);
        return new LuckyMetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LuckyMetaViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class LuckyMetaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView ivImageView;
        private final TextView tvTitle;
        private final TextView tvSummary;
        private final TextView tvLevel;

        public LuckyMetaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImageView = itemView.findViewById(R.id.iv_classic_app_list);
            tvTitle = itemView.findViewById(R.id.tv_classic_app_list_title);
            tvSummary = itemView.findViewById(R.id.tv_classic_app_list_summary);
            tvLevel = itemView.findViewById(R.id.tv_classic_app_list_level);
            itemView.setOnClickListener(this);
        }

        public void bind(AppInfoResp data){
            GlideApp.with(ivImageView.getContext())
                    .load(data.getProductImg())
                    .into(ivImageView);

            tvTitle.setText(data.getProductTitle());
            itemView.setTag(data);
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
