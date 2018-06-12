package com.example.ibrah.movi_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.DatabaBase.favoriteRepositori;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private favoriteRepositori mRepository;

    private LiveData<List<Favorite>> mAllFavorite;

    public ViewModel (Application application) {
        super(application);
        mRepository = new favoriteRepositori(application);
        mAllFavorite = mRepository.getmAllFavorite();
    }

    LiveData<List<Favorite>> getAllWords() { return mAllFavorite; }

    public void insert(Favorite favorite) { mRepository.insert(favorite); }
}
