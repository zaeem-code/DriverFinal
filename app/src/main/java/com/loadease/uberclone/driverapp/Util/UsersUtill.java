package com.loadease.uberclone.driverapp.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Model.FCMSendData;
import com.loadease.uberclone.driverapp.Model.NotifyToRiderEvent;
import com.loadease.uberclone.driverapp.Model.Token;
import com.loadease.uberclone.driverapp.Retrofit.IFCMService_sep;
import com.loadease.uberclone.driverapp.Retrofit.RetrofitFCMClient;
import com.loadease.uberclone.driverapp.fragment.FragmentDriver;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class UsersUtill {
    public static void sendDeclineRequest(View snackbarView, Context applicationContext, String key) {






        CompositeDisposable compositeDisposable=new CompositeDisposable();
        IFCMService_sep ifcmServiceSep = RetrofitFCMClient.getInstance().create(IFCMService_sep.class);


        FirebaseDatabase.getInstance().getReference(Common.token_tbl).child(key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists())
                        {

                            Token tokenModel=dataSnapshot.getValue(Token.class);
                            Map<String, String> notificationData=new HashMap<>();

                            notificationData.put("title","Decline");
                            notificationData.put("body","Msg from driver decline");
                            notificationData.put("DriverKey", FirebaseAuth.getInstance().getCurrentUser().getUid());





                            FCMSendData fcmSendData=new FCMSendData(tokenModel.getToken(),notificationData);
                            compositeDisposable.add(ifcmServiceSep.sendNotification(fcmSendData)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(fcmResponse -> {

                                        if (fcmResponse.getSuccess()==0)
                                        {
                                            compositeDisposable.clear();
                                            Snackbar.make(snackbarView,"fail to decline",Snackbar.LENGTH_SHORT).show();

                                        }
                                        else
                                        {
                                            Snackbar.make(snackbarView,"decline success",Snackbar.LENGTH_SHORT).show();

                                        }


                                    }, throwable -> {

                                        compositeDisposable.clear();
                                        Snackbar.make(snackbarView,throwable.getMessage(),Snackbar.LENGTH_SHORT).show();

                                    }));


                        }
                        else
                        {
                            Snackbar.make(snackbarView,"driver token not found",Snackbar.LENGTH_SHORT).show();

                            compositeDisposable.clear();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        compositeDisposable.clear();
                    }
                });







    }









    public static void sendAcceptRequestToRider(View snackbarView, Context applicationContext, String key, String tripNumberId) {


        CompositeDisposable compositeDisposable=new CompositeDisposable();
        IFCMService_sep ifcmServiceSep = RetrofitFCMClient.getInstance().create(IFCMService_sep.class);

        FirebaseDatabase.getInstance().getReference(Common.token_tbl).child(key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists())
                        {

                            Token tokenModel=dataSnapshot.getValue(Token.class);
                            Map<String, String> notificationData=new HashMap<>();

                            notificationData.put("title","Accept");
                            notificationData.put("body","Msg from driver Accept");
                            notificationData.put("DriverKey", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            notificationData.put("TripKey",tripNumberId);




                            FCMSendData fcmSendData=new FCMSendData(tokenModel.getToken(),notificationData);
                            compositeDisposable.add(ifcmServiceSep.sendNotification(fcmSendData)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(fcmResponse -> {

                                        if (fcmResponse.getSuccess()==0)
                                        {
                                            compositeDisposable.clear();
                                            Snackbar.make(snackbarView,"fail to accept",Snackbar.LENGTH_SHORT).show();

                                        }
                                        else
                                        {
                                            Snackbar.make(snackbarView,"Accept success",Snackbar.LENGTH_SHORT).show();

                                        }


                                    }, throwable -> {

                                        compositeDisposable.clear();
                                        Snackbar.make(snackbarView,throwable.getMessage(),Snackbar.LENGTH_SHORT).show();

                                    }));


                        }
                        else
                        {
                            compositeDisposable.clear();
                            Snackbar.make(snackbarView,"driver token not found",Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        compositeDisposable.clear();
                    }
                });







    }

    public static void sendNotifyToRider(Context context , View snackbarView, String key) {

        CompositeDisposable compositeDisposable=new CompositeDisposable();
        IFCMService_sep ifcmServiceSep = RetrofitFCMClient.getInstance().create(IFCMService_sep.class);

        FirebaseDatabase.getInstance().getReference(Common.token_tbl).child(key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists())
                        {

                            Token tokenModel=dataSnapshot.getValue(Token.class);
                            Map<String, String> notificationData=new HashMap<>();

                            notificationData.put("title","Driver Arrived");
                            notificationData.put("body","Your Driver Arrived");
                            notificationData.put("DriverKey", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            notificationData.put("Rider Key",key );




                            FCMSendData fcmSendData=new FCMSendData(tokenModel.getToken(),notificationData);
                            compositeDisposable.add(ifcmServiceSep.sendNotification(fcmSendData)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(fcmResponse -> {

                                        if (fcmResponse.getSuccess()==0)
                                        {
                                            compositeDisposable.clear();
                                            Snackbar.make(snackbarView,"fail to accept",Snackbar.LENGTH_SHORT).show();

                                        }
                                        else
                                        {
//                                            Snackbar.make(snackbarView,"Accept success",Snackbar.LENGTH_SHORT).show();


                                            EventBus.getDefault().postSticky(new NotifyToRiderEvent());


                                        }


                                    }, throwable -> {

                                        compositeDisposable.clear();
                                        Snackbar.make(snackbarView,throwable.getMessage(),Snackbar.LENGTH_SHORT).show();

                                    }));


                        }
                        else
                        {
                            compositeDisposable.clear();
                            Snackbar.make(snackbarView,"driver token not found",Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        compositeDisposable.clear();
                    }
                });



    }

    public static void sendDeclineAndRemoveTripRequest(View snackbarView, Context applicationContext, String key, String tripNumberId) {


        CompositeDisposable compositeDisposable=new CompositeDisposable();
        IFCMService_sep ifcmServiceSep = RetrofitFCMClient.getInstance().create(IFCMService_sep.class);


        SharedPreferences prefs = applicationContext.getSharedPreferences("ip", MODE_PRIVATE);
        String user_curr = prefs.getString("user_curr", "");

        if (!user_curr.equals("")) {


            FirebaseDatabase.getInstance().getReference("Trips").child(user_curr)
                    .child(tripNumberId)
                    .removeValue()
                    .addOnSuccessListener(aVoid -> FirebaseDatabase.getInstance().getReference(Common.token_tbl).child(key)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {

                                        Token tokenModel = dataSnapshot.getValue(Token.class);
                                        Map<String, String> notificationData = new HashMap<>();

                                        notificationData.put("title", "DeclineAndRemoveTrip");
                                        notificationData.put("body", "Msg from driver decline");
                                        notificationData.put("DriverKey", FirebaseAuth.getInstance().getCurrentUser().getUid());


                                        FCMSendData fcmSendData = new FCMSendData(tokenModel.getToken(), notificationData);
                                        compositeDisposable.add(ifcmServiceSep.sendNotification(fcmSendData)
                                                .subscribeOn(Schedulers.newThread())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(fcmResponse -> {

                                                    if (fcmResponse.getSuccess() == 0) {
                                                        compositeDisposable.clear();
                                                        Snackbar.make(snackbarView, "fail to decline", Snackbar.LENGTH_SHORT).show();

                                                    } else {
                                                        Snackbar.make(snackbarView, "decline success", Snackbar.LENGTH_SHORT).show();

                                                    }


                                                }, throwable -> {

                                                    compositeDisposable.clear();
                                                    Snackbar.make(snackbarView, throwable.getMessage(), Snackbar.LENGTH_SHORT).show();

                                                }));


                                    } else {
                                        Snackbar.make(snackbarView, "driver token not found", Snackbar.LENGTH_SHORT).show();

                                        compositeDisposable.clear();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                    compositeDisposable.clear();
                                }
                            })).addOnFailureListener(e -> {


            });


        }
        else
        {
            Toast.makeText(applicationContext, "curr null", Toast.LENGTH_SHORT).show();
        }

    }

    public static void sendCompleteTripToRider(View snackbarView, Context applicationContext, String key, String tripNumberId) {


        CompositeDisposable compositeDisposable=new CompositeDisposable();
        IFCMService_sep ifcmServiceSep = RetrofitFCMClient.getInstance().create(IFCMService_sep.class);


                        FirebaseDatabase.getInstance().getReference(Common.token_tbl).child(key)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists())
                                        {

                                            Token tokenModel=dataSnapshot.getValue(Token.class);
                                            Map<String, String> notificationData=new HashMap<>();

                                            notificationData.put("title","DriverCompleteTrip");
                                            notificationData.put("body","Msg from driver complete trip");
                                            notificationData.put("TripKey", tripNumberId);





                                            FCMSendData fcmSendData=new FCMSendData(tokenModel.getToken(),notificationData);
                                            compositeDisposable.add(ifcmServiceSep.sendNotification(fcmSendData)
                                                    .subscribeOn(Schedulers.newThread())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(fcmResponse -> {

                                                        if (fcmResponse.getSuccess()==0)
                                                        {
                                                            compositeDisposable.clear();
                                                            Snackbar.make(snackbarView,"fail to complete trip",Snackbar.LENGTH_SHORT).show();

                                                        }
                                                        else
                                                        {
                                                            Snackbar.make(snackbarView,"Thankyou ! you have complete trip",Snackbar.LENGTH_SHORT).show();

                                                        }


                                                    }, throwable -> {

                                                        compositeDisposable.clear();
                                                        Snackbar.make(snackbarView,throwable.getMessage(),Snackbar.LENGTH_SHORT).show();

                                                    }));


                                        }
                                        else
                                        {
                                            Snackbar.make(snackbarView,"driver token not found",Snackbar.LENGTH_SHORT).show();

                                            compositeDisposable.clear();
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                        compositeDisposable.clear();
                                    }
                                });







    }
}
