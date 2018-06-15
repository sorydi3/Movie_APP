package com.example.ibrah.movi_app.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.DatabaBase.favoriteRepositori;
import com.example.ibrah.movi_app.Utils.Movie;

import java.util.List;

public class ViewModel extends AndroidViewModel {
public static String URL_top;
    public static String URL_pop;
    public static favoriteRepositori mRepository;
    public static void SetUrls(String url_top,String url_pop){
        URL_top=url_top;
        URL_pop=url_pop;
    }
    public static LiveData<List<Favorite>> mAllFavorite;
    public static MutableLiveData<List<Movie>> mMovie;

    public ViewModel (Application application) {
        super(application);
        mRepository = new favoriteRepositori(application);
        mAllFavorite = mRepository.getmAllFavorite();
        mMovie= mRepository.getDataNetwork();
    }

    LiveData<List<Favorite>> getAllWords() { return mAllFavorite; }
    public static MutableLiveData<List<Movie>> getAllMovies(){return mMovie;}
    public void insert(Favorite favorite) { mRepository.insert(favorite); }
}
