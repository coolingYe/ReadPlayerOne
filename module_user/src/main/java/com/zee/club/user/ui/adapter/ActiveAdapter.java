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
import com.zee.club.user.data.protocol.response.ActiveResp;

import java.util.List;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> {


    private final List<ActiveResp> data;

    public ActiveAdapter(List<ActiveResp> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }


    @Override
    public int getItemCount() {
        //返回List的大小，使其全部显示
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvActiveTitle;
        private ImageView ivActiveIcon;
        private TextView tvActionStatue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //定位要设置的控件
            tvActiveTitle = (TextView) itemView.findViewById(R.id.tv_active_title);
            ivActiveIcon = (ImageView) itemView.findViewById(R.id.iv_active_icon);
            tvActionStatue = (TextView) itemView.findViewById(R.id.tv_active_statue);
        }

        public void onBind(ActiveResp itemData) {

        }

        @Override
        public void onClick(View view) {

        }
    }
}