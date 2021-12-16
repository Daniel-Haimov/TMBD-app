package com.hw.tmbd_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Activity_Main extends AppCompatActivity {

    private RecyclerView main_LST_movies;
    private Bundle bundle;
    private MoviesDB moviesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_LST_movies = findViewById(R.id.main_LST_movies);

        this.bundle = getIntent().getBundleExtra("BUNDLE_KEY");

        moviesDB = new Gson().fromJson(bundle.getString("MovieDB"), MoviesDB.class);

        ArrayList<Movie> movies = moviesDB.getMovies();

        Adapter_Movie adapter_movie = new Adapter_Movie(this, movies);


        // Grid
        main_LST_movies.setLayoutManager(new GridLayoutManager(this, 2));

        // Vertically
        //main_LST_movies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        main_LST_movies.setHasFixedSize(true);
        main_LST_movies.setItemAnimator(new DefaultItemAnimator());
        main_LST_movies.setAdapter(adapter_movie);

        adapter_movie.setMovieItemClickListener((movie, position) -> Toast.makeText(Activity_Main.this, movie.getTitle(), Toast.LENGTH_SHORT).show());

    }
}