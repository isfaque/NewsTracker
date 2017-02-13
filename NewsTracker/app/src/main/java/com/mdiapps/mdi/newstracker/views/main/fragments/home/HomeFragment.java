package com.mdiapps.mdi.newstracker.views.main.fragments.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mdiapps.mdi.newstracker.R;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsSourceAdapter;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsSourceData;
import com.mdiapps.mdi.newstracker.helpers.CustomAutoComplete.SearchAutoComplete;
import com.mdiapps.mdi.newstracker.presenters.HomePresenter;
import com.mdiapps.mdi.newstracker.utils.UrlUtil;
import com.mdiapps.mdi.newstracker.views.main.MainActivity;
import com.mdiapps.mdi.newstracker.views.main.fragments.homedetail.HomeDetailFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mascot on 1/8/2017.
 */
public class HomeFragment extends Fragment implements HomeView {

    ListView list;
    private HomePresenter presenter;
    SearchAutoComplete autoComplete;
    private ProgressDialog progress;

    public static String news_source_id, news_source_title, news_source_detail, news_source_imageurl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("News Tracker");

        list=(ListView) v.findViewById(R.id.list);
        autoComplete = (SearchAutoComplete) v.findViewById(R.id.search_news_source);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        presenter = new HomePresenter(this);

        if(isNetworkAvailable()){
            if(NewsSourceData.news_source_title.size()>0){
                startGetNotice();
            }else{
                presenter.getNotice(UrlUtil.GET_NEWS_SOURCE_URL);
            }

        }else{
            Toast.makeText(getActivity(), "Internet connectivity not available", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    @Override
    public void startGetNotice() {
        progress.dismiss();
        NewsSourceAdapter adapter=new NewsSourceAdapter(getActivity(), NewsSourceData.news_source_id, NewsSourceData.news_source_title, NewsSourceData.news_source_detail, NewsSourceData.news_source_imageurl);
        list.setAdapter(adapter);

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<NewsSourceData.news_source_title.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("news_source_id", NewsSourceData.news_source_id.get(i));
            hm.put("news_source_title", NewsSourceData.news_source_title.get(i));
            hm.put("news_source_imageurl", NewsSourceData.news_source_imageurl.get(i));

            aList.add(hm);
        }
        String[] from = {"news_source_title", "news_source_id", "news_source_imageurl"};
        int[] to = {R.id.txt};
        SimpleAdapter adapter2 = new SimpleAdapter(getActivity(), aList, R.layout.custom_autocomplete_layout, from, to);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                /** Each item in the adapter is a HashMap object.
                 *  So this statement creates the currently clicked hashmap object
                 * */
                HashMap<String, String> hm = (HashMap<String, String>) arg0.getAdapter().getItem(position);
                String selected_news_source_id = hm.get("news_source_id");
                String selected_news_source_title = hm.get("news_source_title");
                String selected_news_source_imageurl = hm.get("news_source_imageurl");

                UrlUtil.GET_NEWS_URL = "https://newsapi.org/v1/articles?source="+selected_news_source_id+"&sortBy=top&apiKey=70155966f37a4c2c85b7a33a234ace41";
                UrlUtil.SELECTED_NEWS_ICON_URL = selected_news_source_imageurl;
                UrlUtil.SELECTED_NEWS_SOURCE_NAME = selected_news_source_title;

                startHomeDetailFragment();

            }
        };
        autoComplete.setOnItemClickListener(itemClickListener);
        autoComplete.setAdapter(adapter2);
        autoComplete.setThreshold(1);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                news_source_id = NewsSourceData.news_source_id.get(position);
                news_source_title = NewsSourceData.news_source_title.get(position);
                news_source_detail = NewsSourceData.news_source_detail.get(position);
                news_source_imageurl = NewsSourceData.news_source_imageurl.get(position);

                UrlUtil.GET_NEWS_URL = "https://newsapi.org/v1/articles?source="+news_source_id+"&sortBy=top&apiKey=70155966f37a4c2c85b7a33a234ace41";
                UrlUtil.SELECTED_NEWS_ICON_URL = news_source_imageurl;
                UrlUtil.SELECTED_NEWS_SOURCE_NAME = news_source_title;

                Toast.makeText(getActivity(),news_source_id,Toast.LENGTH_SHORT).show();
                startHomeDetailFragment();

            }
        });
    }

    public void startHomeDetailFragment() {
        android.support.v4.app.Fragment fr;
        fr = new HomeDetailFragment();
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void errorGetNotice(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
