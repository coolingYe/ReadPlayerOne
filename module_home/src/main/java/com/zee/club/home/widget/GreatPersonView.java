package com.zee.club.home.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zee.club.home.R;

public class GreatPersonView extends LinearLayout {

    private ImageView ivAvatar;
    private TextView tvPersonName, tvPowerValue;

    public GreatPersonView(Context context) {
        super(context);
    }

    public GreatPersonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GreatPersonView);
        CharSequence personNameText = typedArray.getText(R.styleable.GreatPersonView_personNameText);
        CharSequence powerNumberText = typedArray.getText(R.styleable.GreatPersonView_powerValueText);
        int powerNumberTextColor = typedArray.getColor(R.styleable.GreatPersonView_powerValueTextColor, 0);
        int personNameTextColor = typedArray.getColor(R.styleable.GreatPersonView_personNameTextColor, 0);
        int avatarFrameImage = typedArray.getResourceId(R.styleable.GreatPersonView_avatarFrameImage, 0);
        int avatarImage = typedArray.getResourceId(R.styleable.GreatPersonView_avatarImage, 0);
        typedArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.layout_great_person_view, this);
        LinearLayout layoutAvatarFrame = findViewById(R.id.layout_avatar_frame);
        ivAvatar = findViewById(R.id.iv_avatar);
        tvPersonName = findViewById(R.id.tv_person_name);
        tvPowerValue = findViewById(R.id.tv_power_value);

        if (personNameText != null) tvPersonName.setText(personNameText);
        if (powerNumberText != null) tvPowerValue.setText(powerNumberText);
        if (avatarFrameImage != 0) layoutAvatarFrame.setBackgroundResource(avatarFrameImage);
        if (avatarImage != 0) ivAvatar.setBackgroundResource(avatarImage);
        if (personNameTextColor != 0) tvPersonName.setTextColor(personNameTextColor);
        if (powerNumberTextColor != 0) tvPowerValue.setTextColor(powerNumberTextColor);
    }

    public GreatPersonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(String personName, String powerValue, int imageRes) {
        tvPersonName.setText(personName);
        tvPowerValue.setText(powerValue);
        ivAvatar.setImageResource(imageRes);
    }

}
