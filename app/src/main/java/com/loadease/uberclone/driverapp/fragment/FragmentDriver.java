package com.loadease.uberclone.driverapp.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kusu.library.LoadingButton;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Helpers.FirebaseHelper;
import com.loadease.uberclone.driverapp.Interfaces.locationListener;
import com.loadease.uberclone.driverapp.Messages.DriverRequestReceived;
import com.loadease.uberclone.driverapp.Model.LocationUtils;
import com.loadease.uberclone.driverapp.Model.NotifyToRiderEvent;
import com.loadease.uberclone.driverapp.Model.RiderModel;
import com.loadease.uberclone.driverapp.Model.Token;
import com.loadease.uberclone.driverapp.Model.TripPlaneModel;
import com.loadease.uberclone.driverapp.Model.User;
import com.loadease.uberclone.driverapp.R;
import com.loadease.uberclone.driverapp.Retrofit.IGoogleApi_sep;
import com.loadease.uberclone.driverapp.Retrofit.RetrofitClient_sep;
import com.loadease.uberclone.driverapp.Util.Location;
import com.loadease.uberclone.driverapp.Util.UsersUtill;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

    public class FragmentDriver extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback
        , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{


    private GoogleMap mMap;
    Location location;
    private Marker currentLocationMarket;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount account;

    AccessToken accessToken = AccessToken.getCurrentAccessToken();
    boolean isLoggedInFacebook = accessToken != null && !accessToken.isExpired();


    GeoFire pickupGeoFire,destinationGeoFire;
    GeoQuery pickupGeoQuery,destinationGeoQuery;


    GeoQueryEventListener pickupGeoQueryListner=new GeoQueryEventListener() {
        @Override
        public void onKeyEntered(String key, GeoLocation location) {

            btn_start_uber.setEnabled(true);
            UsersUtill.sendNotifyToRider(FragmentDriver.this,snackbarView,key);


            if (pickupGeoQuery!=null)
            {
                pickupGeoFire.removeLocation(key);
                pickupGeoFire=null;
                pickupGeoQuery.removeAllListeners();
            }
        }

        @Override
        public void onKeyExited(String key) {


            btn_start_uber.setEnabled(false);
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location) {

        }

        @Override
        public void onGeoQueryReady() {

        }

        @Override
        public void onGeoQueryError(DatabaseError error) {

        }



    };

    GeoQueryEventListener destinationGeoQueryListner=new GeoQueryEventListener() {
        @Override
        public void onKeyEntered(String key, GeoLocation location) {

            Toast.makeText(FragmentDriver.this, "destination enter", Toast.LENGTH_SHORT).show();
            
            btn_complete_trip.setEnabled(true);

            if (destinationGeoQuery!=null)
            {
                destinationGeoFire.removeLocation(key);
                destinationGeoFire=null;
                destinationGeoQuery.removeAllListeners();
            }
        }

        @Override
        public void onKeyExited(String key) {

        }

        @Override
        public void onKeyMoved(String key, GeoLocation location) {

        }

        @Override
        public void onGeoQueryReady() {

        }

        @Override
        public void onGeoQueryError(DatabaseError error) {

        }
        
    };




    String city_name;
    private DatabaseReference drivers, onlineRef, currentUserRef;
    private GeoFire geoFire;
    ValueEventListener onlineValueEventLisner=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists() && currentUserRef!=null)
            {
                currentUserRef.onDisconnect().removeValue();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };





    private static final int REQUEST_CODE_PERMISSION=100;
    private static final int PLAY_SERVICES_REQUEST_CODE=2001;
    DriverRequestReceived driverRequestReceived;
    Disposable countDownEvent;
    View snackbarView;




    Chip chip_decline;
    CardView layout_accept;
    CircularProgressBar circularProgressBar;
    TextView txt_estimate_time,txt_estimate_distance;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    IGoogleApi_sep iGoogleApi_sep;
    Polyline blackPolyLine,greyPolyLine;
    List<LatLng> polyLineList;
    PolylineOptions polylineOptions,blackPolyLineOption;



    TextView txt_rating,txt_type_uber,txt_rider_name,txt_start_uber_estimate_distance
            ,txt_start_uber_estimate_time;
    ImageView img_round,img_phone_call;
    CardView layout_start_uber;
    LoadingButton btn_start_uber,btn_complete_trip;
    String tripNumberId="";
    boolean  isTripStart=false,onlineSystemAlreadyRegister=false;


    LinearLayout layout_notify_rider;
    TextView txt_notify_rider;
    ProgressBar progress_notify;


    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onNotifyToRider(NotifyToRiderEvent event)
    {

        layout_notify_rider.setVisibility(View.VISIBLE);
        progress_notify.setMax(1*60);
        waiting_timer=new CountDownTimer(1*60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {

                progress_notify.setProgress(progress_notify.getProgress()+1);
                txt_notify_rider.setText(String.format("02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(1)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(1)),
                        TimeUnit.MILLISECONDS.toSeconds(1)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(1)))


                );

            }

            @Override
            public void onFinish() {

            }
        }.start();





    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onDriverRequestReceive(DriverRequestReceived event)
    {


        driverRequestReceived=event;



        compositeDisposable.add(iGoogleApi_sep.getDirection("driving",
                "less_driving",
                new StringBuilder()
                        .append(Common.currentLat)
                        .append(",")
                        .append(Common.currentLng)
                        .toString(),
                event.getPickupLocation(),
                "AIzaSyCFMRJrFq6OajUBDKd42R3t783B0-Rbzdg")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(returnResult->{




                            try {


                                JSONObject jsonObject=new JSONObject(returnResult);
                                JSONArray jsonArray=jsonObject.getJSONArray("routes");
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject route=jsonArray.getJSONObject(i);
                                    JSONObject poly=route.getJSONObject("overview_polyline");
                                    String polyline=poly.getString("points");
                                    polyLineList=Common.decodePoly(polyline);


                                }


                                polylineOptions=new PolylineOptions();
                                polylineOptions.color(Color.GRAY);
                                polylineOptions.width(12);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.addAll(polyLineList);

                                greyPolyLine=mMap.addPolyline(polylineOptions);


                                blackPolyLineOption=new PolylineOptions();
                                blackPolyLineOption.color(Color.BLACK);
                                blackPolyLineOption.width(5);
                                blackPolyLineOption.startCap(new SquareCap());
                                blackPolyLineOption.jointType(JointType.ROUND);
                                blackPolyLineOption.addAll(polyLineList);

                                blackPolyLine=mMap.addPolyline(blackPolyLineOption);



////////animation


                                ValueAnimator valueAnimator=ValueAnimator.ofInt(0, 100);
                                valueAnimator.setDuration(1100);
                                valueAnimator.setRepeatCount(ValueAnimator.INFINITE );
                                valueAnimator.setInterpolator(new LinearInterpolator());
                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {


                                        List<LatLng> points=greyPolyLine.getPoints();
                                        int percentValue=(int)valueAnimator.getAnimatedValue();
                                        int size=points.size();
                                        int newPoints=(int)(size * (percentValue/100.0f));
                                        List<LatLng> p=points.subList(0,newPoints);
                                        blackPolyLine.setPoints(p);


                                    }
                                });
                                valueAnimator.start();



                                LatLng origin=new LatLng(Common.currentLat,Common.currentLng );
                                LatLng destination=new LatLng(Double.parseDouble(event.getPickupLocation().split(",")[0]),
                                        Double.parseDouble(event.getPickupLocation().split(",")[1])
                                );
                                LatLngBounds latLngBounds=new LatLngBounds.Builder()
                                        .include(origin)
                                        .include(destination)
                                        .build();


                                ////add car icon


                                JSONObject object=jsonArray.getJSONObject(0);
                                JSONArray legs=object.getJSONArray("legs");
                                JSONObject legObject=legs.getJSONObject(0);

                                JSONObject time=legObject.getJSONObject("duration");
                                String duration=time.getString("text");


                                JSONObject distanceEstimate=legObject.getJSONObject("distance");
                                String distance=distanceEstimate.getString("text");

                                txt_estimate_time.setText(duration);
                                txt_estimate_distance.setText(distance);

                                mMap.addMarker(new MarkerOptions()
                                        .position(destination)
                                        .icon(BitmapDescriptorFactory.defaultMarker())
                                        .title("Pickup Location")
                                );


                                createGeoFirePickupLocation(event.getKey(),destination);

                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,160));
                                mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom-1));


                                ///show layout

                                chip_decline.setVisibility(View.VISIBLE);
                                layout_accept.setVisibility(View.VISIBLE);


