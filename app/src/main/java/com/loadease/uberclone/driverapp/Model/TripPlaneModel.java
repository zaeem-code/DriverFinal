package com.loadease.uberclone.driverapp.Model;

public class TripPlaneModel {


    private String rider,driver;
    private User user;
    private RiderModel riderModel;
    private String origin,originString;
    private String destination,destinationString;
    private String distancePickup,distanceDestination;
    private String durationPickup,durationDestination,pic_url,Fare,name,FromAddress, carnum,date,time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getFromAddress() {
        return FromAddress;
    }

    public void setFromAddress(String fromAddress) {
        FromAddress = fromAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }

    private double currentLat,currentLng;
    private boolean isDone,isCancel;
    private String fare;
    private String picture_url;


    public TripPlaneModel() {
    }


    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getRider() {
        return rider;
    }

    public void setRider(String rider) {
        this.rider = rider;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RiderModel getRiderModel() {
        return riderModel;
    }

    public void setRiderModel(RiderModel riderModel) {
        this.riderModel = riderModel;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginString() {
        return originString;
    }

    public void setOriginString(String originString) {
        this.originString = originString;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationString() {
        return destinationString;
    }

    public void setDestinationString(String destinationString) {
        this.destinationString = destinationString;
    }

    public String getDistancePickup() {
        return distancePickup;
    }

    public void setDistancePickup(String distancePickup) {
        this.distancePickup = distancePickup;
    }

    public String getDistanceDestination() {
        return distanceDestination;
    }

    public void setDistanceDestination(String distanceDestination) {
        this.distanceDestination = distanceDestination;
    }

    public String getDurationPickup() {
        return durationPickup;
    }

    public void setDurationPickup(String durationPickup) {
        this.durationPickup = durationPickup;
    }

    public String getDurationDestination() {
        return durationDestination;
    }

    public void setDurationDestination(String durationDestination) {
        this.durationDestination = durationDestination;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public double getCurrentLng() {
        return currentLng;
    }

    public void setCurrentLng(double currentLng) {
        this.currentLng = currentLng;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }
}
