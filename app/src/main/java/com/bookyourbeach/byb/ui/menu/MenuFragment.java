package com.bookyourbeach.byb.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.bookyourbeach.byb.R;
import com.bookyourbeach.byb.helpers.WebViewHelper;

public class MenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        new WebViewHelper(v.findViewById(R.id.menuWebView), getString(R.string.url_menu)/*, v.findViewById(R.id.swiperefresh_menu)*/);

        return v;

    }

}