package com.bookyourbeach.byb.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bookyourbeach.byb.MainActivity;
import com.bookyourbeach.byb.notification_center.INotificationJS;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class WebViewHelper extends MainActivity{

    private WebView webView;
    boolean firstLoad = true;
    //SwipeRefreshLayout swipeRefreshLayout;

    public WebViewHelper(WebView webView, String url/*, SwipeRefreshLayout refreshLayout*/){

        this.webView = webView;
        //this.swipeRefreshLayout = refreshLayout;

        setupWebView();
        load(url);

    }

    private void load(String url) {

        this.webView.loadUrl(url);

    }

    private void setupWebView() {

        webView.addJavascriptInterface(new INotificationJS(webView), "INotificationJS");

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);

        if(this.firstLoad) {

            this.firstLoad = false;

            //handling fcm once
            setWebViewFCM();

        }

        webView.canGoBack();
        webView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {

                    webView.goBack();
                    return true;

                }

                return false;

            }

        });

        /*.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(webView.getUrl());
            }

        });*/

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg) {

                WebView.HitTestResult result = view.getHitTestResult();
                String data = result.getExtra();
                Context context = view.getContext();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
                context.startActivity(browserIntent);
                return false;

            }

        });

    }

    private void setWebViewFCM() {

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(webView, url);
                //swipeRefreshLayout.setRefreshing(false);
                webView.loadUrl("javascript:setTimeout(window.INotificationJS.GetFcmConfig(),1000);");

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(webView.getContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }

        });

    }


}
