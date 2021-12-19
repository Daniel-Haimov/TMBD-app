package com.hw.tmbd_test.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.hw.tmbd_test.callbacks.CallBack_getJsonArray;
import com.hw.tmbd_test.data.DB_Controller;
import com.hw.tmbd_test.data.Movie;
import com.hw.tmbd_test.data.MoviesDB;
import com.hw.tmbd_test.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            runApp();
        } else {
            // not signed in
            signIn();
        }
        
    }

    private void runApp() {
        DB_Controller.getResultJson(this, String.format(getString(R.string.base_url),getString(R.string.api_key), LANGUAGE_KEY, page), getString(R.string.result_key), callBack_getJsonArray);

    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        runApp();
    }

    private void signIn() {


        List<String> allowedCountries = new ArrayList<String>();
        allowedCountries.add("+972");


        Intent signInIntent =
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
//                                new AuthUI.IdpConfig.GoogleBuilder().build(),
//                                new AuthUI.IdpConfig.FacebookBuilder().build(),
//                                new AuthUI.IdpConfig.TwitterBuilder().build(),
//                                new AuthUI.IdpConfig.MicrosoftBuilder().build(),
//                                new AuthUI.IdpConfig.YahooBuilder().build(),
//                                new AuthUI.IdpConfig.AppleBuilder().build(),
//                                new AuthUI.IdpConfig.EmailBuilder().build(),
//                                new AuthUI.IdpConfig.PhoneBuilder().setAllowedCountries(allowedCountries).build(),
                                new AuthUI.IdpConfig.AnonymousBuilder().build()))
                        .build();


        signInLauncher.launch(signInIntent);

    }
}