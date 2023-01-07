package com.zee.club.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.data.protocol.response.AssociationResp;

import java.util.List;

public class SelectIdeAdapter extends RecyclerView.Adapter<SelectIdeAdapter.ViewHolder> {

    //定义所需传入的参数
    private List<AssociationResp> data;
    private Context context;
    private OnItemClickListener listener;
    private int position;

    //初始化Adapter时传入相关参数
    public SelectIdeAdapter(List<AssociationResp> data, Context context, OnItemClickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sel_ide, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
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
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //定位要设置的控件
            tv_content = itemView.findViewById(R.id.tv_content);
        }

        private void bind(AssociationResp data) {
            tv_content.setText(data.getAssociationName());
        }
    }

    public interface OnItemClickListener {
        //在初始化时获得事件
        void onClick(int pos, AssociationResp data);

        void onLongClick(int pos, AssociationResp data);
    }
}