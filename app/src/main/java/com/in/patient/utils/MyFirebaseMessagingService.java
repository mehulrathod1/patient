package com.in.patient.utils;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.in.patient.activity.MyOrder;
import com.in.patient.globle.Glob;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.Channel;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            JSONObject data = new JSONObject(remoteMessage.getData());
            try {
                String jsonMessage = data.getString("chanel_name");
                Log.e(TAG, "onMessageReceived:" + jsonMessage);
                Glob.Channel_name = jsonMessage;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String click = remoteMessage.getNotification().getClickAction();


            Log.d(TAG, "Message data payload: " + title);
            Log.d(TAG, "Message data payload: " + message);
            Log.d(TAG, "Message data payload: " + click);

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

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
