package com.example.ibrah.movi_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.ibrah.movi_app.Adapters.Adapter;
import com.example.ibrah.movi_app.DatabaBase.Favorite;
import com.example.ibrah.movi_app.Utils.Movie;
import com.example.ibrah.movi_app.viewModel.ViewModel;
import com.example.ibrah.movi_app.viewModel.ViewModelFavorite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class favorite_Activity extends AppCompatActivity {
    private ViewModelFavorite mViewModel;

    @BindView(R.id.toolbar_tvv)
    Toolbar toolbar;
    String TAG = "Favorite activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_favorite);
        final Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get a new or existing ViewModel from the ViewModelProvider.
        mViewModel = ViewModelProviders.of(this).get(ViewModelFavorite.class);

        //Add an observer on the LiveData returned by getAlphabetizedWords.
        //The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mViewModel.getmAllFavorite().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable final List<Favorite> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
                Log.i(TAG, "onChanged: " + words.size());

            }
        });
    }
}
