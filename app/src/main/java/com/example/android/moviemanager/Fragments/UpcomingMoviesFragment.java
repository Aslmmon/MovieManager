package com.example.android.moviemanager.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.android.moviemanager.Adapters.RecyclerViewAdapterMovies;
import com.example.android.moviemanager.Api.Client;
import com.example.android.moviemanager.Api.Service;
import com.example.android.moviemanager.Model.Movie;
import com.example.android.moviemanager.Model.Results;
import com.example.android.moviemanager.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingMoviesFragment extends Fragment {

    RecyclerView recyclerView;
    RelativeLayout emptyView;

    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.upcoming_movies_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        emptyView = view.findViewById(R.id.empty_view);
        getUpcomingMovies();

        return view;
    }

    private void getUpcomingMovies() {
        Client client = new Client();
        Service service2 = client.getMovieData().create(Service.class);
        Call<Results> getUpcomingResults = service2.getUpcomingResults();
        getUpcomingResults.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                ArrayList<Movie> results = response.body().getMovies();
                recyclerView.setAdapter(new RecyclerViewAdapterMovies(results, getContext()));
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                emptyView.setVisibility(View.VISIBLE);
            }
        });


    }
}
