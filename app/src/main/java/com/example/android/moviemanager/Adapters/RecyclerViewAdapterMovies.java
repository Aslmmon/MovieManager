package com.example.android.moviemanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviemanager.Activites.DetailsActivity;
import com.example.android.moviemanager.Model.Movie;
import com.example.android.moviemanager.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterMovies extends RecyclerView.Adapter<RecyclerViewAdapterMovies.ViewHolder> {
    private ArrayList<Movie> movies;
    Context context;


    private Context getContext(){
        return context;

    }

    public RecyclerViewAdapterMovies(ArrayList<Movie> movies, Context context) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterMovies.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movies_layout_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.with(context).load(movie.getPosterImage()).placeholder(R.drawable.clock).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleView);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie movie = movies.get(getAdapterPosition());
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("MoviesDetails",movie);
            intent.putExtras(bundle);
            getContext().startActivity(intent);

        }
    }

}
