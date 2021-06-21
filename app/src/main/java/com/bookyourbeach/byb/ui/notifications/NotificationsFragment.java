package com.bookyourbeach.byb.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.bookyourbeach.byb.MainActivity;
import com.bookyourbeach.byb.R;
import com.bookyourbeach.byb.helpers.Helper;
import com.bookyourbeach.byb.helpers.WebViewHelper;

public class NotificationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        new WebViewHelper(v.findViewById(R.id.notificationsWebView), getString(R.string.url_notifications)/*, v.findViewById(R.id.swiperefresh_notifications)*/);
        Helper.setCounter(getContext(),0); // Code by TechyaSoft
        return v;

    }




}