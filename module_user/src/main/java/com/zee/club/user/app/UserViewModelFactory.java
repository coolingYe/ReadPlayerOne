package com.zee.club.user.app;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zee.club.user.data.UserRepository;
import com.zee.club.user.ui.bind.BindCommViewModel;
import com.zee.club.user.ui.login.CommunitySetlViewModel;
import com.zee.club.user.ui.login.ForgetPasswordViewModel;
import com.zee.club.user.ui.login.LoginViewModel;
import com.zee.club.user.ui.mine.DrawUserViewModel;
import com.zee.club.user.ui.mine.ExamineViewModel;
import com.zee.club.user.ui.mine.GatherViewModel;
import com.zee.club.user.ui.mine.InteractionViewModel;
import com.zee.club.user.ui.mine.MessageCenterViewModel;
import com.zee.club.user.ui.mine.MineViewModel;
import com.zee.club.user.ui.mine.UserInfoViewModel;


public class UserViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile UserViewModelFactory INSTANCE;
    private final UserRepository dataRepository;

    public static UserViewModelFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (UserViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserViewModelFactory(UserRepository.getInstance());
                }
            }
        }
        return INSTANCE;
    }

    private UserViewModelFactory(UserRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(BindCommViewModel.class)) {
            return (T) new BindCommViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(ForgetPasswordViewModel.class)) {
            return (T) new ForgetPasswordViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(MineViewModel.class)) {
            return (T) new MineViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(ExamineViewModel.class)) {
            return (T) new ExamineViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(GatherViewModel.class)) {
            return (T) new GatherViewModel(dataRepository);
        } else if (modelClass.isAssignableFrom(MessageCenterViewModel.class)) {
            return (T) new MessageCenterViewModel(dataRepository);
        }else if (modelClass.isAssignableFrom(InteractionViewModel.class)) {
            return (T) new InteractionViewModel(dataRepository);
        }else if (modelClass.isAssignableFrom(UserInfoViewModel.class)) {
            return (T) new UserInfoViewModel(dataRepository);
        }else if (modelClass.isAssignableFrom(CommunitySetlViewModel.class)) {
            return (T) new CommunitySetlViewModel(dataRepository);
        }if (modelClass.isAssignableFrom(DrawUserViewModel.class)) {
            return (T) new DrawUserViewModel(dataRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
