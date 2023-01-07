package com.zee.club.user.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.ui.mine.MessageCenterViewModel;
import com.zee.club.user.ui.view.CommonPopup;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.utils.ToastUtils;

import java.util.ArrayList;

public class CommunitySetlFragment extends BottomSheetDialogFragment {

    private CommunitySetlViewModel mViewModel;
    private CommonPopup commonPopupSecond;
    private Button btnSubmit;
    private EditText etName;
    private EditText etIdeName;
    private EditText etPhone;
    private EditText etIdeApplyName;
    private EditText etCap;
    private int scaleType = 0;
    public boolean isDestory;
    private TextView tvCap;

    public static CommunitySetlFragment newInstance() {
        return new CommunitySetlFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community_setl, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(CommunitySetlViewModel.class);
        initViewObservable();
        initView(view);


    }

    private void initView(View view) {

        TextView tvSelIdeScale = (TextView) view.findViewById(R.id.tv_sel_ide_scale);
        ImageView ivSelScale = (ImageView) view.findViewById(R.id.iv_sel_scale);
        commonPopupSecond = new CommonPopup();
        commonPopupSecond.setmContext(getActivity());
        commonPopupSecond.showChoose(true);
        commonPopupSecond.setItemBackground(R.drawable.shape_title_bg);
        commonPopupSecond.setmSelectTv(tvSelIdeScale);
        commonPopupSecond.setItemClickListener(new CommonPopup.OnItemClickListener() {
            @Override
            public void OnItemClick(CommonPopup.PopwindowsData value) {
                scaleType = Integer.parseInt(value.getItemId());
            }
        });

        RelativeLayout rlName = (RelativeLayout) view.findViewById(R.id.rl_name);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        etName = (EditText) view.findViewById(R.id.et_name);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlName.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });


        RelativeLayout rlIdeName = (RelativeLayout) view.findViewById(R.id.rl_ide_name);
        TextView tvIdeId = (TextView) view.findViewById(R.id.tv_ide_id);
        etIdeName = (EditText) view.findViewById(R.id.et_ide_name);

        etIdeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlIdeName.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });


        ivSelScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CommonPopup.PopwindowsData> data = new ArrayList<>();
                data.add(new CommonPopup.PopwindowsData("1-50人", "1"));
                data.add(new CommonPopup.PopwindowsData("51-100人", "2"));
                data.add(new CommonPopup.PopwindowsData("101-200人", "3"));
                data.add(new CommonPopup.PopwindowsData("201-500人", "4"));
                data.add(new CommonPopup.PopwindowsData("500-1000人", "5"));
                data.add(new CommonPopup.PopwindowsData("1001人以上", "6"));
                commonPopupSecond.setSelectList(data);
                commonPopupSecond.show();
            }
        });


        RelativeLayout rlPhone = (RelativeLayout) view.findViewById(R.id.rl_phone);
        TextView tvIdePhone = (TextView) view.findViewById(R.id.tv_ide_phone);
        etPhone = (EditText) view.findViewById(R.id.et_phone);
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
        etCap = (EditText) view.findViewById(R.id.et_cap);
        tvCap = (TextView) view.findViewById(R.id.tv_cap);
        tvCap.setOnClickListener(view1 -> getCode());

        RelativeLayout rlIdeApplyName = (RelativeLayout) view.findViewById(R.id.rl_ide_apply_name);
        TextView tvIdeApplyId = (TextView) view.findViewById(R.id.tv_ide_apply_id);
        etIdeApplyName = (EditText) view.findViewById(R.id.et_ide_apply_name);

        etIdeApplyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                rlIdeApplyName.setVisibility(edit.length() == 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apply();
            }
        });
    }

    private void apply() {

        String mName = etName.getText().toString().trim();
        if (mName.isEmpty()) {
            ToastUtils.showShort("请输入超级管理员名称");
            etName.requestFocus();
            return;
        }

        String mApplyName = etIdeApplyName.getText().toString().trim();
        if (mApplyName.isEmpty()) {
            ToastUtils.showShort("请输入申请人名称");
            etIdeApplyName.requestFocus();
            return;
        }

        String mIdeName = etIdeName.getText().toString().trim();
        if (mIdeName.isEmpty()) {
            ToastUtils.showShort("请输入社团名称");
            etIdeName.requestFocus();
            return;
        }

        String mPhone = etPhone.getText().toString().trim();
        if (mPhone.isEmpty()) {
            ToastUtils.showShort("请输入手机号码");
            etPhone.requestFocus();
            return;
        }

        String mCap = etCap.getText().toString().trim();
        if (mCap.isEmpty()) {
            ToastUtils.showShort("请输入验证码");
            etCap.requestFocus();
            return;
        }

        if (scaleType == 0) {
            ToastUtils.showShort("请先社团选择规模");
            return;
        }
        mViewModel.reqEnterApply(mName, mPhone, mApplyName, mIdeName, mCap, scaleType, mViewModel.capUUID);

    }

    public void initViewObservable() {
        mViewModel.mldPhoneCaptchaState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    countDownReSend(tvCap, 60);
                }
            }
        });

        mViewModel.mErrStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.mApplyState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState state) {
                if (state == LoadState.Success) {
                    ToastUtils.showShort("申请成功");
                    CommunitySetlFragment.this.dismiss();
                }
            }
        });
    }


    // 获取手机验证码方法
    private void getCode() {
        String mPhone = etPhone.getText().toString();
        if (mPhone.isEmpty()) {
            Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            etPhone.requestFocus();
            return;
        }
        mViewModel.reqPhoneCaptcha(mPhone, UserConstants.CaptchaType.USER_REGISTER);
    }


    /*------------------------ 验证码倒计时方法------------------------*/
    public void countDownReSend(final TextView textView, long sec) {
        if (textView == null) return;
        Toast.makeText(getActivity(), "验证码发送成功，请注意查收", Toast.LENGTH_SHORT).show();
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
    public void onPause() {
        Log.e("onPause", "onPause: ");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy", "onDestroy: ");
        super.onDestroy();
        isDestory = true;
    }
}