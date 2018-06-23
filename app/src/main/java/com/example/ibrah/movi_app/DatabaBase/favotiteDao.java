package com.example.ibrah.movi_app.DatabaBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.ibrah.movi_app.Utils.Movie;

import java.util.List;

@Dao
public interface favotiteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long InsertFavoriteMovie(Movie favorite);

    @Query("DELETE FROM movies_table ")
    void DeleteAll();

    @Query("SELECT * FROM movies_table ORDER BY mId asc")
    LiveData<List<Movie>> ListOfFavorits();

    @Query("DELETE  FROM movies_table WHERE mId=:id")
    void deleteFavoriteMovie(int id);

    @Query("SELECT * FROM movies_table WHERE mId=:id LIMIT 1")
    List<Movie> getmId(int id);
}
