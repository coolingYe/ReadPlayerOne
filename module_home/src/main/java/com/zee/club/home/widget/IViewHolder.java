package com.zee.club.home.widget;

import android.view.ViewGroup;

public interface IViewHolder<T, VH> {

    VH onCreateHolder(ViewGroup parent, int viewType);

    void onBindView(VH holder, T data, int position);
}
