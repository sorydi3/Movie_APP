package com.example.ibrah.movi_app.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    /**
     * A {@link Movie} object that contains details related to a single
     * book item to be displayed in BookListActivity
     */
     private int saved;
     private int mId;
    /**
     * Movie Title
     */
    private String mTitle;

    /**
     * Movie Author
     */
    private String mDate;

    /**
     * Rating
     */
    private int mRating;

    /**
     * Reviews
     */
    private List<String> mReviews;

    /**
     * videos
     */
    private List<String> mVideos;

    /**
     * Thumbnail Link
     */
    private String mThumbnailLink;


    /**
     * Default Constructor - Constructs a new {@link Movie} object
     * @param mId
     * @param title         - Title of the movie
     * @param date        - Author of the movie
     * @param rating        - Rating of the movie (e.g. 3.5)
     * @param reviews   - Description of the movie
     * @param mVideos
     * @param thumbnailLink - Link for the book image
     */
    public Movie(int mId, String title, String date,
                 int rating, List<String> reviews, List<String> mVideos, String thumbnailLink,int sav) {
        this.mId = mId;
        mTitle = title;
        mDate = date;
        mRating = rating;
        mReviews = reviews;
        this.mVideos = mVideos;
        mThumbnailLink = thumbnailLink;
        this.saved=sav;
    }

    public Movie(Parcel input) {
        mId = input.readInt();
        saved=input.readInt();
        mTitle = input.readString();
        mDate = input.readString();
        mRating = input.readInt();
        mReviews =new ArrayList<String>();
        input.readList(mReviews,null);
        mVideos =new ArrayList<String>();
        input.readList(mVideos,null);
        mThumbnailLink = input.readString();

    }


    /**
     * Getter method - Title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Setter method - Title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Getter method - Author
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Setter method - Author
     */
    public void setDate(String date) {
        mDate = date;
    }


    /**
     * Getter method - Rating
     */
    public int getRating() {
        return mRating;
    }

    /**
     * Setter method - Rating
     */
    public void setRating(int rating) {
        mRating = rating;
    }

    /**
     * Getter method - id
     */
    public int getmId() {
        return mId;
    }

    /**
     * Setter method - id
     */
    public void setmId(int ID) {
        mId = ID;
    }

    /**
     * Getter method - Description
     */
    public List<String> getmReviews() {
        return mReviews;
    }

    /**
     * Setter method - Description
     */
    public void setmReviews(List<String> reviews) {
        mReviews = reviews;
    }

    /**
     * Getter method - Thumbnail Link
     */
    public String getThumbnailLink() {
        return mThumbnailLink;
    }

    /**
     * Setter method - Thumbnail Link
     */
    public void setThumbnailLink(String thumbnailLink) {
        mThumbnailLink = thumbnailLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(saved);
        dest.writeString(mTitle);
        dest.writeString(mDate);
        dest.writeInt(mRating);
        dest.writeList(mReviews);
        dest.writeList(mVideos);
        dest.writeString(mThumbnailLink);
    }


    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public List<String> getmVideos() {
        return mVideos;
    }

    public void setmVideos(List<String> mVideos) {
        this.mVideos = mVideos;
    }

    public int isSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }
}
