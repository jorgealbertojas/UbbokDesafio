package com.example.jorge.myapplication.detailMovies;

import com.example.jorge.myapplication.data.source.cloud.movie.model.Movies;

public class DetailMoviesContract {


    interface View {

        void setLoading(boolean isActive);

        void showDetailMovies(Movies detailCarList);

    }

    interface UserActionsListener {

        void loadingDetailMovies(Movies detailMovies);


    }
}
