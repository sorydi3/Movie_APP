package com.example.ibrah.movi_app.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.DatabaBase.favoriteRepositori;
import com.example.ibrah.movi_app.Utils.Movie;
import com.example.ibrah.movi_app.Utils.Review;
import com.example.ibrah.movi_app.Utils.Trailer;

import java.util.List;

public class ViewModelDetailActivity extends AndroidViewModel {
    public static favoriteRepositori mRepository;
    public static MutableLiveData<List<Trailer>> mTrailers;
    public static MutableLiveData<List<Review>> mReviews;

    public ViewModelDetailActivity (Application application) {
        super(application);
        mRepository = new favoriteRepositori(application);
        mTrailers = mRepository.getDataNetworkTrailers();
        mReviews = mRepository.getDataNetworkReviews();
    }

    public List<Movie> getmId(int id) {
        return mRepository.getmId(id);
    }
    public MutableLiveData<List<Review>> getAllReviews() {
        return mReviews;
    }

    public MutableLiveData<List<Trailer>> getAllTrailers() {
        return mTrailers;
    }

    public void insert(Movie favorite) {
        mRepository.insert(favorite);
    }

    public void delete(int id) {
        mRepository.delete(id);
    }
}
