package com.loadease.uberclone.driverapp.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Messages.DriverRequestReceived;
import com.loadease.uberclone.driverapp.Messages.notification_genrater;
import com.loadease.uberclone.driverapp.Model.Pickup;
import com.loadease.uberclone.driverapp.Model.Token;
import com.loadease.uberclone.driverapp.Model.TokenModel;

import org.greenrobot.eventbus.EventBus;

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
                  new notification_genrater(getApplicationContext(),remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
        }


        Map<String, String> datrecover=remoteMessage.getData();

        if (datrecover!=null)
        {



            if (datrecover.get("title").equals("RequestDriver"))
            {


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



            }
            else
            {

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
