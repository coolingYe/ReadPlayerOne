package com.zee.club.user.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.zee.club.user.R;
import com.zee.club.user.app.UserViewModelFactory;
import com.zee.club.user.config.UserConstants;
import com.zee.club.user.data.UserRepository;
import com.zeewain.base.model.LoadState;
import com.zeewain.base.ui.BaseActivity;

public class ForgetPasswordActivity extends BaseActivity {
    private ForgetPasswordViewModel viewModel;
    private EditText etPhone;
    private EditText etCap;
    private TextView tvCap;
    private EditText etPassword;
    private EditText etAccount;
    private ImageView loginCbPassword;
    private EditText etComPassword;
    private ImageView loginCbPasswordCom;
    private Button btnSubmit;
    public boolean isDestory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(ForgetPasswordViewModel.class);
        setContentView(R.layout.activity_forget_password);
        initView();
        initViewListener();
        initViewObservable();
        initData();

    }


    private void initView() {
        etPhone = (EditText) findViewById(R.id.et_phone);
        etAccount = (EditText) findViewById(R.id.et_account);
        etCap = (EditText) findViewById(R.id.et_cap);
        tvCap = (TextView) findViewById(R.id.tv_cap);
        etPassword = (EditText) findViewById(R.id.et_password);
        loginCbPassword = (ImageView) findViewById(R.id.login_cb_password);
        loginCbPassword.setTag(false);
        etComPassword = (EditText) findViewById(R.id.et_com_password);
        loginCbPasswordCom = (ImageView) findViewById(R.id.login_cb_password_com);
        loginCbPasswordCom.setTag(false);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }


    private void initViewListener() {

        btnSubmit.setOnClickListener(view -> submit());
        
        tvCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });
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



        loginCbPasswordCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginCbPasswordCom.getTag() != null) {
                    boolean isCheck = (boolean) loginCbPasswordCom.getTag();
                    loginCbPasswordCom.setTag(!isCheck);
                    etComPassword.setTransformationMethod(!isCheck ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    loginCbPasswordCom.setBackgroundResource(!isCheck ? R.mipmap.eye_can : R.mipmap.eye_no);
                    etComPassword.setSelection(etComPassword.getText().toString().length());
                }
            }
        });

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

    }


    // 获取手机验证码方法
    private void getCode() {
        String mPhone = etPhone.getText().toString();
        if (mPhone.isEmpty()) {
            showToast("手机号不能为空");
            etPhone.requestFocus();
            return;
        }
        viewModel.reqPhoneCaptcha(mPhone, UserConstants.CaptchaType.FIND_PASSWORD);
    }


    // 修改
    private void submit() {
        String mAccount = etAccount.getText().toString();
        if (mAccount.isEmpty()) {
            showToast("账户不能为空");
            etAccount.requestFocus();
            return;
        }
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
        String mPassword = etPassword.getText().toString();
        if (mPassword.isEmpty()) {
            showToast("密码不能为空");
            etPassword.requestFocus();
            return;
        }
        String mComPassword = etComPassword.getText().toString();
        if (mComPassword.isEmpty()) {
            showToast("确认密码不能为空");
            etPassword.requestFocus();
            return;
        }
        if (!mPassword.equals(mComPassword)) {
            showToast("密码与确认密码不一样，请重新输入");
            return;
        }
        viewModel.reqForgetPassword(mAccount, mPhone, mCap, mPassword,viewModel.capUUID);

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