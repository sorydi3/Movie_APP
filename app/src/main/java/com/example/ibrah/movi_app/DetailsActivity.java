package com.example.ibrah.movi_app;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrah.movi_app.Adapters.HorizontalAdapter;
import com.example.ibrah.movi_app.Adapters.VerticalAdapter;
import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.Utils.Movie;
import com.example.ibrah.movi_app.Utils.Review;
import com.example.ibrah.movi_app.Utils.Trailer;
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
    ArrayList<Favorite> sto = new ArrayList();
    FloatingActionButton fab;
    VerticalAdapter adapter1;
    HorizontalAdapter adapter;
    private String TAG = "DETAIL ACTIVITY---->";
    private ViewModelDetailActivity mViewModel;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();
//    @BindView(R.id.edit_word)
//     EditText mEditWordView;
@BindView(R.id.rating_tv)
TextView rating;
    @BindView(R.id.release_date)
    TextView release;
 @BindView(R.id.toolbar_tvv)
 Toolbar toolbar;
    @BindView(R.id.synpsie)
    TextView synopsie;
    private int mPOSITION;
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            ids = savedInstanceState.getIntegerArrayList("mIds");
            Log.i(TAG, "onCreate: saved instance called size---------+++++++--------->" + ids.size());
        }
        Log.i(TAG, "ONCREATE CALLED AGAIN: saved instance called size---------+++++++--------->" + ids.size());
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
        rating.setText(String.valueOf(movie.getRating()));
        synopsie.setText(movie.getmOverview());
        buibdUrl(movie.getmId());
        collapsingToolbarLayout = findViewById(R.id.colapsing);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        ImageView imageView = findViewById(R.id.image_detail);
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
        if (ids.contains(movie.getmId())) fab.setImageResource(R.drawable.ic_star_black_24dp);
        else fab.setImageResource(R.drawable.ic_star_border_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movie.isSaved()) {
                    fab.setImageResource(R.drawable.ic_star_border_black_24dp);
                    if (ids.contains(movie.getmId())) {
                        mViewModel.delete(movie.getmId());
                        movie.setSaved(-1);
                        ids.remove(ids.indexOf(movie.getmId()));
                        Toast.makeText(DetailsActivity.this, "Unsaved", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    fab.setImageResource(R.drawable.ic_star_black_24dp);
                    Favorite favorite = new Favorite(movie.getmId(), movie.getTitle());
                    movie.setSaved(1);
                    if (!ids.contains(movie.getmId())) {
                        mViewModel.insert(favorite);
                        ids.add(movie.getmId());
                        Toast.makeText(DetailsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList("mIds", ids);
        super.onSaveInstanceState(outState);
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        ids=savedInstanceState.getIntegerArrayList("mIds");
//        super.onRestoreInstanceState(savedInstanceState);
//    }

    public void buibdUrl(int id) {
        URL_REVIES = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=61e054df38b65cdfb476d6eeffe14dc3";
        URL_VIDEOS = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=61e054df38b65cdfb476d6eeffe14dc3";
    }

}
