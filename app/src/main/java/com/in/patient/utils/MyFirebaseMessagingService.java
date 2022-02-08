package com.in.patient.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.in.patient.R;
import com.in.patient.activity.MainActivity;
import com.in.patient.activity.MyOrder;
import com.in.patient.activity.VideoCallScreen;
import com.in.patient.globle.Glob;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.Channel;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage);

        Log.d(TAG, "Fro00000m: " + remoteMessage.getData().get("chanel_name"));

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());


//            JSONObject data = new JSONObject(remoteMessage.getData());
//            try {
//
//                String jsonMessage = data.getString("chanel_name");
//                Log.e(TAG, "onMessageReceived:" + jsonMessage);
//                Glob.Channel_name = jsonMessage;
//
//                Log.e(TAG, "onMessageReceived:" + Glob.Channel_name);
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }


        String jsonMessage = remoteMessage.getData().get("chanel_name");
        Log.e(TAG, "onMessageReceived:" + jsonMessage);
        Glob.Channel_name = jsonMessage;

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        String click = remoteMessage.getNotification().getClickAction();

        Log.e("tittllrlrr", "title :" + title);
        Log.e(TAG, "message:" + message);
        Log.e(TAG, "click:" + click);


    }

    public void showNotification(String title, String message, RemoteMessage remoteMessage) {


        String channel_name = remoteMessage.getData().get("chanel_name");


        Intent intent = new Intent(this, VideoCallScreen.class);
        intent.putExtra("channel", channel_name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myNotification");
        builder.setContentTitle(title)
                .setSmallIcon(R.drawable.ic_baseline_arrow_forward_24)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    @Override
    public void onDeletedMessages() {

    }

    @Override
    public void onMessageSent(String msgId) {

    }

    @Override
    public void onNewToken(String token) {

    }

    @Override
    public void onSendError(String msgId, Exception exception) {

    }

}
