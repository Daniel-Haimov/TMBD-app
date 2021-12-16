package com.hw.tmbd_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final String DB_URL = "https://api.themoviedb.org/3/movie/popular?api_key=ed4e70c32a0e3fa40d56ae5d92067d20";
    private final String RESULT_KEY = "results";
    private TextView textView;
    private MoviesDB moviesDB = new MoviesDB();
    private final CallBack_getJsonArray callBack_getJsonArray = (jsonArray) -> {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonMovie = jsonArray.getJSONObject(i);
                moviesDB.getMovies().add(new Gson().fromJson(String.valueOf(jsonMovie), Movie.class));
            } catch (JSONException ignored) {
            }
        }
        textView.setText(moviesDB.getMovies().get(10).getTitle());
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.debug);
        DB_Controller.getResultJson(this, DB_URL, RESULT_KEY, callBack_getJsonArray);
    }
}