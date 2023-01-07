package com.zee.club.user.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.data.protocol.response.AssociationResp;

import java.util.List;

public class DrawCommAdapter extends RecyclerView.Adapter<DrawCommAdapter.ViewHolder> {
    private final List<AssociationResp> data;
    private OnItemClickListener listener;

    public List<AssociationResp> getData() {
        return data;
    }

    public DrawCommAdapter(List<AssociationResp> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_draw_community, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(holder.getAdapterPosition(), data.get(holder.getAdapterPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        //返回List的大小，使其全部显示
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivLeftHead;
        private TextView tvCommunityName;
        private ImageView ivSelected;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //定位要设置的控件
            ivLeftHead = (ImageView) itemView.findViewById(R.id.iv_left_head);
            tvCommunityName = (TextView) itemView.findViewById(R.id.tv_community_name);
            ivSelected = (ImageView) itemView.findViewById(R.id.iv_selected);
        }

        public void onBind(AssociationResp itemData) {
            ivSelected.setVisibility(itemData.isSelected() ? View.VISIBLE : View.INVISIBLE);
            ivLeftHead.setBackgroundResource(itemData.isSelected() ? R.mipmap.icon_draw_person : R.mipmap.icon_draw_person_sel);
            tvCommunityName.setText(itemData.getAssociationName());
            itemView.setBackgroundColor(itemData.isSelected() ? Color.parseColor("#6F59DA") : Color.TRANSPARENT);
        }
    }

    public interface OnItemClickListener {
        //在初始化时获得事件
        void onClick(int pos, AssociationResp data);
    }
}
