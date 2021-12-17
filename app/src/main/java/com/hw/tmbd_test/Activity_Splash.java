package com.hw.tmbd_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.Locale;

public class Activity_Splash extends AppCompatActivity {

    public static final String BUNDLE_KEY = "BUNDLE_KEY";

    private final String BASE_DB_URL = "https://api.themoviedb.org/3/movie/popular?api_key=%s&language=%s&page=%d";
    private final String API_KEY = "ed4e70c32a0e3fa40d56ae5d92067d20";
    private final String LANGUAGE_KEY = Locale.getDefault().toLanguageTag();
    private int page = 1;
    private final String RESULT_KEY = "results";



    private MoviesDB moviesDB = new MoviesDB();
    private final CallBack_getJsonArray callBack_getJsonArray = (jsonArray) -> {
        moviesDB.setMovies(new Gson().fromJson(String.valueOf(jsonArray), moviesDB.getMovies().getClass()));

//        for (int i = 0; i < jsonArray.length(); i++) {
//            try {
//                JSONObject jsonMovie = jsonArray.getJSONObject(i);
//                moviesDB.getMovies().add(new Gson().fromJson(String.valueOf(jsonMovie), Movie.class));
//            } catch (JSONException ignored) {
//            }
//        }
        openHomeActivity();
    };

    private void openHomeActivity() {
        Intent intent = new Intent(this, Activity_Main.class);
        Bundle bundle = new Bundle();

        bundle.putString(Activity_Main.MOVIE_DB_KEY, new Gson().toJson(moviesDB));

        intent.putExtra(BUNDLE_KEY, bundle);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DB_Controller.getResultJson(this, String.format(BASE_DB_URL, API_KEY, LANGUAGE_KEY, page), RESULT_KEY, callBack_getJsonArray);
    }
}