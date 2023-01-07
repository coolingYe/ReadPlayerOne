package com.zee.club.home.ui.about.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zeewain.base.utils.GlideApp;

import java.util.List;

public class LuckyMetaAdapter extends RecyclerView.Adapter<LuckyMetaAdapter.LuckyMetaViewHolder> {
    private List<AppInfoResp> dataList;

    public LuckyMetaAdapter(List<AppInfoResp> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public LuckyMetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lucky_meta_layout, parent, false);
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
        private final ImageView ivLuckyMeta;
        private final TextView tvLuckyMetaTitle;
        private final TextView tvLuckyMetaEnter;

        public LuckyMetaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLuckyMeta = itemView.findViewById(R.id.iv_lucky_meta);
            tvLuckyMetaTitle = itemView.findViewById(R.id.tv_lucky_meta_title);
            tvLuckyMetaEnter = itemView.findViewById(R.id.tv_lucky_meta_enter);
            tvLuckyMetaEnter.setOnClickListener(this);
        }

        public void bind(AppInfoResp data){
            GlideApp.with(ivLuckyMeta.getContext())
                    .load(data.getProductImg())
                    .into(ivLuckyMeta);

            tvLuckyMetaTitle.setText(data.getProductTitle());
            tvLuckyMetaEnter.setTag(data);
        }

        @Override
        public void onClick(View v) {
            if(v.getTag() != null){
                AppInfoResp appInfoResp = (AppInfoResp) v.getTag();

            }
        }
    }
}
