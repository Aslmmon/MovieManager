package com.example.android.moviemanager.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
public class PopularMoviesFragment extends Fragment {

    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    RelativeLayout emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.popular_movies_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        loadData();
        emptyView = view.findViewById(R.id.empty_view);

        return view;
    }

    private void loadData() {
        Client client = new Client();
        Service service = client.getMovieData().create(Service.class);
        Call<Results> getResults = service.getResults();
        getResults.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                ArrayList<Movie> results = response.body().getMovies();
                recyclerView.setAdapter(new RecyclerViewAdapterMovies(results,getContext()));
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Toast.makeText(getContext(),"Check Your internet connection",Toast.LENGTH_SHORT).show();
                emptyView.setVisibility(View.VISIBLE);

            }
        });
    }

}
