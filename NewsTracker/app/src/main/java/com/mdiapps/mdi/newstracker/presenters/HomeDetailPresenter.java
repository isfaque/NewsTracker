package com.mdiapps.mdi.newstracker.presenters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsData;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsSourceData;
import com.mdiapps.mdi.newstracker.helpers.VolleyLibrary.AppController;
import com.mdiapps.mdi.newstracker.views.main.fragments.homedetail.HomeDetailView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mascot on 2/12/2017.
 */
public class HomeDetailPresenter {
    private HomeDetailView view;

    private static final String TAG = "REQ";
    private static final String TAG_NEWS = "articles";
    JSONObject e,f;
    JSONArray city,img;

    public HomeDetailPresenter(HomeDetailView view) {
        this.view = view;
    }

    public void getNotice(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        NewsData.news_author.clear();
                        NewsData.news_title.clear();
                        NewsData.news_description.clear();
                        NewsData.news_datetime.clear();
                        NewsData.news_imageurl.clear();
                        NewsData.news_mainurl.clear();

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);
                            city = jsonObj.getJSONArray(TAG_NEWS);
                            for (int i = 0; i < city.length(); i++) {
                                e = city.getJSONObject(i);

                                String author = e.getString("author");
                                String title = e.getString("title");
                                String description = e.getString("description");
                                String datetime = e.getString("publishedAt");
                                String imageurl = e.getString("urlToImage");
                                String mainurl = e.getString("url");

                                NewsData.news_author.add(author);
                                NewsData.news_title.add(title);
                                NewsData.news_description.add(description);
                                NewsData.news_datetime.add(datetime);
                                NewsData.news_imageurl.add(imageurl);
                                NewsData.news_mainurl.add(mainurl);
                            }
                            view.startGetNotice();
                            return;
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.errorGetNotice("error response");
                return;
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }


}
