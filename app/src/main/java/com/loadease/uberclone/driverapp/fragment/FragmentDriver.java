package com.loadease.uberclone.driverapp.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.local.LruGarbageCollector;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.kusu.library.LoadingButton;
import com.loadease.uberclone.chatIntegration.Activity.MainChatActivity;
import com.loadease.uberclone.driverapp.Activities.Login;
import com.loadease.uberclone.driverapp.Activities.RateActivity;
import com.loadease.uberclone.driverapp.Activities.TripHistory;
import com.loadease.uberclone.driverapp.Common.Common;
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
import com.loadease.uberclone.driverapp.SelectRideType;
import com.loadease.uberclone.driverapp.Util.Location;
import com.loadease.uberclone.driverapp.Util.UsersUtill;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FragmentDriver extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback
        , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
boolean dataalredyloaded=false;

    String ads ="NA";
    CircleImageView imagecusavatar,imagecusavatarX;
    TextView customerName,pickupAddress,dropoffAddress,customerNameX,pickupAddressX,dropoffAddressX,distancex,timex;
    Button accept_btn, reject_btn;
    RelativeLayout startCancelButtonsLayoutUI;
    String duration,distance;
    DriverRequestReceived eventX;
    FrameLayout root;
    private GoogleMap mMap;
    String phone="";
    CircleImageView Driverdp;
    Location location;
    Button canceltheongoingRide;
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

            UsersUtill.sendNotifyToRider(FragmentDriver.this,snackbarView,key);


            if (pickupGeoQuery!=null && pickupGeoFire!=null)
            {
                pickupGeoFire.removeLocation(key);
                pickupGeoFire=null;
                pickupGeoQuery.removeAllListeners();
            }
        }

        @Override
        public void onKeyExited(String key)
        {

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
//    Disposable countDownEvent;
    View snackbarView;




    LinearLayout layout_accept;
    CircularProgressBar circularProgressBar;
    TextView txt_estimate_time,txt_estimate_distance;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    IGoogleApi_sep iGoogleApi_sep;
    Polyline blackPolyLine,greyPolyLine;
    List<LatLng> polyLineList;
    PolylineOptions polylineOptions,blackPolyLineOption;



//    TextView txt_rating,txt_type_uber,txt_rider_name,txt_start_uber_estimate_distance
//            ,txt_start_uber_estimate_time;
    TextView txt_rating,txt_ratingX,txt_type_uber,txt_type_uberX;
    ImageView img_round,img_phone_call,chat;
    LinearLayout layout_start_uber;
    LoadingButton btn_start_uber,btn_complete_trip;
    String tripNumberId="";
    boolean  isTripStart=false,onlineSystemAlreadyRegister=false;


    LinearLayout layout_notify_rider;
    TextView txt_notify_rider;
    ProgressBar progress_notify;

    SharedPreferences prefs;


    String user_curr;

    String curr_Time="";
    SimpleDateFormat _24HourSDF;
    SimpleDateFormat _12HourSDF;
    Date _24HourDt;
    TextView islamic_D_T;






    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onNotifyToRider(NotifyToRiderEvent event)
    {

//        layout_notify_rider.setVisibility(View.VISIBLE);
//        progress_notify.setMax(1*60);
//        waiting_timer=new CountDownTimer(1*60*1000,1000)
//        {
//            @Override
//            public void onTick(long millisUntilFinished)
//            {
//
//                progress_notify.setProgress(progress_notify.getProgress()+1);
//                txt_notify_rider.setText(String.format("02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(1)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(1)),
//                        TimeUnit.MILLISECONDS.toSeconds(1)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(1)))
//
//
//                );
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();
//




    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onDriverRequestReceive(DriverRequestReceived event)
    {

        eventX=event;
        phone=event.getPhone();

        driverRequestReceived=event;
        DatabaseReference db=FirebaseDatabase.getInstance().getReference("DriverHaveUserID").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        HashMap hashMap=new HashMap();
        hashMap.put("Uid",event.getKey());

        db.updateChildren(hashMap);






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
                                  duration=time.getString("text");


                                JSONObject distanceEstimate=legObject.getJSONObject("distance");
                                  distance=distanceEstimate.getString("text");

                                txt_estimate_time.setText(duration);
                                txt_estimate_distance.setText(distance);



                                distancex.setText(duration);
                                timex.setText(distance);

                                mMap.addMarker(new MarkerOptions()
                                        .position(destination)
                                        .icon(BitmapDescriptorFactory.defaultMarker())
                                        .title("Pickup Location")
                                );


                                createGeoFirePickupLocation(event.getKey(),destination);

                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,160));
                                mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom-1));


                                ///show layout when request of user send to driver

                                layout_accept.setVisibility(View.VISIBLE);
                                if (!dataalredyloaded){
                                    dataalredyloaded=true;
                                    Log.v("hassan","---> repeat:  :"+event.getImageurl());
                                    loadpessengerdata(event);
                                                    }

