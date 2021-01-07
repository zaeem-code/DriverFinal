package com.loadease.uberclone.driverapp.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Interfaces.locationListener;
import com.loadease.uberclone.driverapp.Model.LocationUtils;
import com.loadease.uberclone.driverapp.R;
import com.loadease.uberclone.driverapp.Util.Location;
import com.loadease.uberclone.driverapp.fragment.FragmentDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class AdsService extends Service {

    Context context;
    private Integer counter;
    Location location;
    private DatabaseReference drivers, onlineRef, currentUserRef;

    String city_name;
    private GeoFire geoFire;


    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {

            createNotificationChannel();
//            Intent notificationIntent = new Intent(this, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                    0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Smart Keyboard")
                    .setContentText("app is waiting for response")
                    .setSmallIcon(R.drawable.car)
//                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);
        }


        context = this.getApplicationContext();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {


        callbgTask();





        return START_STICKY;
    }





    TimerTask tt;
    Timer tmr = new Timer();


    public void callbgTask()
    {
        final Handler handler = new Handler();

        tt = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable()
                {
                    public void run()
                    {

                        makeDriverOnline();
                        Log.v("xxx","thread countinue");

                    }
                });
            }
        };
        tmr.schedule(tt, 0, 3000); //execute in ev







    }






    private void createNotificationChannel()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);



    }


    private void makeDriverOnline() {


        String saveCityName=city_name;
        city_name= LocationUtils.getAddressFromLocation(getApplicationContext());



        if (!city_name.equals(saveCityName))
        {
            if (currentUserRef !=null)
                currentUserRef.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                updateDriverLocation();
                            }
                        });
        }
        else
        {
            updateDriverLocation();

        }




    }


    private void updateDriverLocation() {

        if (!TextUtils.isEmpty(city_name))
        {
            try {
                drivers = FirebaseDatabase.getInstance().getReference("Drivers")
                        .child(Common.currentUser.getCarType())
                        .child(city_name);



                currentUserRef = drivers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                geoFire = new GeoFire(drivers);






                ///update loc

                geoFire.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        new GeoLocation(Common.currentLat
                                ,Common.currentLng),

                        (key, error) -> {


                            if (error!=null)
                            {

                            }
                            else
                            {

                            }


                        }
                );





            }catch (Exception e){

            }
        }
        else
        {
            Toast.makeText(this, "service unavailable here ", Toast.LENGTH_SHORT).show();
        }


    }
public void killme(){
        onDestroy();
        stopForeground(true);
         stopSelf();
         tmr.cancel();
         tmr.purge();

}

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        stopSelf();
        tmr.cancel();
        tmr.purge();



    }
}