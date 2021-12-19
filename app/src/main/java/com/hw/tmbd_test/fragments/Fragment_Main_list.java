package com.hw.tmbd_test.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.hw.tmbd_test.R;
import com.hw.tmbd_test.activities.Activity_Movie;
import com.hw.tmbd_test.adapters.Adapter_Movie_List;
import com.hw.tmbd_test.data.Movie;
import com.hw.tmbd_test.data.MoviesDB;

import java.util.ArrayList;

import lombok.Setter;

public class Fragment_Main_list extends Fragment {

    @Setter
    private MoviesDB moviesDB;

    private RecyclerView main_LST_movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__main_list, container, false);
        findViews(view);

        return view;
    }

    private void findViews(View view) {
        main_LST_movies = view.findViewById(R.id.main_LST_movies);
    }


//    private void adapter() {
//        ArrayList<Movie> movies = moviesDB.getMovies();
//        Adapter_Movie_List adapter_movieList = new Adapter_Movie_List(this, movies);
//
//        // Grid
//        main_LST_movies.setLayoutManager(new GridLayoutManager(this, 2));
//
//        // Vertically
//        //main_LST_movies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//        main_LST_movies.setHasFixedSize(true);
//        main_LST_movies.setItemAnimator(new DefaultItemAnimator());
//        main_LST_movies.setAdapter(adapter_movieList);
//
//        adapter_movieList.setMovieItemClickListener((movie, position) -> {
//            addMovieToFireBase(movie);
//            openMovieActivity(movie);
//        });
//        adapter_movieList.setLoadPage((page) -> {
//            this.page = page;
//            if(page <= 500){
//                secondVersion();
//            }
//        });
//
//    }



//    private void addMovieToFireBase(Movie movie) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("movies");
//
//        try{
//            //V1
////            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(movie.getOriginal_title()).setValue(movie);
//            //V2
//            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(movie.getOriginal_title()).setValue(movie);
//        } catch (Exception e){
//        }
//    }
//
//    private void openMovieActivity(Movie movie) {
//        Intent intent = new Intent(this, Activity_Movie.class);
//        Bundle bundle = new Bundle();
//
//        bundle.putString(getString(R.string.movie_key), new Gson().toJson(movie));
//
//        intent.putExtra(getString(R.string.bundle_key), bundle);
//        startActivity(intent);
//
//    }
}