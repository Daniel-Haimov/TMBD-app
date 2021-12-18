package com.hw.tmbd_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Activity_Main extends AppCompatActivity {

    private RecyclerView main_LST_movies;
    private Bundle bundle;
    private MoviesDB moviesDB;


    private final String LANGUAGE_KEY = Locale.getDefault().toLanguageTag();
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bundle = getIntent().getBundleExtra(getString(R.string.bundle_key));

//        MobileAds.initialize(this, initializationStatus -> {
//            initADS();
//        });

        initADS();

        moviesDB = new MoviesDB();
        main_LST_movies = findViewById(R.id.main_LST_movies);

        firstVersion();
//        secondVersion();

    }

    private void initADS() {
//        AdManagerAdView adView = new AdManagerAdView(this);
//        adView.setAdSizes(AdSize.BANNER);
//        adView.setAdUnitId("/6499/example/banner");

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void adapter() {
        ArrayList<Movie> movies = moviesDB.getMovies();
        Adapter_Movie_List adapter_movieList = new Adapter_Movie_List(this, movies);

        // Grid
        main_LST_movies.setLayoutManager(new GridLayoutManager(this, 2));

        // Vertically
        //main_LST_movies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        main_LST_movies.setHasFixedSize(true);
        main_LST_movies.setItemAnimator(new DefaultItemAnimator());
        main_LST_movies.setAdapter(adapter_movieList);

        adapter_movieList.setMovieItemClickListener((movie, position) -> {
            openMovieActivity(movie);
        });
        adapter_movieList.setLoadPage((page) -> {
            this.page = page;
            if(page <= 500){
                secondVersion();
            }
        });

    }

    private void openMovieActivity(Movie movie) {
        Intent intent = new Intent(this, Activity_Movie.class);

        bundle.putString(getString(R.string.movie_key), new Gson().toJson(movie));

        intent.putExtra(getString(R.string.bundle_key), bundle);
        startActivity(intent);

    }

    private void firstVersion() {
        moviesDB = new Gson().fromJson(bundle.getString(getString(R.string.movieDB_key)), MoviesDB.class);
        adapter();
    }

    private void secondVersion() {
        GetData getData = new GetData();
        getData.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class GetData extends AsyncTask<String, String, String> {

        public GetData() {
            super();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder current = new StringBuilder();
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(String.format(getString(R.string.base_url),getString(R.string.api_key), LANGUAGE_KEY, page));
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


                    int data = inputStreamReader.read();
                    while (data != -1){
                        current.append((char) data);
                        data = inputStreamReader.read();
                    }

                    return current.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.result_key));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonMovie = jsonArray.getJSONObject(i);
                    moviesDB.getMovies().add(new Gson().fromJson(String.valueOf(jsonMovie), Movie.class));
                }
//                moviesDB.setMovies(new Gson().fromJson(String.valueOf(jsonArray), moviesDB.getMovies().getClass()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            adapter();
        }

    }



}