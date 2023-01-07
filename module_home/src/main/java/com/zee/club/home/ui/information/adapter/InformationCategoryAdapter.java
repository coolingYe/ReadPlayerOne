package com.zee.club.home.ui.information.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.adapter.BaseRecycleViewAdapter;
import com.zeewain.base.utils.GlideApp;

import java.util.List;

public class InformationCategoryAdapter extends BaseRecycleViewAdapter<ArticleInfoResp> {

    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_TEXT = 0;

    public InformationCategoryAdapter(List<ArticleInfoResp> dataList) {
        super(dataList);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolderChild(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_information_text, parent, false);
            return new NormalItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_infomation_video, parent, false);
            return new VideoItemViewHolder(view);
        }
    }

    @Override
    protected void onBindViewHolderChild(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalItemViewHolder) {
            ((NormalItemViewHolder) holder).Bind(dataList.get(position), position);
        } else if (holder instanceof VideoItemViewHolder) {
            ((VideoItemViewHolder) holder).Bind(dataList.get(position));
        }
    }

    @Override
    protected int getItemViewTypeChild(int position) {
        if (true) {
            return TYPE_TEXT;
        } else return TYPE_VIDEO;
    }

    class NormalItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final ImageView imageView;
        private final TextView textRead;
        private final TextView textDate;
        private final ConstraintLayout layoutItem;
        private final View viewLine;

        public void Bind(ArticleInfoResp data, int position) {
            textTitle.setText(data.title);
            textRead.setText("");
            textDate.setText(data.updateTime);
            imageView.setTag(data);
            GlideApp.with(imageView.getContext())
                    .load(data.covers)
                    .into(imageView);

            if (position == dataList.size() - 1) {
                viewLine.setVisibility(View.GONE);
            }

            layoutItem.setOnClickListener(view -> {
                if (onClickItemListener != null) {
                    onClickItemListener.onClick(view, data);
                }
            });
        }

        public NormalItemViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_information_view);
            textTitle = itemView.findViewById(R.id.tv_information_title);
            imageView = itemView.findViewById(R.id.iv_info_img);
            textRead = itemView.findViewById(R.id.tv_have_read);
            textDate = itemView.findViewById(R.id.tv_information_date);
            viewLine = itemView.findViewById(R.id.view_line);
        }
    }

    static class VideoItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final ImageView imageView;
        private final TextView textRead;
        private final TextView textDate;

        public void Bind(ArticleInfoResp data) {
            textTitle.setText(data.title);
            textRead.setText("");
            textDate.setText(data.updateTime);
            imageView.setTag(data);
            GlideApp.with(imageView.getContext())
                    .load(data.covers)
                    .into(imageView);
        }

        public VideoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tv_information_title);
            imageView = itemView.findViewById(R.id.iv_info_img);
            textRead = itemView.findViewById(R.id.tv_have_read);
            textDate = itemView.findViewById(R.id.tv_information_date);
        }
    }

}
