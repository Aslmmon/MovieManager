package com.example.android.moviemanager.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client  {
    public  static final String BASE_URL= "https://api.themoviedb.org";
    public  static  Retrofit retrofit = null;

    public static Retrofit getMovieData(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
