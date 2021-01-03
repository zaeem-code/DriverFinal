package com.loadease.uberclone.driverapp.Messages;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.loadease.uberclone.driverapp.Activities.Login;
import com.loadease.uberclone.driverapp.R;

import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class notification_genrater {
      Context context;
     Bitmap largeIcon;
    String des; AudioAttributes audioAttributes;
    String title1; String date; String tender_no; String render_cat; String data,city;
      Uri notificationSoundUri;
    private final String id ="admin_channel";
    final Intent intent;
    final int notificationID;
    String uid;

    public notification_genrater(Context context, String data, String title1) {




        this.context=context;
        this.title1 = title1;
      des = data;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationID = new Random().nextInt(3000);



        intent = (new Intent(context, Login.class));
        intent.putExtra("chk",true);
        intent.putExtra("UID",uid);
        Log.v("notti",data);   Log.v("notti",title1);

        if (!TextUtils.isEmpty(des) && !TextUtils.isEmpty(this.title1)){

            processs();

            Log.v("notti",data);   Log.v("notti",title1);

        }

    }

    final NotificationManager notificationManager;
    private  void processs() {

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stupChnl(title1, des);
        }


            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context, id)

                            .setContentTitle(title1)
                            .setContentText(des)
                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(des))
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(title1).setSummaryText("LoadEase"))
                            .setShowWhen(true)
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.truck)
                            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)

                            .setContentIntent(pendingIntent);

            notificationManager.notify(notificationID, notificationBuilder.build());

    }



    @RequiresApi(api = Build.VERSION_CODES.O)

    private void stupChnl( String title, String msg){
        audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();

        NotificationChannel notificationChannelx;
        notificationChannelx = new NotificationChannel(id, title, NotificationManager.IMPORTANCE_HIGH);
        notificationChannelx.setDescription( title+"\n"+msg);
        notificationChannelx.enableLights(true);
        notificationChannelx.setSound(notificationSoundUri,audioAttributes);
notificationChannelx.setVibrationPattern(new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 });
        notificationChannelx.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationChannelx.setLightColor(Color.GREEN);
        notificationChannelx.enableVibration(true);

        if (notificationManager != null) {
            List<NotificationChannel> channelList = notificationManager.getNotificationChannels();

            for (int i = 0; channelList != null && i < channelList.size(); i++) {
                notificationManager.deleteNotificationChannel(channelList.get(i).getId());

            }
            notificationManager.createNotificationChannel(notificationChannelx);
        }



    }
}
