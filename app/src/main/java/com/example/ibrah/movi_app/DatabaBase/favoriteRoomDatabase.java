package com.example.ibrah.movi_app.DatabaBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Favorite.class}, version = 1)
public abstract class favoriteRoomDatabase extends RoomDatabase {
    public abstract favotiteDao wordDao();
    private static favoriteRoomDatabase INSTANCE;
    static favoriteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (favoriteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            favoriteRoomDatabase.class, "favorite_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final favotiteDao mDao;

        PopulateDbAsync(favoriteRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.DeleteAll();

            // Favorite favorite = new Favorite(1,"Fast and furious");
            //mDao.InsertFavoriteMovie(favorite);
            // Favorite favorite1 = new Favorite(2,"World");
            // mDao.InsertFavoriteMovie(favorite1);
            return null;
        }
    }
}
