package com.example.ibrah.movi_app;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.ibrah.movi_app.DatabaBase.Favorite;
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
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private List<Movie> mMovies;

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
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
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
                favoriteRepositori.optionQuerry=2;
                favoriteRepositori.getDataNetwork();
            case R.id.action_sort:
                boolean swith=true;
                if(swith) {
                    favoriteRepositori.optionQuerry = 1;
                    favoriteRepositori.getDataNetwork();
                    swith=false;
                }
                else{
                    favoriteRepositori.optionQuerry=2;
                    favoriteRepositori.getDataNetwork();
                    swith=true;
                }

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            //TODO need to fix _mID when retote the fone its get reinicialised againg and repeat the id's againt (primari keys database)
            int _mID = 0;
            Favorite favorite   = new Favorite(_mID++,data.getStringExtra(DetailsActivity.EXTRA_REPLY));
            mViewModel.insert(favorite);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
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
