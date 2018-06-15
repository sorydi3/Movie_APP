package com.example.ibrah.movi_app.DatabaBase;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favorite_table")
public class Favorite {
        @PrimaryKey
        public int mId;
        @NonNull
        public String mNom;
        public Favorite(int id,@NonNull String nom) {this.mId = id;this.mNom=nom;}

        public int getmId(){return this.mId;}
        public String getmNom(){return this.mNom;}
}
