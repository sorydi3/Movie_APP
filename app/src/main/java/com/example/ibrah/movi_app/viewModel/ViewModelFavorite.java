package com.example.ibrah.movi_app.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.DatabaBase.favoriteRepositori;


import java.util.List;

public class ViewModelFavorite extends AndroidViewModel {

    public static favoriteRepositori mRepository;

    public static LiveData<List<Favorite>> mAllFavorite;

    public ViewModelFavorite(Application application) {
        super(application);
        mRepository = new favoriteRepositori(application);
        mAllFavorite = mRepository.getmAllFavorite();
    }

    public LiveData<List<Favorite>> getmAllFavorite() {
        return mAllFavorite;
    }

}
