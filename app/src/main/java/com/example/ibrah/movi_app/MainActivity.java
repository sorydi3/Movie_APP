package com.example.ibrah.movi_app;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ibrah.movi_app.Adapters.Adapter_main;
import com.example.ibrah.movi_app.DatabaBase.favoriteRepositori;
import com.example.ibrah.movi_app.Utils.Movie;
import com.example.ibrah.movi_app.viewModel.ViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static String URL_TOP_RATED="https://api.themoviedb.org/3/movie/top_rated?api_key=61e054df38b65cdfb476d6eeffe14dc3";
    public static String URL_POPULAR="https://api.themoviedb.org/3/movie/popular?api_key=61e054df38b65cdfb476d6eeffe14dc3";
    private List<Movie> mMovies;
    boolean swith = true;

    private ViewModel mViewModel;
    Adapter_main adapter;
    @BindView(R.id.toolbar_tv)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ViewModel.SetUrls(URL_POPULAR, URL_TOP_RATED);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new Adapter_main(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // Get a new or existing ViewModel from the ViewModelProvider.
        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);

         //Add an observer on the LiveData returned by getAlphabetizedWords.
        //The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
            mViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable final List<Movie> words) {
                    // Update the cached copy of the words in the adapter.
                    adapter.setWords(words);
                    mMovies = words;
                }
            });
            adapter.SetLister(new Adapter_main.Listener() {
                @Override
                public void onClick(int position) {
                    sendData(position);
                }
            });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, favorite_Activity.class);
                // bundle that hold the data of the currently playing song
                startActivity(intent);
            case R.id.action_sort:
                if(swith) {
                    favoriteRepositori.optionQuerry = 1;
                    Toast.makeText(this, "Popular", Toast.LENGTH_SHORT).show();
                    favoriteRepositori.getDataNetwork();
                    swith=false;
                }
                else{
                    swith = true;
                    favoriteRepositori.optionQuerry=2;
                    Toast.makeText(this, "Top Rated", Toast.LENGTH_SHORT).show();
                    favoriteRepositori.getDataNetwork();
                }

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void sendData(int position) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        // bundle that hold the data of the currently playing song
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ArrayList", (ArrayList<Movie>) mMovies);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
