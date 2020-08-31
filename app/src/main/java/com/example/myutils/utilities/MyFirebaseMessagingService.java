package com.example.myutils.utilities;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        sendNotification(remoteMessage);
//    notify2(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.e("token generated",s);
        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

//    private void sendNotification(RemoteMessage remoteMessage){
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
//                .setContentText(remoteMessage.getNotification().getBody())
//                .setContentTitle(remoteMessage.getNotification().getTitle())
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setStyle(new NotificationCompat.BigTextStyle())
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setSmallIcon(R.drawable.ic_stat_libmot)
//                .setAutoCancel(true);
//
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(132,notificationBuilder.build());
//
//    }

//    private void notify2(RemoteMessage remoteMessage){
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        String channelId = "Default";
//        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.drawable.ic_stat_libmot)
//                .setContentTitle(remoteMessage.getNotification().getTitle())
//                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
//            manager.createNotificationChannel(channel);
//        }
//        manager.notify(132, builder.build());
//    }
}
