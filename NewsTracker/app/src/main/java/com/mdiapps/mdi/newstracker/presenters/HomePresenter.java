package com.mdiapps.mdi.newstracker.presenters;


import android.util.Log;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsSourceData;
import com.mdiapps.mdi.newstracker.helpers.VolleyLibrary.AppController;
import com.mdiapps.mdi.newstracker.views.main.fragments.home.HomeView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apps2 on 2/8/2017.
 */
public class HomePresenter {
    private HomeView view;

    private static final String TAG = "REQ";
    private static final String TAG_CITY = "sources";
    private static final String TAG_IMAGE = "urlsToLogos";
    JSONObject e,f;
    JSONArray city,img;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    public void getNotice(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        NewsSourceData.news_source_id.clear();
                        NewsSourceData.news_source_title.clear();
                        NewsSourceData.news_source_detail.clear();
                        NewsSourceData.news_source_imageurl.clear();

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);
                            city = jsonObj.getJSONArray(TAG_CITY);
                            for (int i = 0; i < city.length(); i++) {
                                e = city.getJSONObject(i);
                                f = e.getJSONObject("urlsToLogos");
                                String Pick_Id = f.getString("small");
                                Log.i("Author is", Pick_Id);

                                String id = e.getString("id");
                                String title = e.getString("name");
                                String detail = e.getString("description");
                                String imageurl = f.getString("small");
                                NewsSourceData.news_source_id.add(id);
                                NewsSourceData.news_source_title.add(title);
                                NewsSourceData.news_source_detail.add(detail);
                                NewsSourceData.news_source_imageurl.add(imageurl);
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
