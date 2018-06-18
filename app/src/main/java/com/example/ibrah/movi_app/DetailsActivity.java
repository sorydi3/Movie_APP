package com.example.ibrah.movi_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;

import com.example.ibrah.movi_app.Adapters.HorizontalAdapter;
import com.example.ibrah.movi_app.Adapters.Main_Adapter_DetailActivity;
import com.example.ibrah.movi_app.Adapters.VerticalAdapter;
import com.example.ibrah.movi_app.Utils.Movie;
import com.example.ibrah.movi_app.Utils.Review;
import com.example.ibrah.movi_app.Utils.Trailer;
import com.example.ibrah.movi_app.viewModel.ViewModel;
import com.example.ibrah.movi_app.viewModel.ViewModelDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static String URL_REVIES;
    public static String URL_VIDEOS;
    VerticalAdapter adapter1;
    HorizontalAdapter adapter;
    private String TAG = "DETAIL ACTIVITY---->";
    private ViewModelDetailActivity mViewModel;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    public static List<Trailer> mTrailers;
    private static List<Review> mReviews;
    private ArrayList<Object> objects = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();
 //TODO YOU WERE ADDING URL FOR DETAIL ACTIVITY NEXT YOU NEED TO ASYNCROTASK TO REPOSITORY
//    @BindView(R.id.edit_word)
//     EditText mEditWordView;
//
 @BindView(R.id.toolbar_tvv)
    Toolbar toolbar;
    private int mPOSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        movies = bundle.getParcelableArrayList("ArrayList");
        mPOSITION = bundle.getInt("position");
        Movie movie = movies.get(mPOSITION);
        Log.e(TAG, "onCreate: POSITION----> " + movies.get(mPOSITION).getTitle());
        buibdUrl(movie.getmId());
        Log.e(TAG, "onCreate: " + URL_REVIES + " -->" + URL_VIDEOS);
        collapsingToolbarLayout = findViewById(R.id.colapsing);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        ImageView imageView = findViewById(R.id.image_detail);
        String image = movie.getThumbnailLink();
        if (image != null && image.length() > 0) {
            //TODO fix picaso copying the last one that i used from sandwich app
            Picasso.get().load("https://image.tmdb.org/t/p/w300/" + image).into(imageView);
            Log.e(TAG, "onBindViewHolder:---> https://image.tmdb.org/t/p/w185/" + image);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_View_videos);
        adapter = new HorizontalAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerViewR = findViewById(R.id.recycler_View_reviews);
        adapter1 = new VerticalAdapter();
        recyclerViewR.setAdapter(adapter1);
        recyclerViewR.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //Add an observer on the LiveData returned by getAlphabetizedWords.
        //The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mViewModel = ViewModelProviders.of(this).get(ViewModelDetailActivity.class);
        mViewModel.getAllReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                // mReviews = reviews;
                adapter1.setWords(reviews);
                // Log.e(TAG, "onCreate: LENGHT reviews----> " + mReviews.size());
            }
        });

        mViewModel.getAllTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                adapter.setWords(trailers);
                //   Log.e(TAG, "onCreate: LENGHT trailer----> " + mTrailers.size());
            }
        });

    }


    public void buibdUrl(int id) {
        URL_REVIES = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=61e054df38b65cdfb476d6eeffe14dc3";
        URL_VIDEOS = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=61e054df38b65cdfb476d6eeffe14dc3";
    }

    private ArrayList<Object> getObject() {

        objects.add(getVertical());
        objects.add(getHorizontal());
        return objects;
    }

    public static List<Trailer> getHorizontal() {
        return mTrailers;
    }

    public static List<Review> getVertical() {
        return mReviews;
    }

}
