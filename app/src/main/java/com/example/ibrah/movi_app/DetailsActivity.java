package com.example.ibrah.movi_app;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrah.movi_app.Adapters.HorizontalAdapter;
import com.example.ibrah.movi_app.Adapters.VerticalAdapter;
import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.Utils.Movie;
import com.example.ibrah.movi_app.Utils.Review;
import com.example.ibrah.movi_app.Utils.Trailer;
import com.example.ibrah.movi_app.viewModel.ViewModelDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static String URL_REVIES;
    public static String URL_VIDEOS;
    ArrayList<Favorite> sto = new ArrayList();
    FloatingActionButton fab;
    VerticalAdapter adapter1;
    HorizontalAdapter adapter;
    private String TAG = "DETAIL ACTIVITY";
    private ViewModelDetailActivity mViewModel;
    @BindView(R.id.colapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();
    private static String KEY = "ids";
    @BindView(R.id.ratingBar)
    RatingBar rating;
    @BindView(R.id.release_date)
    TextView release;
 @BindView(R.id.toolbar_tvv)
 Toolbar toolbar;
    @BindView(R.id.synpsie)
    TextView synopsie;
    @BindView(R.id.image_detail)
    ImageView imageView;
    private int mPOSITION;
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArrayList(KEY) != null) {
            ids = getArrayList(KEY);
        }
        //Log.i(TAG, "ONCREATE CALLED AGAIN: saved instance called size---------+++++++--------->" + ids.size());
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle;
        bundle = this.getIntent().getExtras();
        movies = bundle.getParcelableArrayList("ArrayList");
        mPOSITION = bundle.getInt("position");
        movie = movies.get(mPOSITION);
        release.setText(movie.getDate());
        rating.setRating((float) movie.getRating() / 2);
        synopsie.setText(movie.getmOverview());
        buibdUrl(movie.getmId());
        collapsingToolbarLayout.setTitle(movie.getTitle());
        String image = movie.getThumbnailLink();
        if (image != null && image.length() > 0) {
            Picasso.get().load("https://image.tmdb.org/t/p/w300/" + image).into(imageView);
            Log.e(TAG, "onBindViewHolder:---> https://image.tmdb.org/t/p/w185/" + image);
        }
        //ids.add(movie.getmId());
        RecyclerView recyclerView = findViewById(R.id.recycler_View_videos);
        adapter = new HorizontalAdapter(this);
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
        fab = findViewById(R.id.fab);
//        if (ids.contains(movie.getmId())) fab.setImageResource(R.drawable.ic_star_black_24dp);
//        else fab.setImageResource(R.drawable.ic_star_border_black_24dp);
        if (ids.contains(movie.getmId())) fab.setImageResource(R.drawable.ic_star_black_24dp);
        else fab.setImageResource(R.drawable.ic_star_border_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ids.contains(movie.getmId())) {
                    fab.setImageResource(R.drawable.ic_star_border_black_24dp);
                        mViewModel.delete(movie.getmId());
                        ids.remove(ids.indexOf(movie.getmId()));
                        Toast.makeText(DetailsActivity.this, "Unsaved", Toast.LENGTH_SHORT).show();
                } else {
                    fab.setImageResource(R.drawable.ic_star_black_24dp);
                    mViewModel.insert(movie);
                        ids.add(movie.getmId());
                        Toast.makeText(DetailsActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("mIds", (ArrayList<Integer>) ids);
    }

    public void buibdUrl(int id) {
        URL_REVIES = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=61e054df38b65cdfb476d6eeffe14dc3";
        URL_VIDEOS = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=61e054df38b65cdfb476d6eeffe14dc3";
    }

    public void saveArraListSharedPreferences(ArrayList<Integer> array, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(array);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<Integer> getArrayList(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = preferences.getString(key, null);
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveArraListSharedPreferences(ids, KEY);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        saveArraListSharedPreferences(ids, KEY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveArraListSharedPreferences(ids, KEY);
    }
}
