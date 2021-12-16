package com.hw.tmbd_test;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DB_Controller {

    public static void getResultJson(Context context, String url, String resultKey, CallBack_getJsonArray callBack_getJsonArray){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    JSONArray jsonArray = null;
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        jsonArray = jsonObject.getJSONArray(resultKey);
                    } catch (JSONException e) {
                        callBack_getJsonArray.getJSONArray(null);
                    }
                    callBack_getJsonArray.getJSONArray(jsonArray);
                }, error -> callBack_getJsonArray.getJSONArray(null));


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

