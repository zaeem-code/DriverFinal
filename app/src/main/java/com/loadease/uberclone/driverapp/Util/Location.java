package com.loadease.uberclone.driverapp.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import com.loadease.uberclone.driverapp.Interfaces.locationListener;
import com.loadease.uberclone.driverapp.Messages.Message;
import com.loadease.uberclone.driverapp.Messages.Messages;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class Location {
    Context activity;
    private final String permissionFineLocation=android.Manifest.permission.ACCESS_FINE_LOCATION;
    private final String permissionCoarseLocation=android.Manifest.permission.ACCESS_COARSE_LOCATION;

    private final int REQUEST_CODE_LOCATION=100;

    private FusedLocationProviderClient fusedLocationClient;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    public Location(Context activity, final locationListener locationListener) {
        this.activity=activity;
        fusedLocationClient=new FusedLocationProviderClient(activity.getApplicationContext());

        inicializeLocationRequest();




            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    locationListener.locationResponse(locationResult);
                }
            };




    }
    private void inicializeLocationRequest(){



            locationRequest = new LocationRequest();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(3000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        }
    private Boolean validatePermissionsLocation(){
        final Boolean fineLocationAvailable= ActivityCompat.checkSelfPermission(activity.getApplicationContext(), permissionFineLocation)== PackageManager.PERMISSION_GRANTED;
        final Boolean coarseLocationAvailable= ActivityCompat.checkSelfPermission(activity.getApplicationContext(), permissionCoarseLocation)== PackageManager.PERMISSION_GRANTED;

        return fineLocationAvailable && coarseLocationAvailable;
    }
    private void permissionRequest(){
        ActivityCompat.requestPermissions((Activity) activity, new String[]{permissionFineLocation, permissionCoarseLocation}, REQUEST_CODE_LOCATION);
    }
    private void requestPermissions(){
        Boolean contextProvider=ActivityCompat.shouldShowRequestPermissionRationale((Activity) activity, permissionFineLocation);

        if (contextProvider)Message.message(activity.getApplicationContext(), Messages.RATIONALE);
        permissionRequest();
    }
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case REQUEST_CODE_LOCATION:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)getLocation();
                else Message.message(activity.getApplicationContext(), Messages.PERMISSION_DENIED);
                break;
        }
    }
    public void inicializeLocation(){
        if (validatePermissionsLocation())getLocation();
        else requestPermissions();
    }
    public void stopUpdateLocation(){
        this.fusedLocationClient.removeLocationUpdates(locationCallback);
    }
    @SuppressLint("MissingPermission")
    public void getLocation(){
        validatePermissionsLocation();

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());



        }

    public  void remove_location()
    {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}
