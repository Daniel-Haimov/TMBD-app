package com.hw.tmbd_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class Activity_Movie extends AppCompatActivity {

    public static final String MOVIE_KEY = "MOVIE_KEY";

    private Bundle bundle;
    private Movie movie;

    private final String SRC_URL = "https://www.themoviedb.org/t/p/w500";

    private AppCompatImageView  movie_IMG_image         ;
    private MaterialTextView    movie_LBL_title         ;
    private MaterialTextView    movie_LBL_overview      ;
    private MaterialTextView    movie_LBL_releaseDate   ;
    private AppCompatRatingBar  movie_RTNG_stars        ;
    private MaterialTextView    movie_LBL_votes         ;
    private AppCompatImageView  movie_IMG_backdrop      ;
    private AppCompatButton     movie_BTN_back          ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        this.bundle = getIntent().getBundleExtra(Activity_Splash.BUNDLE_KEY);
        this.movie = new Gson().fromJson(bundle.getString(MOVIE_KEY), Movie.class);

        findViews();

        showMovie();


    }

    private void findViews() {
        movie_IMG_image         = findViewById(R.id.movieActivity_IMG_image);
        movie_LBL_title         = findViewById(R.id.movieActivity_LBL_title);
        movie_LBL_overview      = findViewById(R.id.movieActivity_LBL_overview);
        movie_LBL_releaseDate   = findViewById(R.id.movieActivity_LBL_releaseDate);
        movie_RTNG_stars        = findViewById(R.id.movieActivity_RTNG_stars);
        movie_LBL_votes         = findViewById(R.id.movieActivity_LBL_votes);
        movie_IMG_backdrop      = findViewById(R.id.movieActivity_IMG_backdrop);
        movie_BTN_back          = findViewById(R.id.movieActivity_BTN_back);

    }

    private void showMovie() {
        Glide
                .with(this)
                .load(SRC_URL + movie.getPoster_path())
                .into(movie_IMG_image);
        this.movie_LBL_title         .setText(movie.getTitle());
        this.movie_LBL_overview      .setText(movie.getOverview());
        this.movie_LBL_releaseDate   .setText(movie.getRelease_date());
        this.movie_RTNG_stars        .setRating(movie.getVote_average() / 2);
        String votes = getString(R.string.vote) + movie.getVote_count();
        this.movie_LBL_votes         .setText(votes);
        Glide
                .with(this)
                .load(SRC_URL + movie.getBackdrop_path())
                .into(movie_IMG_backdrop);
        movie_BTN_back.setOnClickListener(v -> onBackPressed());
    }
}