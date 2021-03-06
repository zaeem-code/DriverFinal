package com.loadease.uberclone.driverapp.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Messages.DriverRequestReceived;
import com.loadease.uberclone.driverapp.Messages.notification_genrater;
import com.loadease.uberclone.driverapp.Model.Pickup;
import com.loadease.uberclone.driverapp.Model.RiderModel;
import com.loadease.uberclone.driverapp.Model.Token;
import com.loadease.uberclone.driverapp.Model.TokenModel;
import com.loadease.uberclone.driverapp.Model.TripPlaneModel;
import com.loadease.uberclone.driverapp.chatIntegration.Activity.MessageActivity;
import com.loadease.uberclone.driverapp.chatIntegration.Notifications.OreoNotification;
import com.loadease.uberclone.driverapp.fragment.FragmentDriver;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class firebaseMessaging extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);













        Map<String, String> datrecover=remoteMessage.getData();

        if (datrecover!=null)
        {

            Log.v("FCMMM","not null");


            if (datrecover.get("title").equals("RequestDriver"))
            {

                Log.v("FCMMM","RequestDriver");


                DriverRequestReceived driverRequestReceived=new DriverRequestReceived();
                driverRequestReceived.setKey(datrecover.get("RiderKey"));
                driverRequestReceived.setPickupLocation(datrecover.get("PickupLocation"));
                driverRequestReceived.setPuckupLocationString(datrecover.get("PickupLocationString"));
                driverRequestReceived.setDestinationLocation(datrecover.get("DestinationLocation"));
                driverRequestReceived.setDestinationLocationString(datrecover.get("DestinationLocationString"));



                driverRequestReceived.setImageurl(datrecover.get("imageurl"));
                driverRequestReceived.setPhone(datrecover.get("phone"));
                driverRequestReceived.setName(datrecover.get("name"));



                driverRequestReceived.setIndustry(datrecover.get("insurancedetails"));
                driverRequestReceived.setLsbourdetails(datrecover.get("Lsbourdetails"));
                driverRequestReceived.setVehicaltype(datrecover.get("ridetype"));
                driverRequestReceived.setInsurancedetails(datrecover.get("insurancedetails"));

                driverRequestReceived.setDiscountCode(datrecover.get("DiscountCode"));

                driverRequestReceived.setDiscountAmmount(datrecover.get("DiscountAmmount"));
//                driverRequestReceived.setFromAddress(datrecover.get("FromAddress"));
                Log.v("Coupon",datrecover.get("DiscountCode")+", "+datrecover.get("DiscountAmmount"));

                EventBus.getDefault().postSticky(driverRequestReceived);










//                FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset")
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                long timeOffset=snapshot.getValue(Long.class);
//
//                                FirebaseDatabase.getInstance().getReference("RidersInformation")
//                                        .child("100818598893343921903")
//                                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @RequiresApi(api = Build.VERSION_CODES.O)
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                                if (snapshot.exists())
//                                                {
//
//
//
//                                                    RiderModel riderModel=snapshot.getValue(RiderModel.class);
//
//                                                    TripPlaneModel tripPlaneModel=new TripPlaneModel();
//                                                    tripPlaneModel.setDriver(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                                    tripPlaneModel.setRider("100818598893343921903");
//                                                    tripPlaneModel.setUser(Common.currentUser);
//                                                    tripPlaneModel.setRiderModel(riderModel);
//                                                    tripPlaneModel.setOrigin(datrecover.get("PickupLocation"));
//                                                    tripPlaneModel.setOriginString(datrecover.get("PickupLocationString"));
//                                                    tripPlaneModel.setDestination(datrecover.get("DestinationLocation"));
//                                                    tripPlaneModel.setDestinationString(datrecover.get("DestinationLocationString"));
//                                                    tripPlaneModel.setCurrentLat(Common.currentLat);
//                                                    tripPlaneModel.setCurrentLng(Common.currentLng);
//
//                                                    tripPlaneModel.setPic_url(Common.currentRiderprofile.getRider_pic_Url());
//                                                    tripPlaneModel.setFare("100rs");
//                                                    tripPlaneModel.setName(Common.currentRiderprofile.getName());
//                                                    tripPlaneModel.setCarnum(Common.currentRiderprofile.getCarnum());
//                                                    tripPlaneModel.setTrip_status("accept");
//
//
//
//
//
//
//
//                                                    DatabaseReference db=FirebaseDatabase.getInstance().getReference("running_trip");
//                                                    db.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                                            .child(datrecover.get("RiderKey")).setValue(tripPlaneModel);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                                                }
//                                                else
//                                                {
//
//                                                }
//
//
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError error) {
//
//                                            }
//                                        });
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });










            }
            else
            {


                ////for chat notification


                String sented = remoteMessage.getData().get("sented");
                String user = remoteMessage.getData().get("user");

                SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
                String currentUser = preferences.getString("currentuser", "none");
try {

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    if (firebaseUser != null && sented.equals(firebaseUser.getUid())) {
        if (!currentUser.equals(user)) {


            Log.v("section", "send notification");
            Common.showNotification(this, new Random().nextInt(),
                    datrecover.get("title"),
                    datrecover.get("body"),
                    null);

        }
    }

}catch (Exception e){

}

            }


        }
        else
        {

            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }







        String auth=FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (auth!=null) {
            Log.v("msg", "id  " + auth);

            if (remoteMessage.getData().get("title").equals("Vapprove")) {

                Log.v("msg", "Msg  " + remoteMessage.getData().get("message"));


                getSharedPreferences("verified", MODE_PRIVATE).edit().putString("chk", "true").apply();
                new notification_genrater(getApplicationContext(), "Verification process was successful", remoteMessage.getData().get("message"));


            } else if (remoteMessage.getData().get("title").equals("VNapprove")) {

                getSharedPreferences("verified", MODE_PRIVATE).edit().putString("chk", "false").apply();
                new notification_genrater(getApplicationContext(), "You have been Restricted", remoteMessage.getData().get("message"));

            } else if (remoteMessage.getData().get("title").equals("Nblocked")) {

                getSharedPreferences("blocked", MODE_PRIVATE).edit().putString("chk", "false").apply();
                new notification_genrater(getApplicationContext(), "UnBlocked by LoadEase", remoteMessage.getData().get("message"));

            } else if (remoteMessage.getData().get("title").equals("blocked")) {

                getSharedPreferences("blocked", MODE_PRIVATE).edit().putString("chk", "true").apply();
                new notification_genrater(getApplicationContext(), "Blocked by LoadEase", remoteMessage.getData().get("message"));

            } else {
                Log.v("msg", "empty");
            }
        }

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference(Common.token_tbl);

        Token token=new Token(s);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)tokens.child(FirebaseAuth.getInstance().getUid())
                .setValue(token);
    }









}
