package com.example.jorge.myapplication.data.source.cloud.movie;

import java.io.Serializable;
import java.util.List;

public class ListMovies<T> implements Serializable {
    /**
     * Returns List Movies
     */
    public List<T> results;
}

