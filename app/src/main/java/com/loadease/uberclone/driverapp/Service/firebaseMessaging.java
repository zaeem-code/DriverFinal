package com.loadease.uberclone.driverapp.Service;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.loadease.uberclone.driverapp.Activities.CustommerCall;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Model.Pickup;
import com.loadease.uberclone.driverapp.Model.Token;

public class firebaseMessaging extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification().getTitle().equals("Pickup")){
            Pickup pickup=new Gson().fromJson(remoteMessage.getNotification().getBody(), Pickup.class);
            Intent intent=new Intent(getBaseContext(), CustommerCall.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("lat", pickup.getLastLocation().latitude);
            intent.putExtra("lng", pickup.getLastLocation().longitude);
            intent.putExtra("rider", pickup.getID());
            intent.putExtra("token", pickup.getToken().getToken());
            startActivity(intent);
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
