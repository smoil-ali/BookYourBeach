package com.bookyourbeach.byb.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bookyourbeach.byb.R;
import com.bookyourbeach.byb.helpers.WebViewHelper;

public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        new WebViewHelper(v.findViewById(R.id.dashboardWebView), getString(R.string.url_dashboard)/*, v.findViewById(R.id+.swiperefresh_dashboard)*/);

        return v;

    }

}