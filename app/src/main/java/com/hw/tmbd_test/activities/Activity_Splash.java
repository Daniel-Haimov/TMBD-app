package com.hw.tmbd_test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.hw.tmbd_test.callbacks.CallBack_getJsonArray;
import com.hw.tmbd_test.data.DB_Controller;
import com.hw.tmbd_test.data.Movie;
import com.hw.tmbd_test.data.MoviesDB;
import com.hw.tmbd_test.R;

import java.util.ArrayList;
import java.util.Locale;

public class Activity_Splash extends AppCompatActivity {


    private final String LANGUAGE_KEY = Locale.getDefault().toLanguageTag(); //en-US // he-IL etc...
    private int page = 1;



    private final MoviesDB moviesDB = new MoviesDB();
    private final CallBack_getJsonArray callBack_getJsonArray = (jsonArray) -> {
        String json = String.valueOf(jsonArray);
        ArrayList<Movie> tmp = new Gson().fromJson(json, ArrayList.class);
        moviesDB.setMovies(tmp);

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

        MobileAds.initialize(this, initializationStatus -> {});

        bundle.putString(getString(R.string.movieDB_key), new Gson().toJson(moviesDB));

        intent.putExtra(getString(R.string.bundle_key), bundle);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DB_Controller.getResultJson(this, String.format(getString(R.string.base_url),getString(R.string.api_key), LANGUAGE_KEY, page), getString(R.string.result_key), callBack_getJsonArray);
    }
}