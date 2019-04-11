package com.example.jorge.myapplication.movies;


import com.example.jorge.myapplication.data.source.cloud.movie.ListMovies;
import com.example.jorge.myapplication.data.source.cloud.movie.MoviesServiceApi;
import com.example.jorge.myapplication.data.source.cloud.movie.model.Movies;

public class MoviesPresenter implements MoviesContract.UserActionsListener {

    private final MoviesServiceApi mMoviesServiceApi;
    private final MoviesContract.View mMoviesContractView;


    public MoviesPresenter(MoviesServiceApi mCarsServiceApi,MoviesContract.View mCarsContract_View) {
        this.mMoviesContractView = mCarsContract_View;
        this.mMoviesServiceApi = mCarsServiceApi;
    }


    /**
     * Loading the car call Service Api with data
     */
    @Override
    public void loadingMovies(Boolean popular) {
        mMoviesContractView.setLoading(true);
        mMoviesServiceApi.getMovies(new MoviesServiceApi.MoviesServiceCallback<ListMovies<Movies>>(){

            @Override
            public void onLoaded(ListMovies listMovies) {
                mMoviesContractView.setLoading(false);
                mMoviesContractView.showMovies(listMovies);
            }
        },popular);

    }


    @Override
    public void start() {

    }
}
