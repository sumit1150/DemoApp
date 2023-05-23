package com.demoapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFcmMessageListenerService extends FirebaseMessagingService {

    NotificationManager notificationManager;
    NotificationCompat.Builder notificationBuilder;
    // CleverTapAPI.getDefaultInstance(getApplicationContext()).pushFcmRegistrationId(s,true);



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Custom Rendering Notification comes from CleverTap
        //  new CTFcmMessageHandler().createNotification(getApplicationContext(), remoteMessage);
        // new CTFcmMessageHandler().processPushAmp(getApplicationContext(), remoteMessage);
      super.onMessageReceived(remoteMessage);


//Custom Handling
//new CTFcmMessageHandler().createNotification(getApplicationContext(), remoteMessage);

        //old way of custom handling

        try {
            if (remoteMessage.getData().size() > 0) {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }

                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);

                if (info.fromCleverTap) {
                    new CTFcmMessageHandler().createNotification(getApplicationContext(), remoteMessage);
                    CleverTapAPI.createNotification(getApplicationContext(), extras);

                } else {
                    // not from CleverTap handle yourself or pass to another provider

                    //Complete custom rendering

                    Bundle bundle=new Bundle();
                    Map<String, String> messageData = remoteMessage.getData();
                    Log.d("Message: ", messageData.toString());
                    //String CID = messageData.get("wzrk_cid");
                    //String ID = messageData.get("wzrk_id");
                    String title = messageData.get("nt");
                    String text = messageData.get("nm");
                    //String Deeplink=messageData.get("wzrk_dl");
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    for (Map.Entry<String, String> entry : messageData.entrySet()) {
                        //intent.putExtra(entry.getKey(), entry.getValue());
                        bundle.putString(entry.getKey(), entry.getValue());
                    }
                    intent.putExtra("bundle",bundle);
                    PendingIntent pendingIntent;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        // Create a PendingIntent using FLAG_IMMUTABLE.
                        pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);

                    } else {
                        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    }
                    //To simulate the pending intent mutability crash
                    // pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    //PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);

                    //Notification custom Builder
                    notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationBuilder = new NotificationCompat.Builder(this, "General");
                    notificationBuilder.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setSmallIcon(R.drawable.noti_icon)
                            .setContentIntent(pendingIntent)
                            //  .setWhen(System.currentTimeMillis())
                            //.setTicker(remoteMessage.getNotification().getTicker())
                            //.setPriority(remoteMessage.getPriority())
                            .setContentTitle(title)
                            .setContentText(text);
                    //.setContentInfo("Information");
                    CleverTapAPI.processPushNotification(getApplicationContext(),bundle);
                    notificationManager.notify(1, notificationBuilder.build());
                    CleverTapAPI.getDefaultInstance(this).pushNotificationViewedEvent(bundle);

                }
            }
        } catch (Throwable t) {
            Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }

    }




    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("Firebase Token:",s);
        //CleverTapAPI.getDefaultInstance(this).pushFcmRegistrationId(s,true);
        //String fcmRegId= String.valueOf(FirebaseMessaging.getInstance().getToken());
        CleverTapAPI.getDefaultInstance(getApplicationContext()).pushFcmRegistrationId(s,true);
    }
}
