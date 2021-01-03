package com.loadease.uberclone.driverapp.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
//        if(remoteMessage.getNotification().getTitle().equals("RequestDriver"))
//        {
//            Pickup pickup=new Gson().fromJson(remoteMessage.getNotification().getBody(), Pickup.class);
//            Intent intent=new Intent(getBaseContext(), CustommerCall.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("lat", pickup.getLastLocation().latitude);
//            intent.putExtra("lng", pickup.getLastLocation().longitude);
//            intent.putExtra("rider", pickup.getID());
//            intent.putExtra("token", pickup.getToken().getToken());
//            startActivity(intent);
//        }


        Log.v("FCMMM","in");
        if(remoteMessage.getData().get("title").equals("Action against your account"))
        {
            Log.v("FCMMM","ok");
            if (remoteMessage.getData().get("message").trim()=="You have been Restricted by LoadEase, Contact LoadEase office for more details"
                    || remoteMessage.getData().get("message").trim().equals("Note: You have been Blocked by LoadEase,  Signin For more Details")){

                Log.v("notti","contains");
                  getSharedPreferences("blocked", MODE_PRIVATE).edit().putBoolean("chk", false).apply();
              getSharedPreferences("profile", MODE_PRIVATE).edit().putBoolean("chk", false) .apply();
             getSharedPreferences("Nverified", MODE_PRIVATE).edit().putBoolean("chk", false).apply();
          getSharedPreferences("Login", MODE_PRIVATE).edit().putBoolean("chk", false).apply();


            }

            new notification_genrater(getApplicationContext(),remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
        }


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
//                driverRequestReceived.setFromAddress(datrecover.get("FromAddress"));

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

                Log.v("FCMMM","not RequestDriver");
                Common.showNotification(this,new Random().nextInt(),
                        datrecover.get("title"),
                        datrecover.get("body"),
                        null

                );

            }


        }
        else
        {

            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
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
