package com.example.jorge.myapplication.movies;

import com.example.jorge.myapplication.BasePresenter;
import com.example.jorge.myapplication.BaseView;
import com.example.jorge.myapplication.data.source.cloud.movie.ListMovies;
import com.example.jorge.myapplication.data.source.cloud.movie.model.Movies;

public class MoviesContract {

    public interface View extends BaseView<UserActionsListener> {

        void setLoading(boolean isActive);

        void showMovies(ListMovies<Movies> moviesList);

    }

    public interface UserActionsListener extends BasePresenter {

        void loadingMovies(Boolean popular);


    }

}
