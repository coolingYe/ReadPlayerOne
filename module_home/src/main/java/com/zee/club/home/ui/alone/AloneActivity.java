package com.zee.club.home.ui.alone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zee.club.home.R;
import com.zee.club.home.ui.home.HomeFragment;

public class AloneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alone);

        HomeFragment fragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home_content, fragment).commit();
    }
}