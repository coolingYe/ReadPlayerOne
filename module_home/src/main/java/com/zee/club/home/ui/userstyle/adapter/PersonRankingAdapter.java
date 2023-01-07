package com.zee.club.home.ui.userstyle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.RankingPeopleResp;
import com.zee.club.home.widget.CardListViewAdapter;
import com.zeewain.base.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;


public class PersonRankingAdapter extends CardListViewAdapter<RankingPeopleResp.RankingPeople, RecyclerView.ViewHolder> {

    private List<RankingPeopleResp.RankingPeople> dataList = new ArrayList<>();
    public OnClickItemListener onClickItemListener;
    public int TYPE_KEY = 0;
    public static final int TYPE_PERSON_ONE = 0;
    public static final int TYPE_PERSON_TWO = 1;

    public PersonRankingAdapter(List<RankingPeopleResp.RankingPeople> dataList, int type) {
        super(dataList);
        this.TYPE_KEY = type;
    }

    @Override
    public int getItemViewType(int position) {
        if (TYPE_KEY == TYPE_PERSON_ONE) {
            return TYPE_PERSON_ONE;
        } else {
            return TYPE_PERSON_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PERSON_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_list_great_person, parent, false);
            return new UserTopCardListViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_great_person, parent, false);
            return new UserTopNextListViewHolder(view);
        }
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, RankingPeopleResp.RankingPeople data, int position) {
        if (holder instanceof UserTopCardListViewHolder) {
            ((UserTopCardListViewHolder) holder).bind(data);
        } else {
            ((UserTopNextListViewHolder) holder).bind(data);
        }
    }

    static class UserTopCardListViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout layoutUserTopBg;
        private final LinearLayout layoutUserAvatarFrame;
        private final ImageView ivUserAvatar;
        private final TextView tvUserName;
        private final TextView tvUserGroup;
        private final LinearLayout layoutPowerBg;
        private final TextView tvPowerNumber;
        private final TextView tvPowerDesc;

        public UserTopCardListViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutUserTopBg = itemView.findViewById(R.id.layout_user_top);
            layoutUserAvatarFrame = itemView.findViewById(R.id.layout_avatar_frame);
            ivUserAvatar = itemView.findViewById(R.id.iv_user_avatar);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserGroup = itemView.findViewById(R.id.tv_group);
            layoutPowerBg = itemView.findViewById(R.id.layout_power_bg);
            tvPowerNumber = itemView.findViewById(R.id.tv_power_value);
            tvPowerDesc = itemView.findViewById(R.id.tv_power_desc);
        }

        public void bind(RankingPeopleResp.RankingPeople data) {
            itemView.setTag(data);
            if (false) {
                GlideApp.with(ivUserAvatar.getContext())
                        .load(data.getPic())
                        .into(ivUserAvatar);
            } else ivUserAvatar.setBackgroundResource(R.mipmap.ic_user_head);
            tvUserName.setText(data.getUserName());
            tvUserGroup.setText(data.getEnterpriseName());
            tvPowerNumber.setText(String.valueOf(data.getEnergyScore()));
            tvPowerDesc.setText("贡献值");
            if (data.getRank() == 1) {
                layoutUserTopBg.setBackgroundResource(R.drawable.ic_rectangle_top1_background);
                layoutUserAvatarFrame.setBackgroundResource(R.mipmap.ic_avatar_frame_top_1);
                layoutPowerBg.setBackgroundResource(R.drawable.ic_ellipse_top1_backgorund);
            } else {
                layoutUserTopBg.setBackgroundResource(R.drawable.ic_rectangle_background);
                layoutPowerBg.setBackgroundResource(R.drawable.ic_ellipse_background);
            }
        }
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClick(View view, RankingPeopleResp.RankingPeople data);
    }

    static class UserTopNextListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivUserAvatar;
        private final TextView tvPersonName;
        private final TextView tvCompanyName;
        private final TextView tvPowerValue;
        private final TextView tvTopNumber;

        public UserTopNextListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserAvatar = itemView.findViewById(R.id.iv_avatar);
            tvPersonName = itemView.findViewById(R.id.tv_person_name);
            tvCompanyName = itemView.findViewById(R.id.tv_company_name);
            tvPowerValue = itemView.findViewById(R.id.tv_power_value);
            tvTopNumber = itemView.findViewById(R.id.tv_top_number);
        }

        public void bind(RankingPeopleResp.RankingPeople data) {
            itemView.setTag(data);
            if (false) {
                GlideApp.with(ivUserAvatar.getContext())
                        .load(data.getPic())
                        .into(ivUserAvatar);
            } else ivUserAvatar.setBackgroundResource(R.mipmap.ic_user_head);
            tvTopNumber.setText(String.valueOf(data.getRank()));
            tvPersonName.setText(data.getUserName());
            tvCompanyName.setText(data.getEnterpriseName());
            tvPowerValue.setText(String.valueOf(data.getEnergyScore()));
        }
    }
}
