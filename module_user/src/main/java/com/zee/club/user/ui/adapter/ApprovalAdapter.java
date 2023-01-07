package com.zee.club.user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zee.club.user.R;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.protocol.response.ApprovalResp;
import com.zee.club.user.ui.mine.ExamineActivity;
import com.zeewain.base.utils.GlideApp;

import java.util.List;

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ViewHolder> {

    private List<ApprovalResp> data;

    public ApprovalAdapter(List<ApprovalResp> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approval, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView tvApprovalTitle;
        private ImageView ivApprovalHead;
        private TextView tvApprovalStatue;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvApprovalTitle = (TextView) itemView.findViewById(R.id.tv_approval_title);
            ivApprovalHead = (ImageView) itemView.findViewById(R.id.iv_approval_head);
            tvApprovalStatue = (TextView) itemView.findViewById(R.id.tv_approval_statue);
        }

        public void onBind(ApprovalResp itemData) {

            itemView.setTag(itemData);
//            GlideApp.with(ivApprovalHead.getContext())
//                    .load(itemData.getObjId())
//                    .into(ivApprovalHead);
            tvApprovalTitle.setText(itemData.getOrderName());
            String statueString = "";
            switch (itemData.getOrderStatus()) {
                case UserConstants.Order_Check:
                    tvApprovalStatue.setTextColor(Color.parseColor("#6F59DA"));
                    statueString = "去处理";
                    break;
                case UserConstants.Order_accept:
                case UserConstants.Order_reject:
                    statueString = "已处理";
                    break;
            }
            tvApprovalStatue.setText(statueString);
            if (itemData.getOrderStatus() == UserConstants.Order_Check) itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view.getTag() != null) {
                ApprovalResp itemData = (ApprovalResp) view.getTag();
                Intent intent = new Intent(view.getContext(), ExamineActivity.class);
                intent.putExtra(UserConstants.Approve_Order_Id,itemData.getOrderId());
                intent.putExtra(UserConstants.Approve_Order_Name,itemData.getOrderName());
                view.getContext().startActivity(intent);
            }
        }
    }


}