//////count down
                                countDownEvent= Observable.interval(100, TimeUnit.MILLISECONDS)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .doOnNext(x->{
                                            circularProgressBar.setProgress(circularProgressBar.getProgress()+1f);
                                        })
                                        .takeUntil(aLong->aLong==100)
                                        .doOnComplete(()->{


                                            createTripPlan(event,duration,distance);


                                        }).subscribe();









                            }

                            catch (Exception e)
                            {

                            }




                        })

        );


    }

    private void createGeoFirePickupLocation(String key, LatLng destination) {



        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("TripPickupLocation");

        pickupGeoFire=new GeoFire(ref);
        pickupGeoFire.setLocation(key, new GeoLocation(destination.latitude, destination.longitude),
                new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {

                    }
                }
        );


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createTripPlan(DriverRequestReceived event, String duration, String distance) {


        setProcessLayout(true);

        FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        long timeOffset=snapshot.getValue(Long.class);

                        FirebaseDatabase.getInstance().getReference("RidersInformation")
                                .child(event.getKey())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (snapshot.exists())
                                        {
                                            RiderModel riderModel=snapshot.getValue(RiderModel.class);

                                            TripPlaneModel tripPlaneModel=new TripPlaneModel();
                                            tripPlaneModel.setDriver(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            tripPlaneModel.setRider(event.getKey());
                                            tripPlaneModel.setUser(Common.currentUser);
                                            tripPlaneModel.setRiderModel(riderModel);
                                            tripPlaneModel.setOrigin(event.getPickupLocation());
                                            tripPlaneModel.setOriginString(event.getPuckupLocationString());
                                            tripPlaneModel.setDestination(event.getDestinationLocation());
                                            tripPlaneModel.setDestinationString(event.getDestinationLocationString());
                                            tripPlaneModel.setDistancePickup(distance);
                                            tripPlaneModel.setDurationPickup(duration);
                                            tripPlaneModel.setCurrentLat(Common.currentLat);
                                            tripPlaneModel.setPic_url(Common.currentRiderprofile.getRider_pic_Url());
                                            tripPlaneModel.setFare("100rs");

                                            tripNumberId=Common.createUniqueTripNumber(timeOffset);

                                            FirebaseDatabase.getInstance().getReference("Trips")
                                                    .child(tripNumberId)
                                                    .setValue(tripPlaneModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    txt_rider_name.setText(riderModel.getName());
                                                    txt_start_uber_estimate_time.setText(duration);
                                                    txt_start_uber_estimate_distance.setText(distance);

                                                    setOffLineModForDriver(event,duration,distance);



                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Toast.makeText(FragmentDriver.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });




                                        }
                                        else
                                        {
                                            Toast.makeText(FragmentDriver.this, "can not find rider wth key", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setOffLineModForDriver(DriverRequestReceived event, String duration, String distance)
    {


        UsersUtill.sendAcceptRequestToRider(snackbarView,getApplicationContext(),event.getKey(),tripNumberId);


        ///go offline

        if (currentUserRef !=null)
        {
            currentUserRef.removeValue();

            setProcessLayout(false);
            layout_accept.setVisibility(View.GONE);
            layout_start_uber.setVisibility(View.VISIBLE);


            isTripStart=true;
        }


    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setProcessLayout(boolean isProcess)
    {

        int color_dark_grey = -1;
        if (isProcess) {
            color_dark_grey = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
            circularProgressBar.setIndeterminateMode(true);
            txt_rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_star_24,0);

        } else
        {
            color_dark_grey = ContextCompat.getColor(getApplicationContext(), android.R.color.white);
            circularProgressBar.setIndeterminateMode(false);
            circularProgressBar.setProgress(0);
            txt_rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_star_24,0);



        }



        txt_estimate_time.setTextColor(color_dark_grey);
        txt_estimate_distance.setTextColor(color_dark_grey);
        ImageViewCompat.setImageTintList(img_round, ColorStateList.valueOf(color_dark_grey));
        txt_rating.setTextColor(color_dark_grey);
        txt_type_uber.setTextColor(color_dark_grey);


    }


    CountDownTimer waiting_timer;

Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        snackbarView=findViewById(R.id.snackbarView);
        iGoogleApi_sep = RetrofitClient_sep.getInstance().create(IGoogleApi_sep.class);

        txt_rating=findViewById(R.id.txt_rating);
        txt_type_uber=findViewById(R.id.txt_type_uber);
        txt_rider_name=findViewById(R.id.txt_rider_name);
        txt_start_uber_estimate_distance=findViewById(R.id.txt_start_uber_estimate_distance);
        txt_start_uber_estimate_time=findViewById(R.id.txt_start_uber_estimate_time);
        img_round=findViewById(R.id.img_round);
        img_phone_call=findViewById(R.id.img_phone_call);
        layout_start_uber=findViewById(R.id.layout_start_ubber);
        btn_start_uber=findViewById(R.id.btn_start_uber);
        btn_complete_trip=findViewById(R.id.btn_complete_trip);
        layout_notify_rider=findViewById(R.id.layout_notify_rider);
        txt_notify_rider=findViewById(R.id.txt_notify_rider);
        progress_notify=findViewById(R.id.progress_notify);

        chip_decline=findViewById(R.id.chip_decline);
        circularProgressBar=findViewById(R.id.circularProgressBar);
        layout_accept=findViewById(R.id.layout_accept);
        txt_estimate_time=findViewById(R.id.txt_estimate_time);
        txt_estimate_distance=findViewById(R.id.txt_estimate_distance);


        initDrawer();



        chip_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (driverRequestReceived!=null)
                {
                   if(TextUtils.isEmpty(tripNumberId))
                   {
                       if (countDownEvent!=null)
                           countDownEvent.dispose();
                       chip_decline.setVisibility(View.GONE);
                       layout_accept.setVisibility(View.GONE);
                       mMap.clear();

                       UsersUtill.sendDeclineRequest(snackbarView,getApplicationContext(),driverRequestReceived.getKey());

                       driverRequestReceived=null;

                   }
                   else
                   {
                       chip_decline.setVisibility(View.GONE);
                       layout_start_uber.setVisibility(View.GONE);
                       mMap.clear();

                       UsersUtill.sendDeclineAndRemoveTripRequest(snackbarView,getApplicationContext()
                               ,driverRequestReceived.getKey(),tripNumberId );

                       tripNumberId="";

                       driverRequestReceived=null;

                       makeDriverOnline();


                   }



                }

            }
        });


        btn_start_uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (blackPolyLine!=null)blackPolyLine.remove();
                if (greyPolyLine!=null)greyPolyLine.remove();
                if (waiting_timer!=null)waiting_timer.cancel();

                layout_notify_rider.setVisibility(View.GONE);
                if (driverRequestReceived!=null)
                {
                    LatLng destinationLatLng=new LatLng(
                             Double.parseDouble(driverRequestReceived.getDestinationLocation().split(",")[0])
                            ,Double.parseDouble(driverRequestReceived.getDestinationLocation().split(",")[1])
                    );


                    mMap.addMarker(new MarkerOptions()
                    .position(destinationLatLng)
                            .title(driverRequestReceived.getDestinationLocationString())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    );


                    drawPathFromCurrentLocation(driverRequestReceived.getDestinationLocation());




                }

                btn_start_uber.setVisibility(View.GONE);
                chip_decline.setVisibility(View.GONE);
                btn_complete_trip.setVisibility(View.VISIBLE);




            }
        });


        btn_complete_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(FragmentDriver.this, "trip complete", Toast.LENGTH_SHORT).show();



                Map<String, Object> update_trip = new HashMap<>();
                update_trip.put("done",true);
                FirebaseDatabase.getInstance().getReference("Trips")
                        .child(tripNumberId)
                        .updateChildren(update_trip)
                        .addOnSuccessListener(aVoid -> {

                            UsersUtill.sendCompleteTripToRider(snackbarView,getApplicationContext(),driverRequestReceived.getKey(),
                                    tripNumberId
                                    );

                            mMap.clear();
                            tripNumberId="";

                            isTripStart=false;
                            chip_decline.setVisibility(View.GONE);
                            layout_accept.setVisibility(View.GONE);
                            circularProgressBar.setProgress(0);

                            layout_start_uber.setVisibility(View.GONE);
                            layout_notify_rider.setVisibility(View.GONE);
                            progress_notify.setProgress(0);


                            btn_complete_trip.setEnabled(false);
                            btn_complete_trip.setVisibility(View.GONE);

                            btn_start_uber.setEnabled(false);
                            btn_start_uber.setVisibility(View.VISIBLE);


                            destinationGeoFire=null;
                            pickupGeoFire=null;

                            driverRequestReceived=null;

                            makeDriverOnline();




                        }).addOnFailureListener(e -> {

                        });



            }
        });



        verifyGoogleAccount();


        init();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





     updateFirebaseToken();

    }

    private void drawPathFromCurrentLocation(String destinationLocation) {



        compositeDisposable.add(iGoogleApi_sep.getDirection("driving",
                "less_driving",
                new StringBuilder()
                        .append(Common.currentLat)
                        .append(",")
                        .append(Common.currentLng)
                        .toString(),
                destinationLocation,
                "AIzaSyCFMRJrFq6OajUBDKd42R3t783B0-Rbzdg")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(returnResult->{




                            try {


                                JSONObject jsonObject=new JSONObject(returnResult);
                                JSONArray jsonArray=jsonObject.getJSONArray("routes");
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject route=jsonArray.getJSONObject(i);
                                    JSONObject poly=route.getJSONObject("overview_polyline");
                                    String polyline=poly.getString("points");
                                    polyLineList=Common.decodePoly(polyline);


                                }


                                polylineOptions=new PolylineOptions();
                                polylineOptions.color(Color.GRAY);
                                polylineOptions.width(12);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.addAll(polyLineList);

                                greyPolyLine=mMap.addPolyline(polylineOptions);


                                blackPolyLineOption=new PolylineOptions();
                                blackPolyLineOption.color(Color.BLACK);
                                blackPolyLineOption.width(5);
                                blackPolyLineOption.startCap(new SquareCap());
                                blackPolyLineOption.jointType(JointType.ROUND);
                                blackPolyLineOption.addAll(polyLineList);

                                blackPolyLine=mMap.addPolyline(blackPolyLineOption);





                                LatLng origin=new LatLng(Common.currentLat,Common.currentLng );
                                LatLng destination=new LatLng(Double.parseDouble(destinationLocation.split(",")[0]),
                                        Double.parseDouble(destinationLocation.split(",")[1])
                                );
                                LatLngBounds latLngBounds=new LatLngBounds.Builder()
                                        .include(origin)
                                        .include(destination)
                                        .build();



                                createGeoFireDestinationLocation(driverRequestReceived.getKey(),destination);



                                ////add car icon


                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,160));
                                mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom-1));





                            }

                            catch (Exception e)
                            {

                            }




                        })

        );


    }

    private void createGeoFireDestinationLocation(String key, LatLng destination) {

DatabaseReference ref=FirebaseDatabase.getInstance().getReference("TripDestinationLocation");
destinationGeoFire=new GeoFire(ref);
destinationGeoFire.setLocation(key, new GeoLocation(destination.latitude, destination.longitude), new GeoFire.CompletionListener() {
    @Override
    public void onComplete(String key, DatabaseError error) {

    }
});


    }

    private void init() {





        onlineRef= FirebaseDatabase.getInstance().getReference(".info/connected");





        location=new Location(this, new locationListener() {
            @Override
            public void locationResponse(LocationResult response) {
                // Add a icon_marker in Sydney and move the camera
                Common.currentLat=response.getLastLocation().getLatitude();
                Common.currentLng=response.getLastLocation().getLongitude();

                LatLng currentLocation = new LatLng(Common.currentLat, Common.currentLng);

                if (currentLocationMarket != null) currentLocationMarket.remove();

                currentLocationMarket = mMap.addMarker(new MarkerOptions().position(currentLocation)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker))
                        .title("Your Location"));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Common.currentLat,
                        Common.currentLng), 15.0f));


                if (pickupGeoFire!=null)
                {
                    pickupGeoQuery=pickupGeoFire.queryAtLocation(new GeoLocation(Common.currentLat,Common.currentLng),

                            0.05);

                    pickupGeoQuery.addGeoQueryEventListener(pickupGeoQueryListner);
                }





                if (destinationGeoFire!=null)
                {
                    destinationGeoQuery=destinationGeoFire.queryAtLocation(new GeoLocation(Common.currentLat,Common.currentLng),

                            0.05);

                    destinationGeoQuery.addGeoQueryEventListener(destinationGeoQueryListner );
                }








                if (!isTripStart)
                {


                  makeDriverOnline();



                }
                else
                {
                    if (!TextUtils.isEmpty(tripNumberId))
                    {
                         Map<String,Object> update_data=new HashMap<>();

                         update_data.put("currentLat",Common.currentLat);
                        update_data.put("currentLng",Common.currentLng);

                        FirebaseDatabase.getInstance().getReference("Trips")
                                .child(tripNumberId)
                                .updateChildren(update_data).addOnSuccessListener(aVoid -> {




                                }).addOnFailureListener(e -> {

                                });





                    }

                }








            }
        });







        location.getLocation();





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


