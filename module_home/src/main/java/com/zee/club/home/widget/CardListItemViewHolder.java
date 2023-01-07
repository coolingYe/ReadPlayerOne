package com.zee.club.home.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CardListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public CardListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
