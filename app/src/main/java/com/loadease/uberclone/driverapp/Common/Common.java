package com.loadease.uberclone.driverapp.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.maps.model.LatLng;
import com.loadease.uberclone.driverapp.Interfaces.IFCMService;
import com.loadease.uberclone.driverapp.Interfaces.googleAPIInterface;
import com.loadease.uberclone.driverapp.Model.User;
import com.loadease.uberclone.driverapp.R;
import com.loadease.uberclone.driverapp.Retrofit.FCMClient;
import com.loadease.uberclone.driverapp.Retrofit.RetrofitClient;
import com.loadease.uberclone.driverapp.Service.firebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Common {
    public static final String driver_tbl="Drivers";
    public static final String user_driver_tbl="DriversInformation";
    public static final String history_driver = "DriverHistory";
    public static final String history_rider = "RiderHistory";
    public static final String user_rider_tbl="RidersInformation";
    public static final String pickup_request_tbl="PickupRequest";
    public static final String token_tbl="Tokens";
    public static User currentUser;
    public static User currentRiderprofile;
    public static String userID;
    public static final int PICK_IMAGE_REQUEST = 9999;
    static String Notification_Chanel_Id="notification_alert";

    public static Double currentLat;
    public static Double currentLng;

    public static final String baseURL="https://maps.googleapis.com";
    public static final String fcmURL="https://fcm.googleapis.com/";

    public static double baseFare=2.55;
    private static double timeRate=0.35;
    private static double distanceRate=1.75;

    public static double formulaPrice(double km, double min){
        return baseFare+(distanceRate*km)+(timeRate*min);
    }
    public static googleAPIInterface getGoogleAPI(){
        return RetrofitClient.getClient(baseURL).create(googleAPIInterface.class);
    }
    public static IFCMService getFCMService(){
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }

    public static List<LatLng> decodePoly(String encoded) {
        List poly = new ArrayList();
        int index=0,len=encoded.length();
        int lat=0,lng=0;
        while(index < len)
        {
            int b,shift=0,result=0;
            do{
                b=encoded.charAt(index++)-63;
                result |= (b & 0x1f) << shift;
                shift+=5;

            }while(b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1):(result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do{
                b = encoded.charAt(index++)-63;
                result |= (b & 0x1f) << shift;
                shift +=5;
            }while(b >= 0x20);
            int dlng = ((result & 1)!=0 ? ~(result >> 1): (result >> 1));
            lng +=dlng;

            LatLng p = new LatLng((((double)lat / 1E5)),
                    (((double)lng/1E5)));
            poly.add(p);
        }
        return poly;
    }


    public static void showNotification(Context context, int id, String title, String body, Intent intent)
    {


        PendingIntent pendingIntent=null;
        if (pendingIntent!=null)
            pendingIntent=PendingIntent.getActivity(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O)
        {
            setupChanel(notificationManager,title,body);


        }

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_current_location);

        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder
                (context, Notification_Chanel_Id)
                .setSmallIcon(R.drawable.ic_location)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSoundUri)
               ;

        if (pendingIntent!=null)
        {
            notificationBuilder .setContentIntent(pendingIntent);

        }

//       Notification notification=notificationBuilder.build();
        notificationManager.notify(id, notificationBuilder.build());











    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void setupChanel(NotificationManager notificationManager, String title, String msg)
    {

        NotificationChannel notificationChannelx;
        notificationChannelx = new NotificationChannel
                (Notification_Chanel_Id, title, NotificationManager.IMPORTANCE_HIGH);
        notificationChannelx.setDescription( "  \n "+msg);
        notificationChannelx.enableLights(true);
        notificationChannelx.setLightColor(Color.GREEN);
        notificationChannelx.enableVibration(true);
        if (notificationManager != null)
        {
            notificationManager.createNotificationChannel(notificationChannelx);
        }



    }

    public static String createUniqueTripNumber(long timeOffset) {

        Random random=new Random();
        Long current=System.currentTimeMillis()+timeOffset;
        Long unique=current + random.nextLong();
        if (unique<0)unique*=(-1);


        return String.valueOf(unique);
    }

}
