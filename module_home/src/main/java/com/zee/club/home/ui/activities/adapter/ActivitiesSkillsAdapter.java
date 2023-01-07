package com.zee.club.home.ui.activities.adapter;

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

public class ActivitiesSkillsAdapter extends RecyclerView.Adapter<ActivitiesSkillsAdapter.SkillsViewHolder> {
    private List<AppInfoResp> dataList;

    public ActivitiesSkillsAdapter(List<AppInfoResp> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SkillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activities_skills_layout, parent, false);
        return new SkillsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillsViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class SkillsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView ivItemImg;
        private final TextView tvItemTitle;
        private final TextView tvItemDuration;
        private final TextView tvItemLevel;

        public SkillsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImg = itemView.findViewById(R.id.iv_activities_skills);
            tvItemTitle = itemView.findViewById(R.id.tv_activities_skills_title);
            tvItemDuration = itemView.findViewById(R.id.tv_activities_skills_duration);
            tvItemLevel = itemView.findViewById(R.id.tv_activities_skills_level);
            itemView.setOnClickListener(this);
        }

        public void bind(AppInfoResp data){
            GlideApp.with(ivItemImg.getContext())
                    .load(data.getProductImg())
                    .into(ivItemImg);

            tvItemTitle.setText(data.getProductTitle());
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
