package com.zee.club.user.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zee.club.user.R;

public class TypeSelectWindow extends PopupWindow {

    private Context activity;
    View contentView;//弹出框的根布局，可以监听其点击事件，达到点击阴影消失弹框的效果
    private TextView tvAll;
    private TextView tvNoYe;
    private TextView tvYet;
    private View.OnClickListener listener;

    public TypeSelectWindow(Context activity, View.OnClickListener listener) {
        this.activity = activity;
        this.listener = listener;
        initView();
    }

    private void initView() {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        contentView = LayoutInflater.from(activity).inflate(R.layout.join_popup_layout, null);
        this.setContentView(contentView);//设置布局
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        tvAll = (TextView) contentView.findViewById(R.id.tv_all);
        tvNoYe = (TextView) contentView.findViewById(R.id.tv_no_ye);
        tvYet = (TextView) contentView.findViewById(R.id.tv_yet);
        tvAll.setTag("所有,0");
        tvNoYe.setTag("未审批,1");
        tvYet.setTag("已审批,2");
        tvAll.setOnClickListener(listener);
        tvNoYe.setOnClickListener(listener);
        tvYet.setOnClickListener(listener);
    }

    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            showAsDropDown(parent);
        } else {
            dismissPopup();
        }
    }

    //消失
    public void dismissPopup() {
        super.dismiss();// 调用super.dismiss(),如果直接dismiss()会一直会调用下面的dismiss()
    }

    @Override
    public void dismiss() {
        dismissPopup();//动画执行完毕后消失
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}
