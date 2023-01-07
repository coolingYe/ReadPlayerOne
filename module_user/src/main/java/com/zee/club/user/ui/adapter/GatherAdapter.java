package com.zee.club.user.ui.adapter;

import static com.zeewain.base.utils.ActivityHelper.navigateToArticleDetailsPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.protocol.response.GatherInfoResp;
import com.zeewain.base.utils.GlideApp;

import java.util.List;

public class GatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //定义所需传入的参数
    private List<GatherInfoResp> dataList;
    private String viewType;

    //初始化Adapter时传入相关参数
    public GatherAdapter(List<GatherInfoResp> dataList, String viewType) {
        this.dataList = dataList;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, parent, false);
            return new BrowseViewHolder(view);
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, parent, false);
            return new PraiseViewHolder(view);
        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, parent, false);
            return new CollectViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CollectViewHolder) {
            ((CollectViewHolder) holder).bind(dataList.get(position));
        } else if (holder instanceof BrowseViewHolder) {
            ((BrowseViewHolder) holder).bind(dataList.get(position));
        } else if (holder instanceof PraiseViewHolder) {
            ((PraiseViewHolder) holder).bind(dataList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (viewType) {
            case UserConstants.Gather_Type_Browse:
                return 0;
            case UserConstants.Gather_Type_Thumbs:
                return 1;
            case UserConstants.Gather_Type_Collect:
                return 2;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    class CollectViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivPic;
        private TextView tvTime;


        public CollectViewHolder(@NonNull View itemView) {
            super(itemView);
            //定位要设置的控件
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void bind(GatherInfoResp data) {
            GlideApp.with(ivPic.getContext())
                    .load(data.getObjUrl())
                    .into(ivPic);
            tvTitle.setText(data.getObjName());
            tvTime.setText(data.getFavoriteTime());
            itemView.setOnClickListener(v -> {
                navigateToArticleDetailsPage(v.getContext(), new String[]{ data.getObjId(), String.valueOf(data.getObjType()), data.getObjName()});
            });
        }
    }

    class BrowseViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivPic;
        private TextView tvTime;


        public BrowseViewHolder(@NonNull View itemView) {
            super(itemView);
            //定位要设置的控件
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void bind(GatherInfoResp data) {
            GlideApp.with(ivPic.getContext())
                    .load(data.getObjUrl())
                    .into(ivPic);
            tvTitle.setText(data.getObjName());
            tvTime.setText(data.getBrowseTime());
            itemView.setOnClickListener(v -> {
                navigateToArticleDetailsPage(v.getContext(), new String[]{ data.getObjId(), String.valueOf(data.getObjType()), data.getObjName()});
            });
        }
    }

    class PraiseViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivPic;
        private TextView tvTime;


        public PraiseViewHolder(@NonNull View itemView) {
            super(itemView);
            //定位要设置的控件
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void bind(GatherInfoResp data) {
            GlideApp.with(ivPic.getContext())
                    .load(data.getObjUrl())
                    .into(ivPic);
            tvTitle.setText(data.getObjName());
            tvTime.setText(data.getPraiseTime());
            itemView.setOnClickListener(v -> {
                navigateToArticleDetailsPage(v.getContext(), new String[]{ data.getObjId(), String.valueOf(data.getObjType()), data.getObjName()});
            });
        }
    }
}