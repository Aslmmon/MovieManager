package com.example.android.moviemanager.Api;

import com.example.android.moviemanager.Model.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("/3/movie/popular?api_key=658680e409a5e9e11988f3e49361edae&language=en-US&page=1")
    Call<Results> getResults();

    @GET("/3/movie/upcoming?api_key=658680e409a5e9e11988f3e49361edae&language=en-US&page=1")
    Call<Results> getUpcomingResults();
    @GET("/3/movie/top_rated?api_key=658680e409a5e9e11988f3e49361edae&language=en-US&page=1")
    Call<Results> getTopRated();
}
