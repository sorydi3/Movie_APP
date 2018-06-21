package com.example.ibrah.movi_app.DatabaBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.DatabaBase.favoriteRoomDatabase;
import com.example.ibrah.movi_app.DatabaBase.favotiteDao;
import com.example.ibrah.movi_app.DetailsActivity;
import com.example.ibrah.movi_app.MainActivity;
import com.example.ibrah.movi_app.Utils.Movie;
import com.example.ibrah.movi_app.Utils.QuerryMovies;
import com.example.ibrah.movi_app.Utils.QuerryReviews;
import com.example.ibrah.movi_app.Utils.QuerryTrailers;
import com.example.ibrah.movi_app.Utils.Review;
import com.example.ibrah.movi_app.Utils.Trailer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class favoriteRepositori {

    private favotiteDao mWordDao;
    public LiveData<List<Favorite>> mAllFavorite;
    public static MutableLiveData<List<Movie>>mMovie=new MutableLiveData<>();
    public static MutableLiveData<List<Trailer>> mTrailers = new MutableLiveData<>();
    public static MutableLiveData<List<Review>> mReviews = new MutableLiveData<>();
    public static int optionQuerry=0;
    public static Long mRowDeleted;

   public favoriteRepositori(Application application) {
        favoriteRoomDatabase db = favoriteRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllFavorite = mWordDao.ListOfFavorits();
    }

    public LiveData<List<Favorite>> getmAllFavorite() {
        return mAllFavorite;
    }
    public void insert (Favorite word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public MutableLiveData<List<Trailer>> getDataNetworkTrailers() {
        //TODO falata contruir los links
        new fetcheMovieAsyncroTask_trailers().execute(DetailsActivity.URL_VIDEOS);
        return mTrailers;
    }

    public MutableLiveData<List<Review>> getDataNetworkReviews() {
        //TODO falta construir los links
        new fetcheMovieAsyncroTask_reviews().execute(DetailsActivity.URL_REVIES);
        return mReviews;
    }

    public void delete(int id) {
        new deleteAsyncTask(mWordDao).execute(id);
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private favotiteDao mAsyncTaskDao;

        deleteAsyncTask(favotiteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteFavoriteMovie(params[0]);
            return null;
        }

    }

    private static class insertAsyncTask extends AsyncTask<Favorite, Void, Void> {

        private favotiteDao mAsyncTaskDao;

        insertAsyncTask(favotiteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favorite... params) {
            mAsyncTaskDao.InsertFavoriteMovie(params[0]);
            return null;
        }
    }

    public static MutableLiveData<List<Movie>> getDataNetwork(){
       if(optionQuerry==1)
       new fetcheMovieAsyncroTask().execute(MainActivity.URL_POPULAR);
       else new fetcheMovieAsyncroTask().execute(MainActivity.URL_TOP_RATED);
        return mMovie;
    }

    private static class fetcheMovieAsyncroTask extends AsyncTask<String,Void,List<Movie>>{

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings[0] == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.+

            List<Movie> movies = QuerryMovies.fetchData(strings[0]);
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
           mMovie.setValue(movies);
        }
    }

    private static class fetcheMovieAsyncroTask_trailers extends AsyncTask<String, Void, List<Trailer>> {

        @Override
        protected List<Trailer> doInBackground(String... strings) {
            if (strings[0] == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.+

            List<Trailer> trailers = QuerryTrailers.fetchData(strings[0]);
            return trailers;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            super.onPostExecute(trailers);
            mTrailers.setValue(trailers);
        }
    }

    private static class fetcheMovieAsyncroTask_reviews extends AsyncTask<String, Void, List<Review>> {

        @Override
        protected List<Review> doInBackground(String... strings) {
            if (strings[0] == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.+

            List<Review> reviews = QuerryReviews.fetchData(strings[0]);
            return reviews;
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            super.onPostExecute(reviews);
            mReviews.setValue(reviews);
        }
    }
    //TODO ADD ASYNCROS ABOVE 
}
