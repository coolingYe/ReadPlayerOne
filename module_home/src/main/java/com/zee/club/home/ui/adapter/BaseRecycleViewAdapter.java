package com.zee.club.home.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;

import java.util.List;

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public OnClickItemListener<T> onClickItemListener;
    public OnClickFooterItemListener onClickFooterItemListener;
    private Boolean isShowFooterView = false;
    public List<T> dataList;
    private RecyclerView recyclerView;
    private static final int TYPE_NORMAL = 0x000001;
    private static final int TYPE_FOOTER = 0x000002;

    protected abstract RecyclerView.ViewHolder onCreateViewHolderChild(ViewGroup parent, int viewType);

    protected abstract void onBindViewHolderChild(RecyclerView.ViewHolder holder, int position);

    protected abstract int getItemViewTypeChild(int position);

    public BaseRecycleViewAdapter(List<T> dataList) {
        setData(dataList);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void isShowFooterView(Boolean isShowFooterView) {
        this.isShowFooterView = isShowFooterView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
            return new FooterViewHolder(view);
        } else {
            return onCreateViewHolderChild(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).Bind(onClickFooterItemListener);
        } else onBindViewHolderChild(holder, position);
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public void Bind(OnClickFooterItemListener onClickFooterItemListener) {
            textView.setText("清华AI使能·紫为云");
            itemView.setOnClickListener(v -> {
                onClickFooterItemListener.onClick(imageView);
            });
        }

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_footer);
            textView = itemView.findViewById(R.id.tv_footer_title);
        }
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        } else return isShowFooterView ? dataList.size() + 1 : dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && isShowFooterView) {
            return TYPE_FOOTER;
        } else {
            return getItemViewTypeChild(position);
        }
    }

    public void setOnClickItemListener(OnClickItemListener<T> onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener<T> {
        void onClick(View view, T data);
    }

    public void setOnClickFooterItemListener(OnClickFooterItemListener onClickFooterItemListener) {
        this.onClickFooterItemListener = onClickFooterItemListener;
    }

    public interface OnClickFooterItemListener {
        void onClick(View view);
    }

}
