package com.example.android.moviemanager.Activites;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviemanager.Model.Movie;
import com.example.android.moviemanager.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    Movie movie;
    ImageView backimage;
    TextView overview, releaseDate;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        backimage = findViewById(R.id.backImage);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        overview = findViewById(R.id.overview);
        releaseDate = findViewById(R.id.releaseDate);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movie = (Movie) extras.getSerializable("MoviesDetails");
            Picasso.with(getApplicationContext()).load(movie.getBackPoster()).placeholder(R.drawable.clock).into(backimage);
            releaseDate.setText(movie.getDate());
            overview.setText(movie.getOverview());
            collapsingToolbarLayout.setTitle(movie.getTitle());
            collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
        }

    }
}
