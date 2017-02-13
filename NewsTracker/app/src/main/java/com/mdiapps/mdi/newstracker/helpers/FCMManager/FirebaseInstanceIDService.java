package com.mdiapps.mdi.newstracker.helpers.FCMManager;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsSourceData;
import com.mdiapps.mdi.newstracker.helpers.VolleyLibrary.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mascot on 2/13/2017.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "REQ";
    private static final String TAG_CITY = "sources";
    private static final String TAG_IMAGE = "urlsToLogos";
    JSONObject e,f;
    JSONArray city,img;

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        registerToken(token);
    }

    private void registerToken(String token) {
        Log.d("Tocken Registered: ", token);

        String url = "http://139.59.25.251/projects/newstracker/appusers/add_appuser?appuTockenId="+token;

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }
}