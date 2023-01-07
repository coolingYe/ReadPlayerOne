package com.zee.club.user.ui.bind;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.UserRepository;
import com.zee.club.user.data.protocol.request.BindCommunityReq;
import com.zee.club.user.data.protocol.request.JoinApplyReq;
import com.zee.club.user.data.protocol.request.MemberReq;
import com.zee.club.user.data.protocol.response.EnterpriseResp;
import com.zee.club.user.data.protocol.response.MemberResp;
import com.zee.club.user.ui.login.BindStatusActivity;
import com.zee.club.user.ui.login.ForgetPasswordActivity;
import com.zee.club.user.ui.login.SelectIdeActivity;
import com.zee.club.user.ui.mine.TempMainActivity;
import com.zee.club.user.ui.view.CommonPopup;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.utils.SPUtils;
import com.zeewain.base.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BindCommunityActivity extends BaseActivity {

    public boolean isDestory;
    private BindCommViewModel viewModel;

    private ImageView ivBack;
    private TextView tvTitle;
    private EditText etSelCom;
    private TextView tvComName;
    private TextView tvSelIde;
    private ImageView ivSelIde;
    private ImageView ivSelQiYe;
    private LinearLayout llIdePerson;
    private EditText etPerfName;
    private LinearLayout llIdeEnt;
    private EditText etEntName;
    private EditText etEntType;
    private LinearLayout llIdeEntPer;
    private TextView tvSelEntName;
    private EditText etPersName;
    private EditText etPhone;
    private EditText etCap;
    private EditText etApplyName;
    private Button btnNext;
    private RelativeLayout rlIdeId;
    private RelativeLayout rlNameId;
    private RelativeLayout rlPhone;
    private RelativeLayout rlQiYe;
    private RelativeLayout rlQiYeYuan;
    private RelativeLayout rlYuanName;
    private RelativeLayout rlApplyName;
    private TextView tvCap;

    private CommonPopup commonPopupFirst;
    private CommonPopup commonPopupSecond;
    private CommonPopup commonPopupThird;

    private int objType = 0;  // 对象类型 1-社团员工 2-企业 3-企业员工
    private int associationId = 0;  // 	社团Id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(BindCommViewModel.class);
        setContentView(R.layout.activity_bind_comm);
        initViewObservable();
        initView();
        initViewListener();
        initData();
    }


    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        etSelCom = (EditText) findViewById(R.id.et_sel_com);
        tvComName = (TextView) findViewById(R.id.tv_com_name);
        tvSelIde = (TextView) findViewById(R.id.tv_sel_ide);
        ivSelIde = (ImageView) findViewById(R.id.iv_sel_ide);
        llIdePerson = (LinearLayout) findViewById(R.id.ll_ide_person);
        etPerfName = (EditText) findViewById(R.id.et_perf_name);
        llIdeEnt = (LinearLayout) findViewById(R.id.ll_ide_ent);
        etEntName = (EditText) findViewById(R.id.tv_ent_name);
        etEntType = (EditText) findViewById(R.id.et_ent_type);
        llIdeEntPer = (LinearLayout) findViewById(R.id.ll_ide_ent_per);
        tvSelEntName = (TextView) findViewById(R.id.tv_sel_ent_name);

