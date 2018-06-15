package com.example.ibrah.movi_app;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String URL_REVIES ="https://api.themoviedb.org/3/movie/550/reviews?api_key=61e054df38b65cdfb476d6eeffe14dc3";
    public static final String URL_VIDEOS ="https://api.themoviedb.org/3/movie/550/videos?api_key=61e054df38b65cdfb476d6eeffe14dc3";
 //TODO YOU WERE ADDING URL FOR DETAIL ACTIVITY NEXT YOU NEED TO ASYNCROTASK TO REPOSITORY
    @BindView(R.id.edit_word)
     EditText mEditWordView;

    @BindView(R.id.toolbar_tv_d)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}
