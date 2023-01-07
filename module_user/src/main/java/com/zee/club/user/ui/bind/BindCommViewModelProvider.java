package com.zee.club.user.ui.bind;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.ui.login.LoginViewModel;


public class BindCommViewModelProvider implements ViewModelProvider.Factory {

    private final UserRepository mUserRepository;

    public BindCommViewModelProvider(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BindCommViewModel(mUserRepository);
    }
}
