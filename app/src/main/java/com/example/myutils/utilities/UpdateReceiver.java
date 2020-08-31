package com.example.myutils.utilities;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.libramotors.libmot.R;

public class UpdateReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {

        FirebaseImplementation.initialize(context, () -> {
            new BannerDownloader().fetchImage(context);

            try {
                String appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

                String fireData = DataPersistence.getFirebasePrefs(context).getApp_version();
                String[] updateInfo = fireData.split("-");
              //  if (!appVersion.equalsIgnoreCase(updateInfo[0]))
                    //sendNotification(context);
            }catch (Exception e){}});
    }

    private void sendNotification(Context context){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "update")
                .setContentText(context.getResources().getString(R.string.app_update_mandatory))
                .setContentTitle("New version available")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());

    }
}
