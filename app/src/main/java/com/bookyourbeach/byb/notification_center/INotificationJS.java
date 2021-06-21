package com.bookyourbeach.byb.notification_center;

import android.provider.Settings;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class INotificationJS {

    private WebView webView;

    public INotificationJS(WebView webView) {

        this.webView = webView;

    }

    @JavascriptInterface
    public void GetFcmConfig() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {

                    @Override
                    public void onComplete(@NonNull Task<String> task) {

                        if (!task.isSuccessful()) {
                            Log.w("BYB FCM Fetch", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        String android_id = Settings.Secure.getString(webView.getContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);

                        Log.d("BYB FCM Token", token);
                        webView.loadUrl("javascript:BYB_GetFcmTokens('" + android_id + "', '" + token + "')");

                    }

                });

    }

}
