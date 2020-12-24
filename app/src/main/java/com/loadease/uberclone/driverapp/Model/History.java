package com.loadease.uberclone.driverapp.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;
@IgnoreExtraProperties
public class History {
    private String fromAddress, destinationString, durationPickup, distancePickup,date,time,fare;
    private double total;

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

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

    public History(){
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("distance", distancePickup);
        result.put("endAddress", destinationString);
        result.put("time", durationPickup);
//        result.put("locationEnd", locationEnd);
//        result.put("locationStart", locationStart);
//        result.put("name", name);
        result.put("startAddress", fromAddress);
//        result.put("total", total);
//        result.put("tripDate", tripDate);
        return result;
    }




    public History(String startAddress, String endAddress, String time, String distance, String locationStart, String locationEnd, String tripDate, String name, double total) {
        this.fromAddress = startAddress;
        this.destinationString = endAddress;
        this.durationPickup = time;
        this.distancePickup = distance;
//        this.locationStart = locationStart;
//        this.locationEnd = locationEnd;
//        this.tripDate = tripDate;
//        this.name = name;
//        this.total = total;
    }
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getDestinationString() {
        return destinationString;
    }
    public void setDestinationString(String destinationString) {
        this.destinationString = destinationString;
    }
    public String getDurationPickup() {
        return durationPickup;
    }
    public void setDurationPickup(String durationPickup) {
        this.durationPickup = durationPickup;
    }
    public String getDistancePickup() {
        return distancePickup;
    }
    public void setDistancePickup(String distancePickup) {
        this.distancePickup = distancePickup;
    }
    //    public String getLocationStart() {
//        return locationStart;
//    }
//
//    public void setLocationStart(String locationStart) {
//        this.locationStart = locationStart;
//    }
//
//    public String getLocationEnd() {
//        return locationEnd;
//    }
//
//    public void setLocationEnd(String locationEnd) {
//        this.locationEnd = locationEnd;
//    }
//
//    public String getTripDate() {
//        return tripDate;
//    }
//
//    public void setTripDate(String tripDate) {
//        this.tripDate = tripDate;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}