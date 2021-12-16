package com.hw.tmbd_test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class Adapter_Movie extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final ArrayList<Movie> movies;
    private MovieItemClickListener movieItemClickListener;

    public Adapter_Movie(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public void setMovieItemClickListener(MovieItemClickListener movieItemClickListener) {
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_movie_small, viewGroup, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        String urlSrc = "https://www.themoviedb.org/t/p/original";
        Movie movie = getItem(position);
        movieViewHolder.movie_LBL_title.setText(movie.getTitle());
        movieViewHolder.movie_LBL_overview.setText(movie.getOverview());
        movieViewHolder.movie_LBL_duration.setText(movie.getRelease_date());

        Glide
                .with(context)
                .load(urlSrc + movie.getPoster_path())
                .into(movieViewHolder.movie_IMG_image);
        float rating = movie.getVote_average();
        rating /= 2;
        movieViewHolder.movie_RTNG_stars.setRating(rating);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private Movie getItem(int position) {
        return movies.get(position);
    }

    public interface MovieItemClickListener {
        void movieItemClicked(Movie movie, int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView movie_IMG_image;
        public MaterialTextView movie_LBL_title;
        public MaterialTextView movie_LBL_overview;
        public MaterialTextView movie_LBL_duration;
        public AppCompatRatingBar movie_RTNG_stars;

        public MovieViewHolder(final View itemView) {
            super(itemView);
            this.movie_IMG_image = itemView.findViewById(R.id.movie_IMG_image);
            this.movie_LBL_title = itemView.findViewById(R.id.movie_LBL_title);
            this.movie_LBL_overview = itemView.findViewById(R.id.movie_LBL_overview);
            this.movie_LBL_duration = itemView.findViewById(R.id.movie_LBL_duration);
            this.movie_RTNG_stars = itemView.findViewById(R.id.movie_RTNG_stars);

            itemView.setOnClickListener(v ->
                    movieItemClickListener
                            .movieItemClicked(
                                    getItem(getAdapterPosition()), getAdapterPosition()));
        }
    }
}