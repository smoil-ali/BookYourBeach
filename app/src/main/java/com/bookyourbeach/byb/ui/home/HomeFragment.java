package com.bookyourbeach.byb.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.bookyourbeach.byb.R;
import com.bookyourbeach.byb.helpers.WebViewHelper;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        new WebViewHelper(v.findViewById(R.id.homeWebView), getString(R.string.url_home)/*, v.findViewById(R.id.swiperefresh_home)*/);

        return v;

    }

}