//        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//        List<Address> addressList;
//        try {
//
//            addressList = geocoder.getFromLocation(Common.currentLat,
//                    Common.currentLng, 1);
//            city_name = addressList.get(0).getLocality();


//            drivers = FirebaseDatabase.getInstance().getReference("Drivers")
//                    .child(Common.currentUser.getCarType())
//                    .child(city_name);
//
//
//            currentUserRef = drivers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//            geoFire = new GeoFire(drivers);
//
//
//            ///update loc
//
//            geoFire.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(),
//                    new GeoLocation(Common.currentLat
//                            ,Common.currentLng),
//
//                    (key, error) -> {
//
//
//                        if (error!=null)
//                        {
//                            Toast.makeText(FragmentDriver.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                        else
//                        {
//                            Toast.makeText(FragmentDriver.this, "you are online", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//            );
//
//            registerOnlineSystem();





//
//        } catch (Exception e) {
//
//        }


    }

    private void updateDriverLocation() {

        if (!TextUtils.isEmpty(city_name))
        {
             try {
                 drivers = FirebaseDatabase.getInstance().getReference("Drivers")
                         .child(Common.currentUser.getCarType())
                         .child(city_name);
             }catch (Exception e){

             }


            currentUserRef = drivers.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            geoFire = new GeoFire(drivers);


            ///update loc

            geoFire.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    new GeoLocation(Common.currentLat
                            ,Common.currentLng),

                    (key, error) -> {


                        if (error!=null)
                        {
                            Toast.makeText(FragmentDriver.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(FragmentDriver.this, "you are online", Toast.LENGTH_SHORT).show();
                        }


                    }
            );

            registerOnlineSystem();

        }
        else
        {
            Toast.makeText(this, "service unavailable here ", Toast.LENGTH_SHORT).show();
        }


    }


    public void loadDriverInformation(){
        FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl)
                .child(Common.userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Common.currentUser = dataSnapshot.getValue(User.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    private void verifyGoogleAccount()
    {
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        OptionalPendingResult<GoogleSignInResult> opr=Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()){
            GoogleSignInResult result= opr.get();
            handleSignInResult(result);
        }else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>()
            {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }



    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess())
        {
            account = result.getSignInAccount();
            Common.userID=account.getId();

            loadDriverInformation();
        }
        else if(isLoggedInFacebook)
        {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            String id=object.optString("id");
                            Common.userID=id;

                            loadDriverInformation();

                        }
                    });
            request.executeAsync();
        }

        else
            {

            Common.userID=FirebaseAuth.getInstance().getCurrentUser().getUid();

            loadDriverInformation();
        }
    }



    private void updateFirebaseToken() {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        final DatabaseReference tokens=db.getReference(Common.token_tbl);

        final Token token=new Token(FirebaseInstanceId.getInstance().getToken());
        if(FirebaseAuth.getInstance().getUid()!=null) tokens.child(FirebaseAuth.getInstance().getUid()).setValue(token);
        else if(account!=null) tokens.child(account.getId()).setValue(token);
        else{
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            String id = object.optString("id");
                            tokens.child(id).setValue(token);
                        }
                    });
            request.executeAsync();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        location.remove_location();
        geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
        onlineRef.removeEventListener(onlineValueEventLisner);


        if (EventBus.getDefault().hasSubscriberForEvent(DriverRequestReceived.class))
            EventBus.getDefault().removeStickyEvent(DriverRequestReceived.class);

        if (EventBus.getDefault().hasSubscriberForEvent(NotifyToRiderEvent.class))
            EventBus.getDefault().removeStickyEvent(NotifyToRiderEvent.class);




        EventBus.getDefault().unregister(this );
        compositeDisposable.clear();
        onlineSystemAlreadyRegister=false;



    }

    @Override
    protected void onResume() {
        super.onResume();

            registerOnlineSystem();

    }

    @Override
    protected void onStart() {
        super.onStart();
        location.inicializeLocation();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    private void registerOnlineSystem() {


        if (!onlineSystemAlreadyRegister)
        {
            onlineRef.addValueEventListener(onlineValueEventLisner);

            onlineSystemAlreadyRegister=true;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);



        try {

            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle
                    (getApplicationContext(), R.raw.uber_style_map));

            if (!success)
            {
                Toast.makeText(this, "load map fail", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "load map ", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e)
        {

        }




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        location.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStop() {

        super.onStop();
        location.stopUpdateLocation();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        location.inicializeLocation();


    }

    @Override
    public void onConnectionSuspended(int i)
    {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    DrawerLayout drawer;
    public void menueopen(View view) {
        if (drawer!=null) {
            drawer.openDrawer(GravityCompat.START);
        }
    }
    public void initDrawer(){
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        findViewById(R.id.menue).setOnClickListener(this::menueopen);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navigationHeaderView=navigationView.getHeaderView(0);
        TextView tvName=(TextView)navigationHeaderView.findViewById(R.id.tvDriverName);
        TextView tvStars=(TextView)navigationHeaderView.findViewById(R.id.tvStars);
        CircleImageView imageAvatar=(CircleImageView) navigationHeaderView.findViewById(R.id.imageAvatar);
        Common.userID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("RidersProfile")
                .child( Common.userID)

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.v("hassan",   "current user ->>>>"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.v("hassan",   "data ->>>>"+dataSnapshot.getValue(User.class));
 Common.currentRiderprofile=dataSnapshot.getValue(User.class);


                        Log.v("hassan",   "current user name ->>>>"+Common.currentRiderprofile.getName());


                        Log.v("hassan",   "current user name ->>>>"+Common.currentRiderprofile.getName());
                        Log.v("hassan",   "current user pic ->>>>"+Common.currentRiderprofile.getRider_pic_Url());if (!TextUtils.isEmpty(Common.currentRiderprofile.getName())){
                            tvName.setText(Common.currentRiderprofile.getName());
                        }
                        if (!TextUtils.isEmpty(Common.currentRiderprofile.getRider_pic_Url())){
                            Picasso.get().load(Common.currentRiderprofile.getRider_pic_Url()).into(imageAvatar);

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



try {


    if (Common.currentUser.getRates() != null) {
        tvStars.setText(Common.currentUser.getRates());
    }
}catch (Exception e){
    //error ha rates empty ka

}
        if(isLoggedInFacebook)
            Picasso.get().load("https://graph.facebook.com/" + Common.userID + "/picture?width=500&height=500").into(imageAvatar);
        else if(account!=null)
            Picasso.get().load(account.getPhotoUrl()).into(imageAvatar);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



}