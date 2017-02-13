package com.mdiapps.mdi.newstracker.views.main.fragments.webview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mdiapps.mdi.newstracker.R;
import com.mdiapps.mdi.newstracker.utils.UrlUtil;
import com.mdiapps.mdi.newstracker.views.main.MainActivity;

/**
 * Created by Mascot on 2/12/2017.
 */
public class WebViewFragment extends Fragment {

    private WebView webView;
    private ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webview, container, false);

        ((MainActivity) getActivity()).setActionBarTitle(UrlUtil.SELECTED_NEWS_SOURCE_NAME);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        webView = (WebView) v.findViewById(R.id.webView1);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(UrlUtil.SELECTED_NEWS_MAIN_URL);

        return v;
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.dismiss();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
