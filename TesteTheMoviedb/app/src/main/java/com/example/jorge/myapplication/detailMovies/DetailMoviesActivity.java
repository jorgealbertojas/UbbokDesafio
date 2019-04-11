package com.example.jorge.myapplication.detailMovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import com.example.jorge.myapplication.R;
import com.example.jorge.myapplication.data.source.cloud.movie.model.Movies;
import com.example.jorge.myapplication.util.ActivityUtils;
import com.example.jorge.myapplication.util.Common;

import static com.example.jorge.myapplication.movies.MoviesFragment.EXTRA_MOVIE;

public class DetailMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail_movies);


        Movies moviesDetail = (Movies) getIntent().getExtras().getSerializable(EXTRA_MOVIE);

        if (null == savedInstanceState) {
            if (Common.isOnline(this)) {
                initFragment(moviesDetail);
            }
        }
    }

    private void initFragment(Movies detailMovie) {
        DetailMoviesFragment detailMoviesFragment =
                (DetailMoviesFragment) getSupportFragmentManager().findFragmentById(R.id.fl_detail_movies);

        if (detailMoviesFragment == null) {
            // Create the fragment
            detailMoviesFragment = DetailMoviesFragment.newInstance(detailMovie);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), detailMoviesFragment, R.id.fl_detail_movies);
        }
    }

}
