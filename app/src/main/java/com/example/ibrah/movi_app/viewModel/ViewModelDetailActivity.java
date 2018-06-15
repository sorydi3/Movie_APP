package com.example.ibrah.movi_app.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.DatabaBase.favoriteRepositori;
import com.example.ibrah.movi_app.Utils.Movie;

import java.util.List;

public class ViewModelDetailActivity extends AndroidViewModel {
    public static String URL_top;
    public static String URL_pop;
    public static favoriteRepositori mRepository;
    public static void SetUrls(String url_top,String url_pop){
        URL_top=url_top;
        URL_pop=url_pop;
    }

    public static MutableLiveData<List<Movie>>  mTrailers;
    public static MutableLiveData<List<Movie>> mReviews;

    public ViewModelDetailActivity (Application application) {
        super(application);
        mRepository = new favoriteRepositori(application);
        mReviews= mRepository.getDataNetworkTrailers();
        mTrailers= mRepository.getDataNetworkReviews();
    }

    public static MutableLiveData<List<Movie>> getAllReviews(){return mTrailers;}
    public static MutableLiveData<List<Movie>> getAllTrailers(){return mReviews;}
    public void insert(Favorite favorite) { mRepository.insert(favorite); }
}
