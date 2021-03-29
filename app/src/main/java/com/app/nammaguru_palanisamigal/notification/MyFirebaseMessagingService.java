package com.app.nammaguru_palanisamigal.notification;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.nammaguru_palanisamigal.APIcalls.ApiCall;
import com.app.nammaguru_palanisamigal.APIcalls.NetworkClient;
import com.app.nammaguru_palanisamigal.APIcalls.Request_params;
import com.app.nammaguru_palanisamigal.model.Upcoming_model;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationUtils notificationUtils;
    String body;
    String title;
    String user_image;
    String ago_value;
    String post_id;
    String flag;
    String user_id;
    String notification_id;
    String post_Image;
    String post_name;
    String author_id;
    String like_values;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {

            String notification_title = remoteMessage.getNotification().getTitle();
            String notification_body = remoteMessage.getNotification().getBody();

            showNotificationMessageFeed(getApplicationContext(), notification_body,notification_title);

        }

    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }

    private void showNotificationMessageFeed(Context context, String body,
                                             String title) {
        notificationUtils = new NotificationUtils(context);
        notificationUtils.sendNotification_feed(body, title);
    }

}