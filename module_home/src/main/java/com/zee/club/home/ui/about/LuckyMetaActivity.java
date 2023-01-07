package com.zee.club.home.ui.about;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.AppInfoResp;
import com.zee.club.home.ui.about.adapter.LuckyMetaAdapter;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.ui.BaseActivity;

import java.util.List;

public class LuckyMetaActivity extends BaseActivity {

    private RecyclerView recyclerViewLuckyMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_meta);

        recyclerViewLuckyMeta = findViewById(R.id.recycler_view_lucky_meta);

        Bundle bundle = getIntent().getBundleExtra(BaseConstants.EXTRA_APP_INFO_LIST);
        if(bundle != null){
            List<AppInfoResp> appInfoRespList = (List<AppInfoResp>) bundle.getSerializable(BaseConstants.EXTRA_APP_INFO_LIST);
            recyclerViewLuckyMeta.setAdapter(new LuckyMetaAdapter(appInfoRespList));
        }
    }
}