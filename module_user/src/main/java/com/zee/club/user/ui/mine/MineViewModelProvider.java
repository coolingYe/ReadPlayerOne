package com.zee.club.user.ui.mine;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.user.data.UserRepository;


public class MineViewModelProvider implements ViewModelProvider.Factory {

    private final UserRepository mUserRepository;

    public MineViewModelProvider(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MineViewModel(mUserRepository);
    }
}
