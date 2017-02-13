package com.mdiapps.mdi.newstracker.views.main.fragments.homedetail;

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
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsAdapter;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsData;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsSourceAdapter;
import com.mdiapps.mdi.newstracker.helpers.CustomAdapter.NewsSourceData;
import com.mdiapps.mdi.newstracker.helpers.CustomAutoComplete.SearchAutoComplete;
import com.mdiapps.mdi.newstracker.presenters.HomeDetailPresenter;
import com.mdiapps.mdi.newstracker.presenters.HomePresenter;
import com.mdiapps.mdi.newstracker.utils.UrlUtil;
import com.mdiapps.mdi.newstracker.views.main.MainActivity;
import com.mdiapps.mdi.newstracker.views.main.fragments.webview.WebViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mascot on 2/12/2017.
 */
public class HomeDetailFragment extends Fragment implements HomeDetailView {

    ListView list;
    private HomeDetailPresenter presenter;
    private ProgressDialog progress;

    public static String news_author, news_title, news_description, news_datetime, news_imageurl, news_mainurl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_detail, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(UrlUtil.SELECTED_NEWS_SOURCE_NAME);

        list=(ListView) v.findViewById(R.id.list);

        presenter = new HomeDetailPresenter(this);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        if(isNetworkAvailable()){
            presenter.getNotice(UrlUtil.GET_NEWS_URL);
        }else{
            Toast.makeText(getActivity(), "Internet connectivity not available", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    @Override
    public void startGetNotice() {
        progress.dismiss();
        NewsAdapter adapter=new NewsAdapter(getActivity(), NewsData.news_author, NewsData.news_title, NewsData.news_description, NewsData.news_datetime, NewsData.news_imageurl, NewsData.news_mainurl);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                news_author = NewsData.news_author.get(position);
                news_title = NewsData.news_title.get(position);
                news_description = NewsData.news_description.get(position);
                news_datetime = NewsData.news_datetime.get(position);
                news_imageurl = NewsData.news_imageurl.get(position);
                news_mainurl = NewsData.news_mainurl.get(position);

                UrlUtil.SELECTED_NEWS_MAIN_URL = news_mainurl;


                Toast.makeText(getActivity(),news_title,Toast.LENGTH_SHORT).show();
                startWebViewFragment();

            }
        });
    }

    public void startWebViewFragment() {
        android.support.v4.app.Fragment fr;
        fr = new WebViewFragment();
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
