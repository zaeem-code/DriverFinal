//package com.loadease.uberclone.driverapp.Activities;
//
//import android.Manifest;
//import android.animation.ValueAnimator;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.res.ColorStateList;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.location.Address;
//import android.location.Geocoder;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.SystemClock;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.cardview.widget.CardView;
//import androidx.core.app.ActivityCompat;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.widget.SwitchCompat;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import com.google.android.gms.maps.model.JointType;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.gms.maps.model.SquareCap;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.material.chip.Chip;
//import com.google.android.material.navigation.NavigationView;
//
//import androidx.core.content.ContextCompat;
//import androidx.core.view.GravityCompat;
//import androidx.core.widget.ImageViewCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import android.view.MenuItem;
//import android.view.animation.Interpolator;
//import android.view.animation.LinearInterpolator;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.facebook.AccessToken;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginManager;
//import com.firebase.geofire.GeoFire;
//import com.firebase.geofire.GeoLocation;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.OptionalPendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MapStyleOptions;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.EmailAuthProvider;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.kusu.library.LoadingButton;
//import com.loadease.uberclone.driverapp.Common.Common;
//import com.loadease.uberclone.driverapp.Interfaces.locationListener;
//import com.loadease.uberclone.driverapp.Messages.DriverRequestReceived;
//import com.loadease.uberclone.driverapp.Messages.Errors;
//import com.loadease.uberclone.driverapp.Messages.Message;
//import com.loadease.uberclone.driverapp.Model.RiderModel;
//import com.loadease.uberclone.driverapp.Model.Token;
//import com.loadease.uberclone.driverapp.Model.TripPlaneModel;
//import com.loadease.uberclone.driverapp.Model.User;
//import com.loadease.uberclone.driverapp.R;
//import com.loadease.uberclone.driverapp.Retrofit.IGoogleApi_sep;
//import com.loadease.uberclone.driverapp.Retrofit.RetrofitClient_sep;
//import com.loadease.uberclone.driverapp.Util.Location;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.MultiplePermissionsReport;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//import com.loadease.uberclone.driverapp.Util.UsersUtill;
//import com.mikhaellopez.circularprogressbar.CircularProgressBar;
//import com.rengwuxian.materialedittext.MaterialEditText;
//import com.squareup.picasso.Picasso;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import dmax.dialog.SpotsDialog;
//import io.reactivex.Observable;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//
//public class DriverHome extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
//        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//    private Toolbar toolbar;
//    private SwitchCompat locationSwitch;
//    private Marker currentLocationMarket;
//    private SupportMapFragment mapFragment;
//
//    private GoogleMap mMap;
//
//
//    private FirebaseStorage firebaseStorage;
//    private StorageReference storageReference;
//    private DatabaseReference drivers, onlineRef, currentUserRef;
//    private GeoFire geoFire;
//
//    private GoogleSignInAccount account;
//    private GoogleApiClient mGoogleApiClient;
//
//    private Location location;
//
//    //Facebook
//    AccessToken accessToken = AccessToken.getCurrentAccessToken();
//    boolean isLoggedInFacebook = accessToken != null && !accessToken.isExpired();
//
//    private static final int REQUEST_CODE_PERMISSION=100;
//    private static final int PLAY_SERVICES_REQUEST_CODE=2001;
//    String city_name;
//    DriverRequestReceived driverRequestReceived;
//    Disposable countDownEvent;
//    View snackbarView;
//
//
//
//
//    Chip chip_decline;
//    CardView layout_accept;
//    CircularProgressBar circularProgressBar;
//    TextView txt_estimate_time,txt_estimate_distance;
//    CompositeDisposable compositeDisposable=new CompositeDisposable();
//    IGoogleApi_sep iGoogleApi_sep;
//    Polyline blackPolyLine,greyPolyLine;
//    List<LatLng> polyLineList;
//    PolylineOptions polylineOptions,blackPolyLineOption;
//
//
//
//
//
//
//
//    TextView txt_rating,txt_type_uber,txt_rider_name,txt_start_uber_estimate_distance
//            ,txt_start_uber_estimate_time;
//    ImageView img_round,img_phone_call;
//    CardView layout_start_uber;
//    LoadingButton btn_start_uber;
//    String tripNumberId="";
//    boolean isTripStart=false,onlineSystemAlreadyRegister=false;
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
//    public void onDriverRequestReceive(DriverRequestReceived event)
//    {
//
//
//         driverRequestReceived=event;
//
//
//
//        compositeDisposable.add(iGoogleApi_sep.getDirection("driving",
//                "less_driving",
//                new StringBuilder()
//                        .append(Common.currentLat)
//                        .append(",")
//                        .append(Common.currentLng)
//                        .toString(),
//                event.getPickupLocation(),
//                "AIzaSyCFMRJrFq6OajUBDKd42R3t783B0-Rbzdg")
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(returnResult->{
//
//
//
//
//                            try {
//
//
//                                JSONObject jsonObject=new JSONObject(returnResult);
//                                JSONArray jsonArray=jsonObject.getJSONArray("routes");
//                                for (int i=0;i<jsonArray.length();i++)
//                                {
//                                    JSONObject route=jsonArray.getJSONObject(i);
//                                    JSONObject poly=route.getJSONObject("overview_polyline");
//                                    String polyline=poly.getString("points");
//                                    polyLineList=Common.decodePoly(polyline);
//
//
//                                }
//
//
//                                polylineOptions=new PolylineOptions();
//                                polylineOptions.color(Color.GRAY);
//                                polylineOptions.width(12);
//                                polylineOptions.startCap(new SquareCap());
//                                polylineOptions.jointType(JointType.ROUND);
//                                polylineOptions.addAll(polyLineList);
//
//                                greyPolyLine=mMap.addPolyline(polylineOptions);
//
//
//                                blackPolyLineOption=new PolylineOptions();
//                                blackPolyLineOption.color(Color.BLACK);
//                                blackPolyLineOption.width(5);
//                                blackPolyLineOption.startCap(new SquareCap());
//                                blackPolyLineOption.jointType(JointType.ROUND);
//                                blackPolyLineOption.addAll(polyLineList);
//
//                                blackPolyLine=mMap.addPolyline(blackPolyLineOption);
//
//
//
//////////animation
//
//
//                                ValueAnimator valueAnimator=ValueAnimator.ofInt(0, 100);
//                                valueAnimator.setDuration(1100);
//                                valueAnimator.setRepeatCount(ValueAnimator.INFINITE );
//                                valueAnimator.setInterpolator(new LinearInterpolator());
//                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                    @Override
//                                    public void onAnimationUpdate(ValueAnimator animation) {
//
//
//                                        List<LatLng> points=greyPolyLine.getPoints();
//                                        int percentValue=(int)valueAnimator.getAnimatedValue();
//                                        int size=points.size();
//                                        int newPoints=(int)(size * (percentValue/100.0f));
//                                        List<LatLng> p=points.subList(0,newPoints);
//                                        blackPolyLine.setPoints(p);
//
//
//                                    }
//                                });
//                                valueAnimator.start();
//
//
//
//                                LatLng origin=new LatLng(Common.currentLat,Common.currentLng );
//                                LatLng destination=new LatLng(Double.parseDouble(event.getPickupLocation().split(",")[0]),
//                                        Double.parseDouble(event.getPickupLocation().split(",")[1])
//                                );
//                                LatLngBounds latLngBounds=new LatLngBounds.Builder()
//                                        .include(origin)
//                                        .include(destination)
//                                        .build();
//
//
//                                ////add car icon
//
//
//                                JSONObject object=jsonArray.getJSONObject(0);
//                                JSONArray legs=object.getJSONArray("legs");
//                                JSONObject legObject=legs.getJSONObject(0);
//
//                                JSONObject time=legObject.getJSONObject("duration");
//                                String duration=time.getString("text");
//
//
//                                JSONObject distanceEstimate=legObject.getJSONObject("distance");
//                                String distance=distanceEstimate.getString("text");
//
//                                txt_estimate_time.setText(duration);
//                                txt_estimate_distance.setText(distance);
//
//                                mMap.addMarker(new MarkerOptions()
//                                        .position(destination)
//                                        .icon(BitmapDescriptorFactory.defaultMarker())
//                                        .title("Pickup Location")
//                                );
//
//
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,160));
//                                mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom-1));
//
//
//                                ///show layout
//
//                                chip_decline.setVisibility(View.VISIBLE);
//                                layout_accept.setVisibility(View.VISIBLE);
//
//
////////count down
//                            countDownEvent= Observable.interval(100, TimeUnit.MILLISECONDS)
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .doOnNext(x->{
//                                            circularProgressBar.setProgress(circularProgressBar.getProgress()+1f);
//                                        })
//                                        .takeUntil(aLong->aLong==100)
//                                        .doOnComplete(()->{
//
//
//                                        createTripPlan(event,duration,distance);
//
//
//                                        }).subscribe();
//
//
//
//
//
//
//
//
//
//                            }
//
//                            catch (Exception e)
//                            {
//
//                            }
//
//
//
//
//                        })
//
//        );
//
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private void createTripPlan(DriverRequestReceived event, String duration, String distance) {
//
//
//        setProcessLayout();
//
//        FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        long timeOffset=snapshot.getValue(Long.class);
//
//                        FirebaseDatabase.getInstance().getReference("RidersInformation")
//                                .child(event.getKey())
//                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                        if (snapshot.exists())
//                                        {
//                                            RiderModel riderModel=snapshot.getValue(RiderModel.class);
//
//                                            TripPlaneModel tripPlaneModel=new TripPlaneModel();
//                                            tripPlaneModel.setDriver(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                            tripPlaneModel.setRider(event.getKey());
//                                            tripPlaneModel.setUser(Common.currentUser);
//                                            tripPlaneModel.setRiderModel(riderModel);
//                                            tripPlaneModel.setOrigin(event.getPickupLocation());
//                                            tripPlaneModel.setOriginString(event.getPuckupLocationString());
//                                            tripPlaneModel.setDestination(event.getDestinationLocation());
//                                            tripPlaneModel.setDestinationString(event.getDestinationLocationString());
//                                            tripPlaneModel.setDistancePickup(distance);
//                                            tripPlaneModel.setDurationPickup(duration);
//                                            tripPlaneModel.setCurrentLat(Common.currentLat);
//                                            tripPlaneModel.setCurrentLng(Common.currentLng);
//
//
//                                            tripNumberId=Common.createUniqueTripNumber(timeOffset);
//
//                                            FirebaseDatabase.getInstance().getReference("Trip")
//                                                    .child(tripNumberId)
//                                                    .setValue(tripPlaneModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//
//                                             txt_rider_name.setText(riderModel.getName());
//                                             txt_start_uber_estimate_time.setText(duration);
//                                             txt_start_uber_estimate_distance.setText(distance);
//
//                                             setOffLineModForDriver(event,duration,distance);
//
//
//
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//
//                                                    Toast.makeText(DriverHome.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//
//
//
//                                        }
//                                        else
//                                        {
//                                            Toast.makeText(DriverHome.this, "can not find rider wth key", Toast.LENGTH_SHORT).show();
//                                        }
//
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private void setOffLineModForDriver(DriverRequestReceived event, String duration, String distance) {
//
//    if (currentUserRef !=null)
//    {
//        currentUserRef.removeValue();
//
//        setProcessLayout(false);
//        layout_accept.setVisibility(View.GONE);
//        layout_start_uber.setVisibility(View.VISIBLE);
//
//
//        isTripStart=true;
//    }
//
//
//    }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private void setProcessLayout(boolean isProcess)
//    {
//
//        int color_dark_grey = -1;
//        if (isProcess) {
//            color_dark_grey = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
//            circularProgressBar.setIndeterminateMode(true);
//            txt_rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_star_24,0);
//
//        } else
//        {
//            color_dark_grey = ContextCompat.getColor(getApplicationContext(), android.R.color.white);
//        circularProgressBar.setIndeterminateMode(false);
//        circularProgressBar.setProgress(0);
//            txt_rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_star_24,0);
//
//
//
//        }
//
//
//
//                txt_estimate_time.setTextColor(color_dark_grey);
//                txt_estimate_distance.setTextColor(color_dark_grey);
//                ImageViewCompat.setImageTintList(img_round, ColorStateList.valueOf(color_dark_grey));
//                txt_rating.setTextColor(color_dark_grey);
//                txt_type_uber.setTextColor(color_dark_grey);
//
//
//            }
//
//
//
////        else
////        {
////
////            int color_white= ContextCompat.getColor(getApplicationContext(),android.R.color.white);
////            circularProgressBar.setIndeterminateMode(true);
////            txt_estimate_time.setTextColor(color_white);
////            txt_estimate_distance.setTextColor(color_white);
////            ImageViewCompat.setImageTintList(img_round, ColorStateList.valueOf(color_white));
////            txt_rating.setTextColor(color_white);
////            txt_rating.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_star_24,0);
////            txt_type_uber.setTextColor(color_white);
////        }
//
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_drawer_home);
//        verifyGoogleAccount();
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        firebaseStorage=FirebaseStorage.getInstance();
//        storageReference=firebaseStorage.getReference();
//        snackbarView=findViewById(R.id.snackbarView);
//
//
//        iGoogleApi_sep = RetrofitClient_sep.getInstance().create(IGoogleApi_sep.class);
//
//
//        txt_rating=findViewById(R.id.txt_rating);
//        txt_type_uber=findViewById(R.id.txt_type_uber);
//        txt_rider_name=findViewById(R.id.txt_rider_name);
//        txt_start_uber_estimate_distance=findViewById(R.id.txt_start_uber_estimate_distance);
//        txt_start_uber_estimate_time=findViewById(R.id.txt_start_uber_estimate_time);
//        img_round=findViewById(R.id.img_round);
//        img_phone_call=findViewById(R.id.img_phone_call);
//        layout_start_uber=findViewById(R.id.layout_start_ubber);
//        btn_start_uber=findViewById(R.id.btn_start_uber);
//
//        chip_decline=findViewById(R.id.chip_decline);
//        circularProgressBar=findViewById(R.id.circularProgressBar);
//        layout_accept=findViewById(R.id.layout_accept);
//        txt_estimate_time=findViewById(R.id.txt_estimate_time);
//        txt_estimate_distance=findViewById(R.id.txt_estimate_distance);
//
//
//
//
//
//        chip_decline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (driverRequestReceived!=null)
//                {
//                    if (countDownEvent!=null)
//                        countDownEvent.dispose();
//                    chip_decline.setVisibility(View.GONE);
//                    layout_accept.setVisibility(View.GONE);
//                    mMap.clear();
//
//                    UsersUtill.sendDeclineRequest(snackbarView,getApplicationContext(),driverRequestReceived.getKey());
//                    driverRequestReceived=null;
//
//                }
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
//        location=new Location(this, new locationListener() {
//            @Override
//            public void locationResponse(LocationResult response) {
//                // Add a icon_marker in Sydney and move the camera
//                Common.currentLat=response.getLastLocation().getLatitude();
//                Common.currentLng=response.getLastLocation().getLongitude();
//                displayLocation();
//
//
//            }
//        });
//
//
//
//
//        Context context;
//        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
//        List<Address> addressList;
//        try
//        {
//            addressList=geocoder.getFromLocation(Common.currentLat,
//                    Common.currentLng,1);
//            city_name=addressList.get(0).getLocality();
//
//            Toast.makeText(getApplicationContext(), ""+city_name, Toast.LENGTH_SHORT).show();
//
//
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//
//
//
//
//
//        locationSwitch=findViewById(R.id.locationSwitch);
//        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//
//                        FirebaseDatabase.getInstance().goOnline();
//                        location.inicializeLocation();
//                        drivers = FirebaseDatabase.getInstance().
//                                getReference(Common.driver_tbl).child(Common.currentUser.getCarType());
//                        geoFire = new GeoFire(drivers);
//
//
//
//                }else{
//                    FirebaseDatabase.getInstance().goOffline();
//                    location.stopUpdateLocation();
//                    currentLocationMarket.remove();
//                    mMap.clear();
//                    //handler.removeCallbacks(drawPathRunnable);
//                    if (currentLocationMarket!=null)currentLocationMarket.remove();
//                }
//            }
//        });
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        setUpLocation();
//        updateFirebaseToken();
//    }
//
//    public void initDrawer(){
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        View navigationHeaderView=navigationView.getHeaderView(0);
//        TextView tvName=(TextView)navigationHeaderView.findViewById(R.id.tvDriverName);
//        TextView tvStars=(TextView)navigationHeaderView.findViewById(R.id.tvStars);
//        CircleImageView imageAvatar=(CircleImageView) navigationHeaderView.findViewById(R.id.imageAvatar);
//
//
//
//
//
//
//
//        tvName.setText(Common.currentUser.getName());
//        if(Common.currentUser.getRates()!=null &&
//                !TextUtils.isEmpty(Common.currentUser.getRates()))
//            tvStars.setText(Common.currentUser.getRates());
//
//         if(isLoggedInFacebook)
//            Picasso.get().load("https://graph.facebook.com/" + Common.userID + "/picture?width=500&height=500").into(imageAvatar);
//        else if(account!=null)
//            Picasso.get().load(account.getPhotoUrl()).into(imageAvatar);
//
//        if(Common.currentUser.getAvatarUrl()!=null &&
//                !TextUtils.isEmpty(Common.currentUser.getAvatarUrl()))
//        Picasso.get().load(Common.currentUser.getAvatarUrl()).into(imageAvatar);
//    }
//
//    private void loadUser(){
//        FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl)
//                .child(Common.userID)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Common.currentUser=dataSnapshot.getValue(User.class);
//                        initDrawer();
//                        loadDriverInformation();
//                        onlineRef=FirebaseDatabase.getInstance().getReference().child(".info/connected");
//                        currentUserRef=FirebaseDatabase.getInstance().getReference(Common.driver_tbl).
//                                child(Common.currentUser.getCarType())
//                                .child(Common.userID);
//
//                        onlineRef.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                currentUserRef.onDisconnect().removeValue();
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//    }
//
//
//
//
//    private void loadDriverInformation(){
//        FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl)
//                .child(Common.userID)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Common.currentUser = dataSnapshot.getValue(User.class);
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//    }
//
//    private void verifyGoogleAccount()
//    {
//        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        mGoogleApiClient=new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        OptionalPendingResult<GoogleSignInResult> opr=Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()){
//            GoogleSignInResult result= opr.get();
//            handleSignInResult(result);
//        }else {
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>()
//            {
//                @Override
//                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
//    }
//
//    private void updateFirebaseToken() {
//        FirebaseDatabase db=FirebaseDatabase.getInstance();
//        final DatabaseReference tokens=db.getReference(Common.token_tbl);
//
//        final Token token=new Token(FirebaseInstanceId.getInstance().getToken());
//        if(FirebaseAuth.getInstance().getUid()!=null) tokens.child(FirebaseAuth.getInstance().getUid()).setValue(token);
//        else if(account!=null) tokens.child(account.getId()).setValue(token);
//        else{
//            GraphRequest request = GraphRequest.newMeRequest(
//                    accessToken,
//                    new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            String id = object.optString("id");
//                            tokens.child(id).setValue(token);
//                        }
//                    });
//            request.executeAsync();
//        }
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()) {
//            account = result.getSignInAccount();
//            Common.userID=account.getId();
//            loadUser();
//        }else if(isLoggedInFacebook){
//            GraphRequest request = GraphRequest.newMeRequest(
//                    accessToken,
//                    new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            String id=object.optString("id");
//                            Common.userID=id;
//                            loadUser();
//                        }
//                    });
//            request.executeAsync();
//        }else{
//            Common.userID=FirebaseAuth.getInstance().getCurrentUser().getUid();
//            loadUser();
//        }
//    }
//
//    private void setUpLocation() {
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//            }, REQUEST_CODE_PERMISSION);
//        }else{
//            if (checkPlayServices()){
//                buildGoogleApiClient();
//                if (locationSwitch.isChecked())
//                {
//                    drivers= FirebaseDatabase.getInstance().getReference(Common.driver_tbl).
//                            child(Common.currentUser.getCarType());
//                     geoFire=new GeoFire(drivers);
//                    displayLocation();
//                }
//            }
//        }
//    }
//
//    private void buildGoogleApiClient() {
//        mGoogleApiClient=new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }
//
//    private boolean checkPlayServices() {
//        int resultCode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (resultCode!=ConnectionResult.SUCCESS){
//            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
//                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_REQUEST_CODE).show();
//            else {
//                Message.messageError(this, Errors.NOT_SUPPORT);
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }
//
//    private void displayLocation(){
//        if (Common.currentLat!=null && Common.currentLng!=null){
//            if (locationSwitch.isChecked()) {
//                geoFire.setLocation(Common.userID,
//                        new GeoLocation(Common.currentLat, Common.currentLng),
//                        new GeoFire.CompletionListener() {
//                            @Override
//                            public void onComplete(String key, DatabaseError error) {
//                                LatLng currentLocation = new LatLng(Common.currentLat, Common.currentLng);
//                                if (currentLocationMarket != null) currentLocationMarket.remove();
//
//                                currentLocationMarket = mMap.addMarker(new MarkerOptions().position(currentLocation)
//                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker))
//                                        .title("Your Location"));
//                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Common.currentLat,
//                                        Common.currentLng), 15.0f));
//
//                            }
//                        });
//            }
//        }else{
//            Message.messageError(this, Errors.WITHOUT_LOCATION);
//        }
//
//    }
//
//    private void rotateMarket(Marker marker, final float degrees, GoogleMap mMap)
//    {
//        final Handler handler=new Handler();
//        long start= SystemClock.uptimeMillis();
//        final float startRotation=currentLocationMarket.getRotation();
//        final long duration=1500;
//
//        final Interpolator interpolator=new LinearInterpolator();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                long elapsed=SystemClock.uptimeMillis();
//                float t=interpolator.getInterpolation((float)elapsed/duration);
//                float rot=t*degrees+(1-t)*startRotation;
//
//                currentLocationMarket.setRotation(-rot>180?rot/2:rot);
//                if (t<1.0){
//                    handler.postDelayed(this, 16);
//                }
//            }
//        });
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap)
//    {
//        mMap = googleMap;
//        mMap.setTrafficEnabled(false);
//        mMap.setIndoorEnabled(false);
//        mMap.setBuildingsEnabled(false);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.uber_style_map));
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode){
//            case REQUEST_CODE_PERMISSION:
//                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    location.onRequestPermissionResult(requestCode, permissions, grantResults);
//                    if (checkPlayServices()){
//                        buildGoogleApiClient();
//                        if (locationSwitch.isChecked())displayLocation();
//                    }
//                }
//
//                break;
//            case PLAY_SERVICES_REQUEST_CODE:
//                break;
//        }
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        displayLocation();
//        location.inicializeLocation();
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        location.inicializeLocation();
//
//        if (!EventBus.getDefault().isRegistered(this))
//        EventBus.getDefault().register(this);
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        location.stopUpdateLocation();
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        switch (id){
//            case R.id.nav_trip_history:
//                showTripHistory();
//                break;
//            case R.id.nav_car_type:
//                showDialogUpdateCarType();
//                break;
//            case R.id.nav_update_info:
//                showDialogUpdateInfo();
//                break;
//            case R.id.nav_change_pwd:
//                if(account!=null)
//                    showDialogChangePwd();
//                break;
//            case R.id.nav_sign_out:
//                signOut();
//                break;
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    private void showTripHistory() {
//        Intent intent=new Intent(DriverHome.this, TripHistory.class);
//        startActivity(intent);
//    }
//
//    private void showDialogUpdateCarType() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DriverHome.this);
//        alertDialog.setTitle("UPDATE VEHICLE TYPE");
//        LayoutInflater inflater = this.getLayoutInflater();
//        View carType = inflater.inflate(R.layout.layout_update_car_type, null);
//        final RadioButton rbUberX=carType.findViewById(R.id.rbUberX);
//        final RadioButton rbUberBlack=carType.findViewById(R.id.rbUberBlack);
//
//        if(Common.currentUser.getCarType().equals("UberX"))
//            rbUberX.setChecked(true);
//        else if(Common.currentUser.getCarType().equals("Uber Black"))
//            rbUberBlack.setChecked(true);
//
//        alertDialog.setView(carType);
//        alertDialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                final android.app.AlertDialog waitingDialog = new SpotsDialog.Builder().setContext(DriverHome.this).build();
//                waitingDialog.show();
//                Map<String, Object> updateInfo=new HashMap<>();
//                if(rbUberX.isChecked())
//                    updateInfo.put("carType", rbUberX.getText().toString());
//                else if(rbUberBlack.isChecked())
//                    updateInfo.put("carType", rbUberBlack.getText().toString());
//
//                DatabaseReference driverInformation = FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl);
//                driverInformation.child(Common.userID)
//                        .updateChildren(updateInfo)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                waitingDialog.dismiss();
//                                if(task.isSuccessful())
//                                    Toast.makeText(DriverHome.this,"Information Updated!",Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(DriverHome.this,"Information Update Failed!",Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                driverInformation.child(Common.userID)
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                Common.currentUser=dataSnapshot.getValue(User.class);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//            }
//        });
//        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        alertDialog.show();
//    }
//
//    private void showDialogUpdateInfo() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DriverHome.this);
//        alertDialog.setTitle("UPDATE INFORMATION");
//        LayoutInflater inflater = this.getLayoutInflater();
//        View layout_pwd = inflater.inflate(R.layout.layout_update_information, null);
//        final MaterialEditText etName = (MaterialEditText) layout_pwd.findViewById(R.id.etName);
//        final MaterialEditText etPhone = (MaterialEditText) layout_pwd.findViewById(R.id.etPhone);
//        final ImageView image_upload = (ImageView) layout_pwd.findViewById(R.id.imageUpload);
//        image_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseImage();
//            }
//        });
//        alertDialog.setView(layout_pwd);
//        alertDialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                final android.app.AlertDialog waitingDialog = new SpotsDialog.Builder().setContext(DriverHome.this).build();
//                waitingDialog.show();
//                String name = etName.getText().toString();
//                String phone = etPhone.getText().toString();
//
//                Map<String, Object> updateInfo = new HashMap<>();
//                if(!TextUtils.isEmpty(name))
//                    updateInfo.put("name", name);
//                if(!TextUtils.isEmpty(phone))
//                    updateInfo.put("phone",phone);
//                DatabaseReference driverInformation = FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl);
//                driverInformation.child(Common.userID)
//                        .updateChildren(updateInfo)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                waitingDialog.dismiss();
//                                if(task.isSuccessful())
//                                    Toast.makeText(DriverHome.this,"Information Updated!",Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(DriverHome.this,"Information Update Failed!",Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//            }
//        });
//        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        alertDialog.show();
//    }
//
//    private void chooseImage() {
//        Dexter.withContext(this)
//                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if(report.areAllPermissionsGranted()){
//                            Intent intent=new Intent(Intent.ACTION_PICK);
//                            intent.setType("image/*");
//                            startActivityForResult(intent, Common.PICK_IMAGE_REQUEST);
//                        }else{
//                            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==Common.PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
//            Uri saveUri=data.getData();
//            if(saveUri!=null){
//                final ProgressDialog progressDialog=new ProgressDialog(this);
//                progressDialog.setMessage("Uploading...");
//                progressDialog.show();
//
//                String imageName=UUID.randomUUID().toString();
//                final StorageReference imageFolder=storageReference.child("images/"+imageName);
//
//                imageFolder.putFile(saveUri)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressDialog.dismiss();
//                        Toast.makeText(DriverHome.this, "Uploaded!", Toast.LENGTH_SHORT).show();
//                        imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//
//                                Map<String, Object> avatarUpdate=new HashMap<>();
//                                avatarUpdate.put("avatarUrl", uri.toString());
//
//                                DatabaseReference driverInformations=FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl);
//                                driverInformations.child(Common.userID).updateChildren(avatarUpdate)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if(task.isSuccessful())
//                                            Toast.makeText(DriverHome.this, "Uploaded!", Toast.LENGTH_SHORT).show();
//                                        else
//                                            Toast.makeText(DriverHome.this, "Uploaded error!", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                });
//                            }
//                        });
//                    }
//                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//                        progressDialog.setMessage("Uploaded "+progress+"%");
//                    }
//                });
//            }
//        }
//    }
//
//    private void showDialogChangePwd() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DriverHome.this);
//        alertDialog.setTitle("CHANGE PASSWORD");
//
//
//        LayoutInflater inflater = this.getLayoutInflater();
//        View layout_pwd = inflater.inflate(R.layout.layout_change_pwd, null);
//
//        final MaterialEditText edtPassword = (MaterialEditText) layout_pwd.findViewById(R.id.edtPassword);
//        final MaterialEditText edtNewPassword = (MaterialEditText) layout_pwd.findViewById(R.id.edtNewPassword);
//        final MaterialEditText edtRepeatPassword = (MaterialEditText) layout_pwd.findViewById(R.id.edtRepetPassword);
//
//        alertDialog.setView(layout_pwd);
//
//        alertDialog.setPositiveButton("CHANGE PASSWORD", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                final android.app.AlertDialog waitingDialog = new SpotsDialog.Builder().setContext(DriverHome.this).build();
//                waitingDialog.show();
//
//                if (edtNewPassword.getText().toString().equals(edtRepeatPassword.getText().toString())) {
//                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//
//                    //Get auth credentials from the user for re-authentication.
//                    //Example with only email
//                    AuthCredential credential = EmailAuthProvider.getCredential(email, edtPassword.getText().toString());
//                    FirebaseAuth.getInstance().getCurrentUser()
//                            .reauthenticate(credential)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (task.isSuccessful()) {
//                                        FirebaseAuth.getInstance().getCurrentUser()
//                                                .updatePassword(edtRepeatPassword.getText().toString())
//                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                                        if (task.isSuccessful()) {
//                                                            //update driver information password column
//                                                            Map<String, Object> password = new HashMap<>();
//                                                            password.put("password", edtRepeatPassword.getText().toString());
//                                                            DatabaseReference driverInformation = FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl);
//                                                            driverInformation.child(Common.userID)
//                                                                    .updateChildren(password)
//                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                        @Override
//                                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                                            if (task.isSuccessful())
//                                                                                Toast.makeText(DriverHome.this, "Password was changed!", Toast.LENGTH_SHORT).show();
//                                                                            else
//                                                                                Toast.makeText(DriverHome.this, "Password was doesn't changed!", Toast.LENGTH_SHORT).show();
//                                                                            waitingDialog.dismiss();
//
//                                                                        }
//                                                                    });
//
//                                                        } else {
//                                                            Toast.makeText(DriverHome.this, "Password doesn't change", Toast.LENGTH_SHORT).show();
//
//                                                        }
//                                                    }
//                                                });
//
//                                    } else {
//                                        waitingDialog.dismiss();
//                                        Toast.makeText(DriverHome.this, "Wrong old password", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });
//
//                } else {
//                    waitingDialog.dismiss();
//                    Toast.makeText(DriverHome.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//
//        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        //show dialog
//        alertDialog.show();
//
//    }
//
//    private void signOut() {
//        if(account!=null) {
//            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
//                @Override
//                public void onResult(@NonNull Status status) {
//                    if (status.isSuccess()) {
//                        Intent intent = new Intent(DriverHome.this, signup_and_profile_Activity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(DriverHome.this, "Could not log out", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//        }else if(isLoggedInFacebook){
//            LoginManager.getInstance().logOut();
//            Intent intent = new Intent(DriverHome.this, signup_and_profile_Activity.class);
//            startActivity(intent);
//            finish();
//        }else{
//            FirebaseAuth.getInstance().signOut();
//            Intent intent=new Intent(DriverHome.this, signup_and_profile_Activity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
//
//
//    @Override
//    protected void onDestroy() {
//
//            location.remove_location();
//            geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
//            onlineRef.removeEventListener();
//
//           if (EventBus.getDefault().hasSubscriberForEvent(DriverRequestReceived.class))
//               EventBus.getDefault().removeStickyEvent(DriverRequestReceived.class);
//            EventBus.getDefault().unregister(this );
//            compositeDisposable.clear();
//
//        super.onDestroy();
//
//     }
//
//
//}