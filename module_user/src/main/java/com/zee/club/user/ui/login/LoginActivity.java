package com.zee.club.user.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.protocol.response.AssociationResp;
import com.zee.club.user.data.protocol.response.CaptchaResp;
import com.zee.club.user.data.protocol.response.PhoneLoginResp;
import com.zee.club.user.ui.bind.BindCommunityActivity;
import com.zee.club.user.ui.view.CaptchaDialog;
import com.zee.club.user.ui.web.WebViewActivity;
import com.zeewain.base.config.BaseConstants;
import com.zeewain.base.config.SharePrefer;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;
import com.zeewain.base.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {

    public boolean isDestory;

    private int loginType = UserConstants.LoginType.PHONE;
    private boolean agreeStatue = false;

    private LoginViewModel viewModel;

    private ImageView imgBackLogin;
    private TextView tlSelLog;
    private RelativeLayout rvPhoLog;
    private RelativeLayout rvAcLog;
    private ImageView loginCbPassword;
    private TextView tvCap;
    private TextView tvJoin;
    private TextView tvAgr;
    private Button loginBtn;
    private EditText etPhone;
    private EditText etCap;
    private EditText etAccount;
    private EditText etPassword;
    private TextView tvChangeLogType;
    private TextView tvForgetPass;
    private ImageView ivClear;
    private ImageView ivClearCount;
    private ImageView ivAgree;
    private FrameLayout flAgree;
    private CaptchaDialog mCapDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPUtils.getInstance().put(SharePrefer.userToken, "");
        viewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(LoginViewModel.class);
        setContentView(R.layout.activity_login);
        initData();
        initViewObservable();
        initView();
        initViewListener();
    }


    private void initView() {
        imgBackLogin = (ImageView) findViewById(R.id.img_back_login);
        tlSelLog = (TextView) findViewById(R.id.tl_sel_log);
        rvPhoLog = (RelativeLayout) findViewById(R.id.rv_pho_log);
        rvAcLog = (RelativeLayout) findViewById(R.id.rv_ac_log);
        loginCbPassword = (ImageView) findViewById(R.id.login_cb_password);
        loginCbPassword.setTag(false);
        tvCap = (TextView) findViewById(R.id.tv_cap);
        tvJoin = (TextView) findViewById(R.id.tv_join);
        tvAgr = (TextView) findViewById(R.id.tv_agr);
        loginBtn = (Button) findViewById(R.id.login_btn);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCap = (EditText) findViewById(R.id.et_cap);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvChangeLogType = (TextView) findViewById(R.id.tv_change_log_type);
        tvForgetPass = (TextView) findViewById(R.id.tv_forget_pass);
        ivClear = (ImageView) findViewById(R.id.iv_clear);
        ivClearCount = (ImageView) findViewById(R.id.iv_clear_count);
        ivAgree = (ImageView) findViewById(R.id.iv_agree);
        flAgree = (FrameLayout) findViewById(R.id.fl_agree);
    }


    private void initViewListener() {

        imgBackLogin.setOnClickListener(view -> finish());

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CommunitySetlActivity.class);
                startActivity(intent);
            }
        });

        flAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreeStatue = !agreeStatue;
                ivAgree.setVisibility(agreeStatue ? View.VISIBLE : View.INVISIBLE);
            }
        });

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPhone.setText("");
            }
        });

        ivClearCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAccount.setText("");
            }
        });

        tvChangeLogType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginType = (loginType == UserConstants.LoginType.PHONE ? UserConstants.LoginType.ACCOUNT : UserConstants.LoginType.PHONE);
                tlSelLog.setText(loginType == UserConstants.LoginType.PHONE ? "手机号登录" : "账户登录");
                tvChangeLogType.setText(loginType == UserConstants.LoginType.PHONE ? "账户登录" : "手机号登录");
                rvPhoLog.setVisibility(loginType == UserConstants.LoginType.PHONE ? View.VISIBLE : View.GONE);
                rvAcLog.setVisibility(loginType == UserConstants.LoginType.ACCOUNT ? View.VISIBLE : View.GONE);
                tvForgetPass.setVisibility(loginType == UserConstants.LoginType.ACCOUNT ? View.VISIBLE : View.GONE);
            }
        });
        tvCap.setOnClickListener(view -> getCode());


        loginCbPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginCbPassword.getTag() != null) {
                    boolean isCheck = (boolean) loginCbPassword.getTag();
                    loginCbPassword.setTag(!isCheck);
                    etPassword.setTransformationMethod(!isCheck ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    loginCbPassword.setBackgroundResource(!isCheck ? R.mipmap.eye_can : R.mipmap.eye_no);
                    etPassword.setSelection(etPassword.getText().toString().length());
                }
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        tvAgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.startActivity(LoginActivity.this, "https://m.baidu.com", "百度");
            }
        });
    }

    // 登录方法
    private void login() {
        if (loginType == UserConstants.LoginType.PHONE) {
            String mPhone = etPhone.getText().toString();
            if (mPhone.isEmpty()) {
                showToast("手机号不能为空");
                etPhone.requestFocus();
                return;
            }
            String mCap = etCap.getText().toString();
            if (mCap.isEmpty()) {
                showToast("验证码不能为空");
                etCap.requestFocus();
                return;
            }
            if (!agreeStatue) {
                showToast("请阅读并勾选隐私协议");
                return;
            }
            viewModel.reqPhoneLogin(mCap, mPhone, String.valueOf(UserConstants.LoginType.PHONE), viewModel.capUuid); // uuid 暂时给空
//            startActivity(new Intent(LoginActivity.this, BindStatusActivity.class));
        } else {
            String mAccount = etAccount.getText().toString();
            if (mAccount.isEmpty()) {
                showToast("账号不能为空");
                etPhone.requestFocus();
                return;
            }
            String mPassword = etPassword.getText().toString();
            if (mPassword.isEmpty()) {
                showToast("验证码不能为空");
                etCap.requestFocus();
                return;
            }
            if (!agreeStatue) {
                showToast("请阅读并勾选隐私协议");
                return;
            }

            // 请求防水墙
//            mCapDialog = new CaptchaDialog(viewModel);
//            mCapDialog.show(getSupportFragmentManager(), "captcha");
            viewModel.reqAccountLogin(etAccount.getText().toString(), String.valueOf(UserConstants.LoginType.ACCOUNT),
                    etPassword.getText().toString(), "", "");
        }
    }

    // 获取手机验证码方法
    private void getCode() {
        String mPhone = etPhone.getText().toString();
        if (mPhone.isEmpty()) {
            showToast("手机号不能为空");
            etPhone.requestFocus();
            return;
        }
        viewModel.reqPhoneCaptcha(etPhone.getText().toString(), UserConstants.CaptchaType.USER_LOGIN, "", "");
    }

    public void initData() {

    }

    public void initViewObservable() {
        viewModel.mErrStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast("ErrString" + s);
            }
        });


        viewModel.mPhoneLoginResp.observe(this, new Observer<PhoneLoginResp>() {
            @Override
            public void onChanged(PhoneLoginResp phoneLoginResp) {
                SPUtils.getInstance().put(SharePrefer.userToken, phoneLoginResp.getToken());
                // 保存手机号
                if (loginType == UserConstants.LoginType.PHONE) {
                    SPUtils.getInstance().put(SharePrefer.userPhone, etPhone.getText().toString());
                } else {
                    SPUtils.getInstance().put(SharePrefer.userPhone, etAccount.getText().toString());
                }
                // 获取我加入的社团列表信息
                viewModel.reqGetAssociationList();
            }
        });

        viewModel.mAssociationResp.observe(this, new Observer<List<AssociationResp>>() {
            @Override
            public void onChanged(List<AssociationResp> associationResps) {
                int length = associationResps.size();
                if (length >= 2) length = 2;
                switch (length) {
                    case 0:
                        Intent intentBind = new Intent(LoginActivity.this, BindCommunityActivity.class);
                        startActivity(intentBind);
                        finish();
                        break;
                    case 1:
                        SPUtils.getInstance().put(SharePrefer.associationId, associationResps.get(0).getAssociationId());
                        goToMain();
                        finish();
                        break;
                    case 2:
                        Intent intent = new Intent(LoginActivity.this, SelectIdeActivity.class);
                        intent.putParcelableArrayListExtra(BaseConstants.EXTRA_IDE_INFO, (ArrayList<? extends Parcelable>) associationResps);
                        startActivity(intent);
                        finish();
                        break;
                }
                finish();
            }
        });

        viewModel.mldPhoneCaptchaState.observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(LoadState loadState) {
                if (loadState == LoadState.Success) {
                    countDownReSend(tvCap, 60);
                } else if (loadState == LoadState.Failed) {
                    showToast("获取验证码失败");
                }
            }
        });

        viewModel.captchaString.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mCapDialog.dismiss();
                CaptchaResp bean = new Gson().fromJson(s, CaptchaResp.class);
                viewModel.reqAccountLogin(etAccount.getText().toString(), String.valueOf(UserConstants.LoginType.ACCOUNT),
                        etPassword.getText().toString(), bean.getRandstr(), bean.getTicket());
            }
        });
    }

    private void goToMain() {
        try {
            Class<?> clazz = Class.forName("com.zee.club.ui.main.MainActivity");
            Intent intent = new Intent(LoginActivity.this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /*------------------------ 验证码倒计时方法------------------------*/
    public void countDownReSend(final TextView textView, long sec) {
        if (textView == null) return;
        showToast("验证码发送成功，请注意查收");
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
    protected void onDestroy() {
        super.onDestroy();
        isDestory = true;
    }
}