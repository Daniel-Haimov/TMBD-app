package com.hw.tmbd_test;

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


public class Adapter_Movie_List extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final ArrayList<Movie> movies;
    private final String SRC_URL = "https://www.themoviedb.org/t/p/w500";
    private MovieItemClickListener movieItemClickListener;
    private LoadPage loadPage;
    private int page = 1;

    public Adapter_Movie_List(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public void setMovieItemClickListener(MovieItemClickListener movieItemClickListener) {
        this.movieItemClickListener = movieItemClickListener;
    }

    public void setLoadPage(LoadPage loadPage) {
        this.loadPage = loadPage;
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
        Movie movie = getItem(position);
        movieViewHolder.movieList_LBL_title.setText(movie.getTitle());
        movieViewHolder.movieList_LBL_overview.setText(movie.getOverview());
        movieViewHolder.movieList_LBL_duration.setText(movie.getRelease_date());

        Glide
                .with(context)
                .load(SRC_URL + movie.getPoster_path())
                .into(movieViewHolder.movieList_IMG_image);
        movieViewHolder.movieList_RTNG_stars.setRating(movie.getVote_average() / 2);

        if (movies.size() - position > 5){
            loadPage.loadPage(++page);
        }
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

    public interface LoadPage {
        void loadPage(int page);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView movieList_IMG_image;
        public MaterialTextView movieList_LBL_title;
        public MaterialTextView movieList_LBL_overview;
        public MaterialTextView movieList_LBL_duration;
        public AppCompatRatingBar movieList_RTNG_stars;


        public MovieViewHolder(final View itemView) {
            super(itemView);
            this.movieList_IMG_image = itemView.findViewById(R.id.movieList_IMG_image);
            this.movieList_LBL_title = itemView.findViewById(R.id.movieList_LBL_title);
            this.movieList_LBL_overview = itemView.findViewById(R.id.movieList_LBL_overview);
            this.movieList_LBL_duration = itemView.findViewById(R.id.movieList_LBL_duration);
            this.movieList_RTNG_stars = itemView.findViewById(R.id.movieList_RTNG_stars);

            itemView.setOnClickListener(v ->
                    movieItemClickListener
                            .movieItemClicked(
                                    getItem(getAdapterPosition()), getAdapterPosition()));
        }
    }
}