//        tvSelEntType = (TextView) findViewById(R.id.tv_sel_ent_type);
        etApplyName = (EditText) findViewById(R.id.et_apply_name);
        etPersName = (EditText) findViewById(R.id.et_pers_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCap = (EditText) findViewById(R.id.et_cap);
        btnNext = (Button) findViewById(R.id.btn_next);
        rlIdeId = (RelativeLayout) findViewById(R.id.rl_ide_id);
        rlNameId = (RelativeLayout) findViewById(R.id.rl_name_id);
        rlPhone = (RelativeLayout) findViewById(R.id.rl_phone);
        rlQiYe = (RelativeLayout) findViewById(R.id.rl_qi_ye);
        rlQiYeYuan = (RelativeLayout) findViewById(R.id.rl_qi_ye_yuan);
        rlYuanName = (RelativeLayout) findViewById(R.id.rl_yuan_name);
        rlApplyName = (RelativeLayout) findViewById(R.id.rl_apply_name);
        tvCap = (TextView) findViewById(R.id.tv_cap);
        ivSelQiYe = (ImageView) findViewById(R.id.iv_sel_qi_ye);


        tvTitle.setText("申请加入社团");
        commonPopupFirst = new CommonPopup();
        commonPopupFirst.setmContext(this);
        commonPopupFirst.showChoose(true);
        commonPopupFirst.setItemBackground(R.drawable.shape_title_bg);
        commonPopupFirst.setmSelectTv(tvComName);
        commonPopupFirst.setmEditTv(etSelCom);
        commonPopupFirst.setItemClickListener(new CommonPopup.OnItemClickListener() {
            @Override
            public void OnItemClick(CommonPopup.PopwindowsData value) {
                associationId = Integer.parseInt(value.getItemId());
            }
        });
        etSelCom.setTag(true);

        commonPopupSecond = new CommonPopup();
        commonPopupSecond.setmContext(this);
        commonPopupSecond.showChoose(true);
        commonPopupSecond.setItemBackground(R.drawable.shape_title_bg);
        commonPopupSecond.setmSelectTv(tvSelIde);
        commonPopupSecond.setItemClickListener(new CommonPopup.OnItemClickListener() {
            @Override
            public void OnItemClick(CommonPopup.PopwindowsData value) {
                switch (value.getItemName()) {
                    case "社团员工":
                        objType = 1;
                        llIdePerson.setVisibility(View.VISIBLE);
                        llIdeEnt.setVisibility(View.GONE);
                        llIdeEntPer.setVisibility(View.GONE);
                        break;
                    case "企业":
                        objType = 2;
                        llIdePerson.setVisibility(View.GONE);
                        llIdeEnt.setVisibility(View.VISIBLE);
                        llIdeEntPer.setVisibility(View.GONE);
                        break;
                    case "企业员工":
                        objType = 3;
                        llIdePerson.setVisibility(View.GONE);
                        llIdeEnt.setVisibility(View.GONE);
                        llIdeEntPer.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


        commonPopupThird = new CommonPopup();
        commonPopupThird.setmContext(this);
        commonPopupThird.showChoose(true);
        commonPopupThird.setItemBackground(R.drawable.shape_title_bg);
        commonPopupThird.setmSelectTv(tvSelEntName);
        commonPopupThird.setItemClickListener(value -> {
            tvSelEntName.setText(value.getItemName());
            viewModel.mEnterpriseId = Integer.parseInt(value.getItemId());
        });
    }


    private void initViewListener() {

        ivBack.setOnClickListener(view -> finish());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objType == 1) {
                    submitCommPer();
                } else if (objType == 2) {
                    submitQiYe();
                } else if (objType == 3) {
                    submitQiYePer();
                } else {
                    showToast("请选择身份");
                }
            }
        });

        tvCap.setOnClickListener(view -> getCode(tvCap));

        tvSelEntName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlQiYeYuan.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });


        etPerfName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlNameId.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });

        etPersName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlYuanName.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });

        etEntName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlQiYe.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlPhone.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });

        etSelCom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlIdeId.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
                if (edit.length() == 0) {
                    tvComName.setText("");
                    etSelCom.setTextColor(getResources().getColor(R.color.colorBlack));
                    associationId = 0; // 重置id
                    return;
                }
                Boolean tag = (Boolean) etSelCom.getTag();
                if (tag) {
                    // 查询数据
                    viewModel.reqGetMemberList(edit);
                    etSelCom.setTextColor(getResources().getColor(R.color.colorBlack));
                    tvComName.setText("");
                    associationId = 0; // 重置id
                } else {
                    etSelCom.setTag(true);
                    etSelCom.setSelection(edit.length()); // 将光标移至文字末尾
                }
            }
        });

        ivSelIde.setOnClickListener(view -> {
            ArrayList<CommonPopup.PopwindowsData> data = new ArrayList<>();
            data.add(new CommonPopup.PopwindowsData("社团员工", "1"));
            data.add(new CommonPopup.PopwindowsData("企业", "2"));
            data.add(new CommonPopup.PopwindowsData("企业员工", "3"));
            commonPopupSecond.setSelectList(data);
            commonPopupSecond.show();
        });

        ivSelQiYe.setOnClickListener(view -> {
            if (associationId == 0) {
                showToast("请先输入正确的社团ID");
                return;
            }
            viewModel.getEnterpriseList(associationId);
        });

        etApplyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlApplyName.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });

    }

    private void submitQiYePer() {

        if (associationId == 0) {
            showToast("请输入正确的社团ID");
            etSelCom.requestFocus();
            return;
        }

        String mName = tvSelEntName.getText().toString().trim();
        if (mName.isEmpty()) {
            showToast("请选择企业企业名称");
            return;
        }
//
//        String mEntType = tvSelEntType.getText().toString().trim();
//        if (mEntType.isEmpty()) {
//            showToast("请选择企业类型");
//            return;
//        }

        String mPersName = etPersName.getText().toString().trim();
        if (mPersName.isEmpty()) {
            showToast("请输入员工姓名");
            etPersName.requestFocus();
            return;
        }
        String mCap = etCap.getText().toString().trim();
        if (mCap.isEmpty()) {
            showToast("请输入验证码");
            etCap.requestFocus();
            return;
        }

        JoinApplyReq req = new JoinApplyReq();
        req.setAssociationId(String.valueOf(associationId));
        req.setApplyUserName(mPersName);
        req.setCode(mCap);
        req.setEnterpriseId(viewModel.mEnterpriseId);
        req.setEnterpriseName(mName);
        req.setTelephone(etPhone.getText().toString());
        req.setUuid(viewModel.capUUID);
        viewModel.reqBindPerCommunity(req);
    }

    private void submitQiYe() {
        if (associationId == 0) {
            showToast("请输入正确的社团ID");
            etSelCom.requestFocus();
            return;
        }

        String mName = etEntName.getText().toString().trim();
        if (mName.isEmpty()) {
            showToast("请输入企业名称");
            etPerfName.requestFocus();
            return;
        }

        String mEntType = etEntType.getText().toString().trim();
        if (mEntType.isEmpty()) {
            showToast("请输入企业类型");
            etEntType.requestFocus();
            return;
        }

        String mApplyName = etApplyName.getText().toString().trim();
        if (mApplyName.isEmpty()) {
            showToast("请输入申请人名称");
            etApplyName.requestFocus();
            return;
        }


        String mCap = etCap.getText().toString().trim();
        if (mCap.isEmpty()) {
            showToast("请输入验证码");
            etCap.requestFocus();
            return;
        }

        BindCommunityReq req = new BindCommunityReq();
        req.setAssociationId(associationId);
        req.setCode(mCap);
        req.setApplyUserName(mApplyName);
        req.setObjType(objType);
        req.setTelephone(etPhone.getText().toString());
        req.setEnterpriseName(mName);
        req.setIndustryType(mEntType);
        req.setUuid(viewModel.capUUID);
        viewModel.reqBindCommunity(req);
    }

    private void submitCommPer() {
        if (associationId == 0) {
            showToast("请输入正确的社团ID");
            etSelCom.requestFocus();
            return;
        }

        String mName = etPerfName.getText().toString().trim();
        if (mName.isEmpty()) {
            showToast("请输入员工姓名");
            etPerfName.requestFocus();
            return;
        }

        String mCap = etCap.getText().toString().trim();
        if (mCap.isEmpty()) {
            showToast("请输入验证码");
            etCap.requestFocus();
            return;
        }
        BindCommunityReq req = new BindCommunityReq();
        req.setAssociationId(associationId);
        req.setCode(mCap);
        req.setObjType(objType);
        req.setTelephone(etPhone.getText().toString());
        req.setApplyUserName(etPerfName.getText().toString());
        req.setUuid(viewModel.capUUID);

        viewModel.reqBindCommunity(req);
    }


    // 获取手机验证码方法
    private void getCode(TextView view) {
        String mPhone = etPhone.getText().toString();
        if (mPhone.isEmpty()) {
            showToast("手机号不能为空");
            etPhone.requestFocus();
            return;
        }
        viewModel.reqPhoneCaptcha(mPhone, UserConstants.CaptchaType.USER_REGISTER);
    }

    public void initData() {
        etPhone.setText(SPUtils.getInstance().getString(SharePrefer.userPhone));
        etPhone.setEnabled(false);
    }

    public void initViewObservable() {

        viewModel.mErrStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });


        viewModel.mMember.observe(this, new Observer<List<MemberResp>>() {
            @Override
            public void onChanged(List<MemberResp> memberResps) {
                if (memberResps.size() == 0) {
                    showToast("请输入正确的社团Id");
                    etSelCom.setTextColor(getResources().getColor(R.color.colorRed));
                } else {
                    List<CommonPopup.PopwindowsData> data = new ArrayList<>();
                    for (MemberResp item : memberResps) {
                        CommonPopup.PopwindowsData tempDate = new CommonPopup.PopwindowsData();
                        tempDate.setItemName(item.getAssociationName());
                        tempDate.setItemShowId(item.getAssociationCode());
                        tempDate.setItemId(String.valueOf(item.getAssociationId()));
                        data.add(tempDate);
                    }
                    commonPopupFirst.setSelectList(data);
                    commonPopupFirst.show();
                }
            }
        });

        viewModel.mEnterPriseState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    if (viewModel.mEnterpriseList.size() == 0) {
                        showToast("暂时没有数据");
                    } else {
                        List<CommonPopup.PopwindowsData> data = new ArrayList<>();
                        for (EnterpriseResp item : viewModel.mEnterpriseList) {
                            CommonPopup.PopwindowsData tempDate = new CommonPopup.PopwindowsData();
                            tempDate.setItemName(item.getEnterpriseName());
                            tempDate.setItemId(String.valueOf(item.getEnterpriseId()));
                            data.add(tempDate);
                        }
                        commonPopupThird.setSelectList(data);
                        commonPopupThird.show();
                    }
                }
            }
        });

        viewModel.mldPhoneCaptchaState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    countDownReSend(tvCap, 60);
                }
            }
        });

        viewModel.mBindComm.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    Intent intent = new Intent(BindCommunityActivity.this, BindStatusActivity.class);
                    intent.putExtra(UserConstants.Bind_Statue, UserConstants.BindStatueType.Bind_Statue_wait);
                    startActivity(intent);
                }
            }
        });
    }

    /*------------------------ 验证码倒计时方法------------------------*/
    public void countDownReSend(final TextView textView, long sec) {
        if (textView == null) return;
        ToastUtils.showShort("验证码发送成功，请注意查收");
        textView.setTag(textView.getTextColors());
        textView.setEnabled(false);
        textView.setTextColor(Color.WHITE);
        new CountDownTimer(sec * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (isDestory) {
                    cancel();
                    return;
                }
                textView.setText((millisUntilFinished / 1000) + "S");
            }

            public void onFinish() {
                if (isDestory) return;
                textView.setText("重新获取");
                textView.setEnabled(true);
                textView.setSelected(false);
                textView.setTextColor(Color.parseColor("#5E47B9"));
            }
        }.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestory = true;
    }

}