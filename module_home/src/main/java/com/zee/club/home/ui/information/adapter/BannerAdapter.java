package com.zee.club.home.ui.information.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.article.ArticleDetailActivity;
import com.zeewain.base.utils.GlideApp;

import java.util.List;

public class BannerAdapter extends com.youth.banner.adapter.BannerAdapter<ArticleInfoResp, BannerAdapter.BannerViewHolder> {
    private boolean showTitleSummary;

    public BannerAdapter(List<ArticleInfoResp> dataList) {
        super(dataList);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateDataList(List<ArticleInfoResp> dataList){
        setDatas(dataList);
        notifyDataSetChanged();
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classic_banner_layout, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindView(BannerViewHolder holder, ArticleInfoResp data, int position, int size) {
        holder.bind(data, showTitleSummary);
    }

    public void setShowTitleSummary(boolean showTitleSummary) {
        this.showTitleSummary = showTitleSummary;
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView txtClassicBannerTitle;
        private final TextView txtClassicBannerSummary;
        private final ImageView imgClassicBanner;

        public void bind(ArticleInfoResp data, boolean showTitleSummary){
            imgClassicBanner.setTag(data);
            GlideApp.with(imgClassicBanner.getContext())
                    .load(data.covers)
                    .into(imgClassicBanner);
        }

        public BannerViewHolder(View view) {
            super(view);
            txtClassicBannerTitle = view.findViewById(R.id.txt_classic_banner_title);
            txtClassicBannerSummary = view.findViewById(R.id.txt_classic_banner_summary);
            imgClassicBanner = view.findViewById(R.id.img_classic_banner);
            imgClassicBanner.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getTag() != null){
                ArticleInfoResp data = (ArticleInfoResp)v.getTag();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ArticleInfo", data);
                intent.putExtra("ArticleInfo", bundle);
                intent.setClass(v.getContext(), ArticleDetailActivity.class);
                v.getContext().startActivity(intent);

            }
        }
    }
}
