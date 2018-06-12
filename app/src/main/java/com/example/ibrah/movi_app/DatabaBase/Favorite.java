package com.example.ibrah.movi_app.DatabaBase;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity(tableName = "favorite_table")
public class Favorite {
        @PrimaryKey
        private int mId;
        private String mNom;
        public Favorite(int id,String nom) {this.mId = id;this.mNom=nom;}

        public int getmId(){return this.mId;}
        public String getmNom(){return this.mNom;}
}