Log.v("hassan","--->  :"+event.getImageurl());

//////count down
//                                countDownEvent= Observable.interval(100, TimeUnit.MILLISECONDS)
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .doOnNext(x->{
//                                            circularProgressBar.setProgress(circularProgressBar.getProgress()+1f);
//                                        })
//                                        .takeUntil(aLong->aLong==100)
//                                        .doOnComplete(()->{
//
//
////                                            createTripPlan(eventX,duration,distance);
//
//
//                                        }).subscribe();




                            }

                            catch (Exception e)
                            {

                            }




                        })

        );


    }

    private void loadpessengerdata(DriverRequestReceived event) {


        pickupAddress.setText(event.getPuckupLocationString());
        dropoffAddress.setText(event.getDestinationLocationString());




        customerName.setText(event.getName());
        txt_type_uber.setText(event.getVehicaltype());
        txt_rating.setText("1.7");
        Picasso.get().load(event.getImageurl()).into(imagecusavatar);


        pickupAddressX.setText(event.getPuckupLocationString());
        customerNameX.setText(event.getName());
        txt_type_uberX.setText(event.getVehicaltype());
        txt_ratingX.setText("1.7");
        Picasso.get().load(event.getImageurl()).into(imagecusavatarX);

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
                                    @RequiresApi(api = Build.VERSION_CODES.O)
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
                                            tripPlaneModel.setName(Common.currentRiderprofile.getName());
                                            tripPlaneModel.setFromAddress(fromaddressStringget(Common.currentLat,Common.currentLng));
                                            tripPlaneModel.setCarnum(Common.currentRiderprofile.getCarnum());


                                            ZoneId z = ZoneId.of("Asia/Karachi") ;

                                            LocalTime localTime = LocalTime.now( z ) ;
                                            Locale locale_en_US = Locale.forLanguageTag("PK");
                                            DateTimeFormatter formatterUS = DateTimeFormatter.ofLocalizedTime( FormatStyle.SHORT ).withLocale( locale_en_US ) ;
                                            String output = localTime.format( formatterUS ) ;

                                            LocalDate locale_date= LocalDate.now(z);
                                            Locale locale_SAU_date = Locale.forLanguageTag("PK");

                                            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale( locale_SAU_date ) ;
                                            String output2 = locale_date.format( formatter ) ;


                                            _24HourSDF = new SimpleDateFormat("HH:mm");
                                            _12HourSDF = new SimpleDateFormat("hh:mm a");
                                            try {
                                                _24HourDt = _24HourSDF.parse(output);
                                                tripPlaneModel.setTime(_12HourSDF.format(_24HourDt));
                                                tripPlaneModel.setDate(output2);


                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }



                                            tripNumberId=Common.createUniqueTripNumber(timeOffset);
















                                            FirebaseDatabase.getInstance().getReference("Trips").child(event.getKey())
                                                    .child(tripNumberId)
                                                    .setValue(tripPlaneModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

//                                                    txt_rider_name.setText(riderModel.getName());
//                                                    txt_start_uber_estimate_time.setText(duration);
//                                                    txt_start_uber_estimate_distance.setText(distance);

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
//            txt_rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_star_24,0);

        } else
        {
            color_dark_grey = ContextCompat.getColor(getApplicationContext(), android.R.color.white);
            circularProgressBar.setIndeterminateMode(false);
            circularProgressBar.setProgress(0);
//            txt_rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_star_24,0);



        }



//        txt_estimate_time.setTextColor(color_dark_grey);
//        txt_estimate_distance.setTextColor(color_dark_grey);
//        ImageViewCompat.setImageTintList(img_round, ColorStateList.valueOf(color_dark_grey));
//        txt_rating.setTextColor(color_dark_grey);
//        txt_type_uber.setTextColor(color_dark_grey);


    }


