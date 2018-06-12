package com.example.ibrah.movi_app.DatabaBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
