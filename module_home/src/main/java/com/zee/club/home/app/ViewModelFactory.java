package com.zee.club.home.app;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.zee.club.home.data.DataRepository;
import com.zee.club.home.ui.about.AboutClubViewModel;
import com.zee.club.home.ui.about.ClassicAppListViewModel;
import com.zee.club.home.ui.activities.ActivitiesPublishViewModel;
import com.zee.club.home.ui.activities.ClubActivitiesViewModel;
import com.zee.club.home.ui.activities.SportsMeetingViewModel;
import com.zee.club.home.ui.article.ArticleDetailViewModel;
import com.zee.club.home.ui.notice.BiddingListViewModel;
import com.zee.club.home.ui.notice.ClubNoticeViewModel;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final DataRepository dataRepository;

    public static ViewModelFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(DataRepository.getInstance());
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AboutClubViewModel.class)) {
            return (T) new AboutClubViewModel(dataRepository);
        }else if(modelClass.isAssignableFrom(ClassicAppListViewModel.class)){
            return (T) new ClassicAppListViewModel(dataRepository);
        }else if(modelClass.isAssignableFrom(ClubNoticeViewModel.class)){
            return (T) new ClubNoticeViewModel(dataRepository);
        }else if(modelClass.isAssignableFrom(BiddingListViewModel.class)){
            return (T) new BiddingListViewModel(dataRepository);
        }else if(modelClass.isAssignableFrom(ClubActivitiesViewModel.class)){
            return (T) new ClubActivitiesViewModel(dataRepository);
        }else if(modelClass.isAssignableFrom(ArticleDetailViewModel.class)){
            return (T) new ArticleDetailViewModel(dataRepository);
        }else if(modelClass.isAssignableFrom(ActivitiesPublishViewModel.class)){
            return (T) new ActivitiesPublishViewModel(dataRepository);
        }else if(modelClass.isAssignableFrom(SportsMeetingViewModel.class)){
            return (T) new SportsMeetingViewModel(dataRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
