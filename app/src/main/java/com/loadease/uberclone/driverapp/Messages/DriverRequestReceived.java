package com.loadease.uberclone.driverapp.Messages;

public class DriverRequestReceived {


    String key;
    String pickupLocation,puckupLocationString;
    String destinationLocation,destinationLocationString,FromAddress,phone,name,imageurl,lsbourdetails,insurancedetails,industry,vehicaltype;

    public String getLsbourdetails() {
        return lsbourdetails;
    }

    public void setLsbourdetails(String lsbourdetails) {
        this.lsbourdetails = lsbourdetails;
    }

    public String getInsurancedetails() {
        return insurancedetails;
    }

    public void setInsurancedetails(String insurancedetails) {
        this.insurancedetails = insurancedetails;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getVehicaltype() {
        return vehicaltype;
    }

    public void setVehicaltype(String vehicaltype) {
        this.vehicaltype = vehicaltype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

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
