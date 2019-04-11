package com.example.jorge.myapplication.detailMovies;

import android.content.Context;
import com.example.jorge.myapplication.data.source.cloud.movie.model.Movies;

public class DetailMoviesPresenter implements DetailMoviesContract.UserActionsListener{
    private DetailMoviesContract.View mDetailCarContractView;
    private Context mContext;

    @Override
    public void loadingDetailMovies(Movies detailMovies) {
        mDetailCarContractView.setLoading(false);
        mDetailCarContractView.showDetailMovies(detailMovies);
    }

    public DetailMoviesPresenter( DetailMoviesContract.View mDetailMovieContract_View, Context context) {
        this.mContext = context;
        this.mDetailCarContractView = mDetailMovieContract_View;

    }


}
