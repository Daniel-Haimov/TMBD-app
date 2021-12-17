package com.hw.tmbd_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class Activity_Main extends AppCompatActivity {
    public static final String MOVIE_DB_KEY = "MOVIE_DB_KEY";

    private RecyclerView main_LST_movies;
    private Bundle bundle;
    private MoviesDB moviesDB;


    private final String BASE_DB_URL = "https://api.themoviedb.org/3/movie/popular?api_key=%s&language=%s&page=%d";
    private final String API_KEY = "ed4e70c32a0e3fa40d56ae5d92067d20";
    private final String LANGUAGE_KEY = Locale.getDefault().toLanguageTag();
    private int page = 1;
    private final String RESULT_KEY = "results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bundle = getIntent().getBundleExtra(Activity_Splash.BUNDLE_KEY);
        moviesDB = new MoviesDB();
        main_LST_movies = findViewById(R.id.main_LST_movies);

        firstVersion();
//        secondVersion();

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

        bundle.putString(Activity_Movie.MOVIE_KEY, new Gson().toJson(movie));

        intent.putExtra(Activity_Splash.BUNDLE_KEY, bundle);
        startActivity(intent);

    }

    private void firstVersion() {
        moviesDB = new Gson().fromJson(bundle.getString(MOVIE_DB_KEY), MoviesDB.class);
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

        @SuppressLint("DefaultLocale")
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder current = new StringBuilder();
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(String.format(BASE_DB_URL,API_KEY, LANGUAGE_KEY, page));
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while (data != -1){
                        current.append((char) data);
                        data = inputStreamReader.read();
                    }

                    return current.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
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
                JSONArray jsonArray = jsonObject.getJSONArray(RESULT_KEY);
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