package com.bookyourbeach.byb.notification_center;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bookyourbeach.byb.SplashScreenActivity;
import com.bookyourbeach.byb.helpers.Helper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.bookyourbeach.byb.R;
import com.bookyourbeach.byb.MainActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Map;

public class NotificationService extends FirebaseMessagingService {

    private NotificationConfig notification = new NotificationConfig();

    Target target = new Target() {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            sendNotification(bitmap);

        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }

    };

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        getImage(remoteMessage);

    }
    private void getImage(final RemoteMessage remoteMessage) {

        Map<String, String> dataParams = remoteMessage.getData();

        notification.title = remoteMessage.getData().get("title");
        notification.content = remoteMessage.getData().get("body");

        if(remoteMessage.getData().get("image") != null) {

            notification.image = remoteMessage.getData().get("image");

        }

        Handler uiHandler = new Handler(Looper.getMainLooper());

        uiHandler.post(new Runnable() {
            @Override
            public void run() {

                if (notification.image.isEmpty()) {

                    sendNotification(null);

                } else {

                    Picasso.get()
                            .load(notification.image)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(target); //this is your ImageView

                }
            }
        });

    }


    private void sendNotification(Bitmap bitmap) {

        Intent intent = null;

        int counter = Helper.getCounter(this); // Code by TechyaSoft
        counter++;// Code by TechyaSoft

        if(appInForeground(this)) {

            intent = new Intent(this, MainActivity.class);

        } else {

            intent = new Intent(this, SplashScreenActivity.class);

        }

        intent.putExtra("Fragment", "Notifications");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);  // Code by TechyaSoft


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(notification.title)
                .setContentText(notification.content)
                .setAutoCancel(true)
                .setSound(soundUri)           // Code by TechyaSoft
                .setContentIntent(pendingIntent)
                .setContentInfo(notification.title)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.byb_logo)
                .setNumber(counter)         // Code by TechyaSoft
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notification.content));

        if(bitmap != null) {

            notificationBuilder
                    .setLargeIcon(bitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap));

        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), getString(R.string.notification_channel_name), NotificationManager.IMPORTANCE_DEFAULT
            );

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();  // Code by TechyaSoft

            channel.setDescription("BYB private FCM channel");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            channel.setSound(soundUri,att);  // Code by TechyaSoft
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

    private boolean appInForeground(@NonNull Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();

        if (runningAppProcesses == null) {

            return false;

        }

        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {

            if (runningAppProcess.processName.equals(context.getPackageName()) &&
                    runningAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {

                return true;

            }

        }

        return false;

    }

}