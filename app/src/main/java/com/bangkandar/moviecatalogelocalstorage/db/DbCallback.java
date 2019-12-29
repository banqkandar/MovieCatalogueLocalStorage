package com.bangkandar.moviecatalogelocalstorage.db;

import com.bangkandar.moviecatalogelocalstorage.model.Movie;
import com.bangkandar.moviecatalogelocalstorage.model.TvShow;

import java.util.ArrayList;

public interface DbCallback {

    void preExecuteMovie();

    void preExecuteTv();

    void postExecuteMovie(ArrayList<Movie> movies);

    void postExecuteTvshow(ArrayList<TvShow> tvShows);
}

