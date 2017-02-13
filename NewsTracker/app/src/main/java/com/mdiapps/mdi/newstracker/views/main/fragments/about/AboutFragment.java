package com.mdiapps.mdi.newstracker.views.main.fragments.about;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mdiapps.mdi.newstracker.R;
import com.mdiapps.mdi.newstracker.utils.UrlUtil;
import com.mdiapps.mdi.newstracker.views.main.MainActivity;

/**
 * Created by Mascot on 2/12/2017.
 */
public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("About");

        return v;
    }


}
