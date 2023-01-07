package com.zeewain.base.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.zeewain.base.R;

public class CustomActionBar extends ConstraintLayout {
    private ImageView imgBack, imgFilter;
    private TextView tvTitle;
    private FrameLayout flContent;
    public CustomActionBar(@NonNull Context context) {
        this(context, null);
    }

    public CustomActionBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomActionBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomActionBar);
        String title = typedArray.getString(R.styleable.CustomActionBar_title);
        typedArray.recycle();
        initView(context, title);
        initListener();
    }

    private void initView(Context context, String title){
        LayoutInflater.from(context).inflate(R.layout.layout_custom_action_bar, this);
        imgBack = findViewById(R.id.img_action_bar_back);
        tvTitle = findViewById(R.id.tv_action_bar_title);
        flContent = findViewById(R.id.fl_action_bar_content);
        imgFilter = findViewById(R.id.img_action_bar_filter);
        if(title != null){
            tvTitle.setText(title);
        }
    }

    private void initListener(){
        imgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getContext() instanceof AppCompatActivity){
                    ((AppCompatActivity)v.getContext()).finish();
                }
            }
        });
    }

    public void setTitle(String title) {
        if(tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void addContentView(View view){
        if(flContent != null){
            tvTitle.setVisibility(View.GONE);
            flContent.setVisibility(View.VISIBLE);
            flContent.addView(view);
        }
    }

    public void setImgFilterVisibility(int stateId) {
        imgFilter.setVisibility(stateId);
    }


}
