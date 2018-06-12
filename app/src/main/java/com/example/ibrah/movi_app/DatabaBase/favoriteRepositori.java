package com.example.ibrah.movi_app.DatabaBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.DatabaBase.favoriteRoomDatabase;
import com.example.ibrah.movi_app.DatabaBase.favotiteDao;

import java.util.List;

public class favoriteRepositori {

    private favotiteDao mWordDao;
    private LiveData<List<Favorite>> mAllFavorite;

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
}
