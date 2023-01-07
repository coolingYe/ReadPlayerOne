package com.zee.club.home.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zee.club.home.R;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.widgets.CustomActionBar;

public class SearchActivity extends BaseActivity {

    private EditText tvSearchBox;
    private TextView  tvSearchButton;
    private ImageView ivSearchIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        CustomActionBar customActionBar = findViewById(R.id.cab_search_header);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_search_box_view, null, false);
        tvSearchBox = view.findViewById(R.id.tv_search_box);
        ivSearchIcon = view.findViewById(R.id.iv_search_icon);
        tvSearchButton = view.findViewById(R.id.tv_search_button);
        customActionBar.addContentView(view);

        tvSearchBox.setEnabled(false);

    }
}
