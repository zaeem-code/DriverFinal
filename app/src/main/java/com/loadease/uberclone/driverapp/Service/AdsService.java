//package com.loadease.uberclone.driverapp.Service;
//
//import android.app.AlarmManager;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.SystemClock;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.CompoundButton;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//
//import com.firebase.geofire.GeoLocation;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.loadease.uberclone.driverapp.Common.Common;
//import com.loadease.uberclone.driverapp.Interfaces.locationListener;
//import com.loadease.uberclone.driverapp.R;
//import com.loadease.uberclone.driverapp.Util.Location;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//
//public class AdsService extends Service {
//
//    Context context;
//    private Integer counter;
//    Location location;
//    private DatabaseReference drivers, onlineRef, currentUserRef;
//
//
//
//    public static final String CHANNEL_ID = "ForegroundServiceChannel";
//
//    @Override
//    public void onCreate() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//
//            createNotificationChannel();
////            Intent notificationIntent = new Intent(this, MainActivity.class);
////            PendingIntent pendingIntent = PendingIntent.getActivity(this,
////                    0, notificationIntent, 0);
//
//            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setContentTitle("Smart Keyboard")
//                    .setContentText("app is waiting for response")
//                    .setSmallIcon(R.drawable.car)
////                    .setContentIntent(pendingIntent)
//                    .build();
//            startForeground(1, notification);
//        }
//
//
//        context = this.getApplicationContext();
//
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent)
//    {
//        return null;
//    }
//
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//
//        callbgTask();
//
//
//
//
//
//        return START_STICKY;
//    }
//
//
//
//
//
//    Timer tmr = new Timer();
//    TimerTask tt;
//
//    public void callbgTask()
//    {
//        final Handler handler = new Handler();
//
//
//
//
//    }
//
//
//
//
//
//
//    private void createNotificationChannel()
//    {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    CHANNEL_ID,
//                    "Foreground Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(serviceChannel);
//        }
//    }
//
//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
//        restartServiceIntent.setPackage(getPackageName());
//
//        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        alarmService.set(
//                AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + 1000,
//                restartServicePendingIntent);
//
//        super.onTaskRemoved(rootIntent);
//
//
//
//    }
//
//    private void init() {
//
//
//
//
//
//        onlineRef= FirebaseDatabase.getInstance().getReference(".info/connected");
//
//
//
//
//
//        location=new Location(this, new locationListener() {
//            @Override
//            public void locationResponse(LocationResult response) {
//                // Add a icon_marker in Sydney and move the camera
//                Common.currentLat=response.getLastLocation().getLatitude();
//                Common.currentLng=response.getLastLocation().getLongitude();
//
//                LatLng currentLocation = new LatLng(Common.currentLat, Common.currentLng);
//
//                if (currentLocationMarket != null) currentLocationMarket.remove();
//
//                currentLocationMarket = mMap.addMarker(new MarkerOptions().position(currentLocation)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
//                        .title("Your Location"));
//
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Common.currentLat,
//                        Common.currentLng), 15.0f));
//
//
//                if (pickupGeoFire!=null)
//                {
//                    pickupGeoQuery=pickupGeoFire.queryAtLocation(new GeoLocation(Common.currentLat,Common.currentLng),
//
//                            0.05);
//
//                    pickupGeoQuery.addGeoQueryEventListener(pickupGeoQueryListner);
//                }
//
//
//
//
//
//                if (destinationGeoFire!=null)
//                {
//                    destinationGeoQuery=destinationGeoFire.queryAtLocation(new GeoLocation(Common.currentLat,Common.currentLng),
//
//                            0.05);
//
//                    destinationGeoQuery.addGeoQueryEventListener(destinationGeoQueryListner );
//                }
//
//
//
//
//
//
//                if (!isTripStart)
//                {
//
//
//                    if (chk.equals("false"))
//                    {
//
//                        makeDriverOnline();
//                        Log.v("onRide","online active");
//
//                    }
//                    else
//                    {
//
//
//                        try {
//                            geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                            onlineRef.removeEventListener(onlineValueEventLisner);
//
//                            //                        onlineSystemAlreadyRegister = false;
//
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//                        Log.v("onRide","delete");
//
//
//                    }
//                    Log.v("onRide","online");
//
//
//
//                }
//                else
//                {
//                    if (!TextUtils.isEmpty(tripNumberId))
//                    {
//                        Map<String,Object> update_data=new HashMap<>();
//
//                        update_data.put("currentLat",Common.currentLat);
//                        update_data.put("currentLng",Common.currentLng);
//
//
//                        FirebaseDatabase.getInstance().getReference("Trips").child(user_curr)
//                                .child(tripNumberId)
//                                .updateChildren(update_data).addOnSuccessListener(aVoid -> {
//
//                        }).addOnFailureListener(e -> {
//
//                        });
//
//
//
//                    }
//
//                }
//
//
//
//
//
//
//
//
//            }
//        });
//
//
//
//
//
//
//
//        location.getLocation();
//
//
//
//
//
//    }
//
//}