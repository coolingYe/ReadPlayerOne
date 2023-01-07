package com.zeewain.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.zeewain.base.R;

public class BackToTopView extends ConstraintLayout {
    private OnBackToTopListener onBackToTopListener;

    public BackToTopView(@NonNull Context context) {
        this(context, null);
    }

    public BackToTopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackToTopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_back_to_top_view, this);
        ImageView ivBackToTop = findViewById(R.id.iv_back_to_top_back);
        ivBackToTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onBackToTopListener != null){
                    onBackToTopListener.onBackToTopClick(v);
                }else{
                    ViewParent parent = getParent();
                    while (parent != null && !(parent instanceof NestedScrollView)) {
                        parent = parent.getParent();
                    }
                    if(parent != null) {
                        NestedScrollView parentScrollView = (NestedScrollView)parent;
                        parentScrollView.fullScroll(View.FOCUS_UP);
                    }
                }
            }
        });
    }

    public interface OnBackToTopListener{
        void onBackToTopClick(View v);
    }
}
