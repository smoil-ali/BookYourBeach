package com.bookyourbeach.byb;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bookyourbeach.byb.helpers.Helper;
import com.bookyourbeach.byb.notification_center.NotificationConfig;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    final String TAG = MainActivity.class.getSimpleName();
    Animation shake;
    BadgeDrawable badgeDrawable;
    BottomNavigationView navView;
    SharedPreferences pref;
    static String SHARED_PREFERENCES = "sharedPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disable night mode

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Code by TechyaSoft
        pref = this.getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);

        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


        notificationRedirect(navView);

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);

    }

    @Override
    public void onResume(){

        super.onResume();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        notificationRedirect(navView);

    }

    String fragment;

    private void notificationRedirect(BottomNavigationView navView) {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            fragment = extras.getString("Fragment");

            if(fragment != null && fragment.equals("Notifications")) {

                navView.setSelectedItemId(R.id.navigation_notifications);

            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Code by TechyaSoft
        pref.registerOnSharedPreferenceChangeListener(this);
        getCounterValue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Code by TechyaSoft
        pref.unregisterOnSharedPreferenceChangeListener(this);
    }

//    private void sendNotification() {
//
//        Intent intent = null;
//        NotificationConfig notification = new NotificationConfig();
//
//        if(true) {
//
//            intent = new Intent(this, MainActivity.class);
//
//        } else {
//
//            intent = new Intent(this, SplashScreenActivity.class);
//
//        }
//
//        intent.putExtra("Fragment", "Notifications");
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);
//
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
//                .setContentTitle("Test")
//                .setContentText("Testing......")
//                .setAutoCancel(true)
//                .setSound(soundUri)
//                .setContentIntent(pendingIntent)
//                .setContentInfo("Testing....jalksdjfk")
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setSmallIcon(R.mipmap.byb_logo)
//                .setNumber(39)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(notification.content));
//
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    getString(R.string.notification_channel_id), getString(R.string.notification_channel_name), NotificationManager.IMPORTANCE_DEFAULT
//            );
//
//            AudioAttributes att = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
//                    .build();
//
//            channel.setDescription("BYB private FCM channel");
//            channel.setShowBadge(true);
//            channel.canShowBadge();
//            channel.enableLights(true);
//            channel.enableVibration(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
//            channel.setSound(soundUri,att);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0, notificationBuilder.build());
//    }

    // Code by TechyaSoft
    public void getCounterValue(){
        Log.i(TAG,"here");
        int counter = Helper.getCounter(this);
        if (counter == 0){
            navView.removeBadge(R.id.navigation_notifications);
        }else {
            badgeDrawable = navView.getOrCreateBadge(R.id.navigation_notifications);
            badgeDrawable.setNumber(counter);
        }

    }

    // Code by TechyaSoft
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        badgeDrawable = navView.getOrCreateBadge(R.id.navigation_notifications);
        if (s.equals(Helper.VALUE)){
            int counter = Helper.getCounter(this);
            Log.i(TAG,counter+"");
            if (counter == 0){
                navView.removeBadge(R.id.navigation_notifications);
            }else {
                badgeDrawable.setNumber(counter);
            }

        }
    }
}