//    CountDownTimer waiting_timer;

Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SuscribingTOfcm();
        DatabaseReference db=FirebaseDatabase.getInstance().getReference("DriverHaveUserID").child(Common.userID);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {
                    user_curr=snapshot.child("Uid").getValue().toString();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







canceltheongoingRide=findViewById(R.id.canceltrip);
        startCancelButtonsLayoutUI =findViewById(R.id.startCancelButtonsLayout);

        pickupAddress=findViewById(R.id.pickupadd);
        dropoffAddress=findViewById(R.id.dropoffadd);
        customerName=findViewById(R.id.custmername);
        imagecusavatar =findViewById(R.id.imageCus);


        pickupAddressX=findViewById(R.id.pickupaddx);
        distancex=findViewById(R.id.txt_estimate_distancex);
                timex=findViewById(R.id.txt_estimate_time);
        customerNameX=findViewById(R.id.custmernamex);
        imagecusavatarX=findViewById(R.id.imageCusx);


        accept_btn =findViewById(R.id.chip_acpt);
        reject_btn =findViewById(R.id.chip_decline);


        snackbarView=findViewById(R.id.snackbarView);
        iGoogleApi_sep = RetrofitClient_sep.getInstance().create(IGoogleApi_sep.class);

        txt_rating=findViewById(R.id.txt_rating);
        txt_type_uber=findViewById(R.id.txt_type_uber);
        txt_ratingX=findViewById(R.id.txt_ratingx);
        txt_type_uberX=findViewById(R.id.txt_type_uberx);
//        txt_rider_name=findViewById(R.id.txt_rider_name);
//        txt_start_uber_estimate_distance=findViewById(R.id.txt_start_uber_estimate_distance);
//        txt_start_uber_estimate_time=findViewById(R.id.txt_start_uber_estimate_time);
        img_round=findViewById(R.id.img_round);
        img_phone_call=findViewById(R.id.img_phone_call);
        chat=findViewById(R.id.msg);
        layout_start_uber=findViewById(R.id.layout_start_ubber);
        btn_start_uber=findViewById(R.id.btn_start_uber);
        btn_complete_trip=findViewById(R.id.btn_complete_trip);
        layout_notify_rider=findViewById(R.id.layout_notify_rider);
        txt_notify_rider=findViewById(R.id.txt_notify_rider);
        progress_notify=findViewById(R.id.progress_notify);

        circularProgressBar=findViewById(R.id.circularProgressBar);
        layout_accept=findViewById(R.id.layout_accept);
        txt_estimate_time=findViewById(R.id.txt_estimate_time);
        txt_estimate_distance=findViewById(R.id.txt_estimate_distance);


        initDrawer();
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat();
            }
        });
        img_phone_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(phone)) {

                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+Uri.encode(phone.trim())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);

                }else {
                    Toast.makeText(FragmentDriver.this, "Unable to call driver", Toast.LENGTH_SHORT).show();
                }
            }
        });
