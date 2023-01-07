package com.zee.club.home.ui.userstyle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.RankingCompanyResp;
import com.zee.club.home.widget.CardListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class CompanyRankingAdapter extends CardListViewAdapter<RankingCompanyResp.RankingCompany, CompanyRankingAdapter.PowerCardListViewHolder> {

    private List<RankingCompanyResp.RankingCompany> dataList = new ArrayList<>();
    public OnClickItemListener onClickItemListener;

    public CompanyRankingAdapter(List<RankingCompanyResp.RankingCompany> dataList) {
        super(dataList);
    }

    @NonNull
    @Override
    public PowerCardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_list_power_ranking, parent, false);
        return new PowerCardListViewHolder(view);
    }

    @Override
    public PowerCardListViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindView(PowerCardListViewHolder holder, RankingCompanyResp.RankingCompany data, int position) {
        holder.bind(data);
    }

    static class PowerCardListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivCompanyTop;
        private final TextView tvCompanyTop;
        private final TextView tvCompanyName;
        private final ProgressBar progressPower;
        private final TextView tvPowerNumber;

        public PowerCardListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCompanyTop = itemView.findViewById(R.id.iv_company_top);
            tvCompanyName = itemView.findViewById(R.id.tv_company_name);
            progressPower = itemView.findViewById(R.id.progress_power);
            tvPowerNumber = itemView.findViewById(R.id.tv_power_value);
            tvCompanyTop = itemView.findViewById(R.id.tv_company_top);
        }

        public void bind(RankingCompanyResp.RankingCompany data) {
            itemView.setTag(data);
            switch (data.getRank()) {
                case 1:
                    ivCompanyTop.setBackgroundResource(R.mipmap.ic_gold_medal);
                    break;
                case 2:
                    ivCompanyTop.setBackgroundResource(R.mipmap.ic_silver_medal);
                    break;
                case 3:
                    ivCompanyTop.setBackgroundResource(R.mipmap.ic_bronze_medal);
                    break;
                default:
                    ivCompanyTop.setVisibility(View.GONE);
                    tvCompanyTop.setVisibility(View.VISIBLE);
                    tvCompanyTop.setText(String.valueOf(data.getRank()));
            }

            tvCompanyName.setText(data.getEnterpriseName());
            progressPower.setProgress(data.getEnergyScore());
            tvPowerNumber.setText(String.valueOf(data.getEnergyScore()));
        }
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClick(View view, RankingCompanyResp.RankingCompany data);
    }
}
