package com.zee.club.user.ui.view;

import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.zee.club.user.ui.login.LoginViewModel;

public class JsBridge {
    private ViewModel viewModel;
    private FragmentActivity activity;

    public JsBridge(FragmentActivity activity, ViewModel viewModel) {
        this.viewModel = viewModel;
        this.activity = activity;
    }

    @JavascriptInterface
    public void postMessage(String data) {
        Log.e("postMessage", "postMessage: " + data);
        activity.runOnUiThread(() -> {
            if (viewModel instanceof LoginViewModel) {
                ((LoginViewModel) viewModel).captchaString.setValue(data);
            }
        });
    }
}