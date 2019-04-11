package com.example.jorge.myapplication;

import com.example.jorge.myapplication.data.source.cloud.movie.*;
import com.example.jorge.myapplication.data.source.cloud.movie.model.Movies;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import retrofit2.Call;
import retrofit2.Response;


import java.io.IOException;

public class MoviesUnitTest {

    MoviesEndpoint mRetrofit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchValidDataShouldLoadIntoViewMoviesPopular() {
        mRetrofit = MoviesClient.getMovies().create(MoviesEndpoint.class);

        Call<ListMovies<Movies>> callMovies = mRetrofit.getMoviesPOPULAR("1");
        try {
            Response<ListMovies<Movies>> quoteOfTheDayResponse = callMovies.execute();

            //Asserting response
            Assert.assertTrue(quoteOfTheDayResponse.isSuccessful());
            Assert.assertNotNull(quoteOfTheDayResponse.body().results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fetchValidDataShouldLoadIntoViewMoviesTop() {
        mRetrofit = MoviesClient.getMovies().create(MoviesEndpoint.class);
        Call<ListMovies<Movies>> callMovies = mRetrofit.getMoviesTOP_RATED("1");
        try {
            Response<ListMovies<Movies>> quoteOfTheDayResponse = callMovies.execute();

            //Asserting response
            Assert.assertTrue(quoteOfTheDayResponse.isSuccessful());
            Assert.assertNotNull(quoteOfTheDayResponse.body().results);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
