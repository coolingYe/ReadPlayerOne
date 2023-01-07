package com.zee.club.home.widget;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class CardListViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IViewHolder<T, VH> {
    private List<T> dataList = new ArrayList<>();
    private VH viewHolder;
    private OnCardListItemClickListener onCardListItemClickListener;

    public CardListViewAdapter(List<T> dataList) {
        setDataList(dataList);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH vh = onCreateHolder(parent, viewType);
        vh.itemView.setOnClickListener(v -> {
            Log.i("test", "---onItemClick--v-1111111111-------");
            if (onCardListItemClickListener != null) {
                Log.i("test", "---onItemClick--v-22222222222-------");
                onCardListItemClickListener.onItemClick(v);
            }
        });
        return vh;
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        viewHolder = holder;
        holder.itemView.setTag(dataList.get(position));
        onBindView(holder, dataList.get(position), position);
        if (onCardListItemClickListener != null) {
            holder.itemView.setOnClickListener(view -> onCardListItemClickListener.onItemClick(view));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 :dataList.size();
    }

    public void setOnCardListItemClickListener(OnCardListItemClickListener onCardListItemClickListener) {
        Log.i("test", "---onItemClick--v-setOnCardListItemClickListener-------");
        this.onCardListItemClickListener = onCardListItemClickListener;
    }
}
