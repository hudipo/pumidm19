package com.hudipo.pum_indomaret.service;

import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalActivity;
import com.hudipo.pum_indomaret.features.approval.fragment.ApprovalFragment;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.status.StatusActivity;
import com.hudipo.pum_indomaret.utils.StartActivity;

import java.util.Map;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseService";


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: " + s);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("FIREBASE_TOKEN");
        intent.putExtra("token", s);
        localBroadcastManager.sendBroadcast(intent);
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(remoteMessage);
        Log.d(TAG, "onMessageReceived: " + remoteMessage);
        Log.d(TAG, "onMessageReceived: "+remoteMessage.getData());

    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String title = getString(R.string.app_name);
        String body="";
        if(remoteMessage.getNotification()!=null){
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }
        Map<String, String> data = remoteMessage.getData();

        int notificationId = 0;
        if(data.get("kodeNotif")!=null){
            notificationId = Integer.parseInt(Objects.requireNonNull(data.get("kodeNotif")));
            Log.d(TAG, "sendNotification: ");
        }

        Intent intent;
        if(notificationId == 1){
            intent = new Intent(this, ApprovalActivity.class);
        }else if(notificationId == 2){
            intent = new Intent(this, StatusActivity.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel for Apps",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
