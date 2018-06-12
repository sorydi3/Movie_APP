package com.example.ibrah.movi_app.DatabaBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface favotiteDao {
    //@Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    int InsertFavoriteMovie(Favorite favorite);
    @Query("DELETE FROM favorite_table ")
    void DeleteAll();
    @Query("SELECT * FROM favorite_table ORDER BY mId asc")
    LiveData<List<Favorite>> ListOfFavorits();
}
