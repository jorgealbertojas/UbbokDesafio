package com.example.jorge.myapplication.data.source.cloud.movie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client for get data with retrofit
 */

public class MoviesClient {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .create();

    /**
     * This function get Retrofit for get Json
     * @return
     */
    public static Retrofit getMovies() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
