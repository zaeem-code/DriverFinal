package com.loadease.uberclone.driverapp.Messages;

public class DriverRequestReceived {


    String key;
    String pickupLocation,puckupLocationString;
    String destinationLocation,destinationLocationString,FromAddress;

    public String getFromAddress() {
        return FromAddress;
    }

    public void setFromAddress(String fromAddress) {
        FromAddress = fromAddress;
    }

    public DriverRequestReceived() {
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getPuckupLocationString() {
        return puckupLocationString;
    }

    public void setPuckupLocationString(String puckupLocationString) {
        this.puckupLocationString = puckupLocationString;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getDestinationLocationString() {
        return destinationLocationString;
    }

    public void setDestinationLocationString(String destinationLocationString) {
        this.destinationLocationString = destinationLocationString;
    }
}
