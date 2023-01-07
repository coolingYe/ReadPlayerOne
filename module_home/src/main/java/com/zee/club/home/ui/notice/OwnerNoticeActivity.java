package com.zee.club.home.ui.notice;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zee.club.home.R;
import com.zee.club.home.data.protocol.response.ArticleInfoResp;
import com.zee.club.home.ui.notice.adapter.OwnerNoticeAdapter;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.ui.BaseActivity;

import java.util.List;

public class OwnerNoticeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_notice);

        RecyclerView recyclerViewOwnerNotice = findViewById(R.id.recycler_view_owner_notice);
        Bundle bundle = getIntent().getBundleExtra(BaseConstants.EXTRA_OWNER_NOTICE_LIST);
        if(bundle != null){
            List<ArticleInfoResp> articleInfoRespList = (List<ArticleInfoResp>) bundle.getSerializable(BaseConstants.EXTRA_OWNER_NOTICE_LIST);
            recyclerViewOwnerNotice.setAdapter(new OwnerNoticeAdapter(articleInfoRespList));
        }



    }
}