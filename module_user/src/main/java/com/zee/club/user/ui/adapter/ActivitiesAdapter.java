package com.zee.club.user.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.data.protocol.response.ActivitiesResp;

import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private List<ActivitiesResp> dataList;


    public ActivitiesAdapter(List<ActivitiesResp> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activities_layout, parent, false);
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
        private ImageView ivActImage;
        private TextView tvActTitle;
        private TextView tvActJoinNumber;
        private TextView tvActTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivActImage = (ImageView) itemView.findViewById(R.id.iv_act_image);
            tvActTitle = (TextView) itemView.findViewById(R.id.tv_act_title);
            tvActJoinNumber = (TextView) itemView.findViewById(R.id.tv_act_join_number);
            tvActTime = (TextView) itemView.findViewById(R.id.tv_act_time);
            itemView.setOnClickListener(this);
        }

        public void bind(ActivitiesResp data) {
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
                ActivitiesResp appInfoResp = (ActivitiesResp) v.getTag();
            }
        }
    }
}