accept_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        createTripPlan(eventX,duration,distance);
    }
});


        canceltheongoingRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reject_btn.performClick();
            }
        });
        reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataalredyloaded=false;

                if (driverRequestReceived!=null)
                {
                   if(TextUtils.isEmpty(tripNumberId))
                   {
//                       if (countDownEvent!=null)
//                           countDownEvent.dispose();
                       layout_accept.setVisibility(View.GONE);
                       mMap.clear();

                       UsersUtill.sendDeclineRequest(snackbarView,getApplicationContext(),driverRequestReceived.getKey());

                       driverRequestReceived=null;

                   }
                   else
                   {
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
//                if (waiting_timer!=null)waiting_timer.cancel();

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
                startCancelButtonsLayoutUI.setVisibility(View.GONE);
                btn_start_uber.setVisibility(View.GONE);
                btn_complete_trip.setVisibility(View.VISIBLE);




            }
        });
        btn_complete_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(FragmentDriver.this, "trip complete", Toast.LENGTH_SHORT).show();



                finaltripendDetails();
                dataalredyloaded=false;
                Map<String, Object> update_trip = new HashMap<>();
                update_trip.put("done",true);


                 FirebaseDatabase.getInstance().getReference("Trips").child(user_curr)
                         .child(tripNumberId)
                         .updateChildren(update_trip)
                         .addOnSuccessListener(aVoid -> {

                             UsersUtill.sendCompleteTripToRider(snackbarView,getApplicationContext(),driverRequestReceived.getKey(),
                                     tripNumberId
                             );
                             mMap.clear();
                             tripNumberId="";

                             isTripStart=false;
                             layout_accept.setVisibility(View.GONE);
                             circularProgressBar.setProgress(0);

                             layout_start_uber.setVisibility(View.GONE);
                             layout_notify_rider.setVisibility(View.GONE);
                             progress_notify.setProgress(0);


//                             btn_complete_trip.setEnabled(false);
                             btn_complete_trip.setVisibility(View.GONE);

                             btn_start_uber.setVisibility(View.VISIBLE);


                             destinationGeoFire=null;
                             pickupGeoFire=null;

                             driverRequestReceived=null;


//


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


                                //////
                                JSONObject object=jsonArray.getJSONObject(0);
                                JSONArray legs=object.getJSONArray("legs");
                                JSONObject legObject=legs.getJSONObject(0);

                                JSONObject time=legObject.getJSONObject("duration");
                                duration=time.getString("text");


                                JSONObject distanceEstimate=legObject.getJSONObject("distance");
                                distance=distanceEstimate.getString("text");



                                distancex.setText(distance);
                                timex.setText(duration);

                                /////
                               DatabaseReference fdb= FirebaseDatabase.getInstance().getReference("Trips").child(user_curr)
                                        .child(tripNumberId);
                               fdb.child("time").setValue(duration);
                                fdb.child("durationPickup").setValue(duration);

                                fdb.child("distancePickup").setValue(distance);
                                ////

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


                              FirebaseDatabase.getInstance().getReference("Trips").child(user_curr)
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
             }catch (Exception e){

             }
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
try {


    location.remove_location();
    geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
    onlineRef.removeEventListener(onlineValueEventLisner);


    if (EventBus.getDefault().hasSubscriberForEvent(DriverRequestReceived.class))
        EventBus.getDefault().removeStickyEvent(DriverRequestReceived.class);

    if (EventBus.getDefault().hasSubscriberForEvent(NotifyToRiderEvent.class))
        EventBus.getDefault().removeStickyEvent(NotifyToRiderEvent.class);


    EventBus.getDefault().unregister(this);
    compositeDisposable.clear();
    onlineSystemAlreadyRegister = false;
}catch (Exception e){

}


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
        root=findViewById(R.id.bg);
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
          Driverdp=(CircleImageView) navigationHeaderView.findViewById(R.id.imageAvatar);
        Common.userID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("RidersProfile")
                .child( Common.userID)

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {


                        Common.currentRiderprofile=dataSnapshot.getValue(User.class);

    Log.v("hassan", "current user ->>>>" + FirebaseAuth.getInstance().getCurrentUser().getUid());
    Log.v("hassan", "data ->>>>" + dataSnapshot.getValue(User.class));
    Log.v("hassan", "current user name ->>>>" + Common.currentRiderprofile.getName());
    Log.v("hassan", "current user name ->>>>" + Common.currentRiderprofile.getName());
    Log.v("hassan", "current user pic ->>>>" + Common.currentRiderprofile.getRider_pic_Url());
    if (!TextUtils.isEmpty(Common.currentRiderprofile.getName())) {
        tvName.setText(Common.currentRiderprofile.getName());
    }
    if (!TextUtils.isEmpty(Common.currentRiderprofile.getRider_pic_Url())) {
        Picasso.get().load(Common.currentRiderprofile.getRider_pic_Url()).into(Driverdp);

    }

}catch (Exception e){

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
            Picasso.get().load("https://graph.facebook.com/" + Common.userID + "/picture?width=500&height=500").into(Driverdp);
        else if(account!=null)
            Picasso.get().load(account.getPhotoUrl()).into(Driverdp);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_trip_history:
                startActivity(new Intent(this, TripHistory.class));

                break;
            case R.id.setting:
                startActivity(new Intent(this, MainChatActivity.class));

                break;

            case R.id.nav_sign_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, Login.class));
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                finish();
                break;
            case R.id.notification:
                break;
            case R.id.wallet:
                break;

            case R.id.Vehocaltype:
                startActivity(new Intent(this, SelectRideType.class));
                break;


            case R.id.profile:
                startActivity(new Intent(this, UpdateProfile.class));
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
private String fromaddressStringget(Double lat, Double lng){
    Geocoder geocoder;

    String address="NA";
    List<Address> addresses;
    geocoder = new Geocoder(this, Locale.getDefault());

    try {
        addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
address= addresses.get(0).getAddressLine(0);
    } catch (IOException e) {
        Log.v("adddress","eddress error: "+e);
    }

    Log.v("adddress","eddress error: "+address);
return address;
}


    public void chat() {

        startActivity(new Intent(this, MainChatActivity.class));
    }

    private  void finaltripendDetails(){

        compositeDisposable.add(iGoogleApi_sep.getDirection("driving",
                "less_driving",

                eventX.getPickupLocation(),
                new StringBuilder()
                        .append(Common.currentLat)
                        .append(",")
                        .append(Common.currentLng)
                        .toString(),
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


                                JSONObject object=jsonArray.getJSONObject(0);
                                JSONArray legs=object.getJSONArray("legs");
                                JSONObject legObject=legs.getJSONObject(0);

                                JSONObject time=legObject.getJSONObject("duration");
                                duration=time.getString("text");


                                JSONObject distanceEstimate=legObject.getJSONObject("distance");
                                distance=distanceEstimate.getString("text");

                                 ads = legObject.getString("end_address");




                                Log.v("FinalTrip","Distance final: "+distance);
                                Log.v("FinalTrip","Time final: "+duration);
                                Log.v("FinalTrip","current user final: "+user_curr);
                                Log.v("FinalTrip","trip final: "+tripNumberId);
                                Log.v("FinalTrip","add final: "+ads);
                                Log.v("FinalTrip","add current lat: "+Common.currentLng);


                                DatabaseReference fdb= FirebaseDatabase.getInstance().getReference("Trips").child(user_curr)
                                        .child(tripNumberId);

                                HashMap hashMap= new HashMap();
                                hashMap.put("time",duration);
                                hashMap.put("durationPickup",duration);
                                hashMap.put("distancePickup",distance);
                                hashMap.put("destinationString",ads);
//                                fdb.child("time").setValue(duration);
//                                fdb.child("durationPickup").setValue(duration);
//
//                                fdb.child("distancePickup").setValue(distance);
                                fdb.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        startActivity(new Intent(getApplicationContext(), RateActivity.class).
                                                putExtra("destination",ads).
                                                putExtra("distance",distance).
                                                putExtra("duration",duration).
                                                putExtra("imageurl",eventX.getImageurl()).
                                                putExtra("name",eventX.getName()).
                                                putExtra("key",eventX.getKey()).
                                                putExtra("price",calculatefare(distance,(duration))).
                                                putExtra("from",eventX.getDestinationLocationString()));
                                    }
                                });



                            }

                            catch (Exception e)
                            {
                                Log.v("FinalTrip","adexpd final: "+e);

                            }




                        })

        );




    }

    private String calculatefare(String distance,String time ){
        Log.v("dis"," =dis --->"+distance);
        Log.v("dis"," =dis --->"+time);

        Log.v("dis"," =dis length --->"+distance.length());
        Log.v("dis"," =time length --->"+time.length());

        Log.v("dis"," =sub length - --->"+distance.substring(0,distance.length()-2));
        Log.v("dis"," =sub length - --->"+time.substring(0,time.length()-4));

        Log.v("dis"," =dis repla - --->"+distance.replace("Km"," "));
        Log.v("dis"," =mins repla - --->"+time.replace("mins"," "));

        Log.v("dis"," =--->"+ String.format("%s + %s = $%.2f", distance, time, Common.getPrice(Double.parseDouble(distance.substring(0,distance.length()-2).trim()),
                Integer.parseInt(time.substring(0,time.length()-4).trim()))));

        return String.format("%s + %s = $%.2f", distance, time, Common.getPrice(Double.parseDouble(distance.substring(0,distance.length()-2).trim()),
                Integer.parseInt(time.substring(0,time.length()-4).trim())));

    }
    /////dp uplod
    public void uploadDP(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 007);
            } else {
                opencamera(1);
            }
        }else {

            opencamera(1);
        }
    }

    private void opencamera(int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {

            if (data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                ///ask for permissions
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), photo, "pic", null);

                 Driverdp.setImageBitmap(photo);
                uploadpic( Uri.parse(path));

            } else {
                Snackbar.make(root, "Failed: try again", Snackbar.LENGTH_SHORT).show();
            }


        } else {
            Snackbar.make(root, "Failed: try again", Snackbar.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                storagepermission();
            }

        } else {
            location.onRequestPermissionResult(requestCode, permissions, grantResults);

        }
    }
    private void storagepermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }


}

    ProgressDialog dialog;

    private void uploadpic(Uri img){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Validating...");
        dialog.show();
        String folder="Rider DP";

        StorageReference mStorageRef;

        mStorageRef = FirebaseStorage.getInstance().getReference();

        StorageReference REF = mStorageRef.child("Rider:"+Common.currentRiderprofile.getName()+", "+Common.currentRiderprofile.getPhone()+"/"+folder+"/"+folder+".jpg");

        REF.putFile(img)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        REF.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.v("Hassan", "onSuccess: uri= "+ uri.toString());

                                profileupdating1(  uri.toString());
                            }
                        });
//         imguri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.v("hassan","exp: "+exception);
                           if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });

    }

    private void profileupdating1(String url){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("RidersProfile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.child("rider_pic_Url").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                profileupdating2(url);
            }
        });


    }

    private void profileupdating2(String url){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chatIntegration").child(Common.userID);

        myRef.child("imageURL").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });


    }

    private void SuscribingTOfcm(){




        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult)
            {
//            String newToken = instanceIdResult.getToken();
//
//            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
            }
        });


    }}