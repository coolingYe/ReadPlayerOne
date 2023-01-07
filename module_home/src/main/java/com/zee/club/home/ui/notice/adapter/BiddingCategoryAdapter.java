package com.zee.club.home.ui.notice.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.model.BiddingCategoryMo;
import com.zeewain.base.utils.DisplayUtil;

import java.util.List;


public class BiddingCategoryAdapter extends RecyclerView.Adapter<BiddingCategoryAdapter.CategoryViewHolder> {

    private final List<BiddingCategoryMo> dataList;
    private int selectedIndex = 0;
    private OnItemSelectedListener onItemSelectedListener;

    public BiddingCategoryAdapter(List<BiddingCategoryMo> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bidding_catetory_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tvItemTitle;
        private final ImageView tvItemImg;
        private final View viewIndex;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.iv_bidding_category_title);
            tvItemImg = itemView.findViewById(R.id.iv_bidding_category);
            viewIndex = itemView.findViewById(R.id.view_bidding_category_index);
        }

        public void bind(BiddingCategoryMo data, int position){
            itemView.setTag(position);
            tvItemImg.setImageResource(data.resId);
            tvItemTitle.setText(data.title);
            if(selectedIndex == position){
                tvItemTitle.setTextColor(0xFF333333);
                tvItemTitle.setTextSize(14);
                viewIndex.setVisibility(View.VISIBLE);
            }else{
                tvItemTitle.setTextColor(0xFF666666);
                tvItemTitle.setTextSize(12);
                viewIndex.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getTag() != null && onItemSelectedListener != null){
                int position = (int)v.getTag();
                onItemSelectedListener.onItemSelected(dataList.get(position), position);
            }
        }
    }

    public interface OnItemSelectedListener{
        void onItemSelected(BiddingCategoryMo data, int position);
    }

}
