package com.loadease.uberclone.driverapp.Model;

public class User{
    private String email;
    private String name;
    private String password;
    private String phone;
    private String rider_pic_Url,carnum;

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getRider_cnic_pic_url() {
        return rider_cnic_pic_url;
    }

    public void setRider_cnic_pic_url(String rider_cnic_pic_url) {
        this.rider_cnic_pic_url = rider_cnic_pic_url;
    }

    public String getRider_licence_pic() {
        return rider_licence_pic;
    }

    public void setRider_licence_pic(String rider_licence_pic) {
        this.rider_licence_pic = rider_licence_pic;
    }

    public String getRider_vehical_pic() {
        return rider_vehical_pic;
    }

    public void setRider_vehical_pic(String rider_vehical_pic) {
        this.rider_vehical_pic = rider_vehical_pic;
    }

    public String getYear_of_prodution() {
        return year_of_prodution;
    }

    public void setYear_of_prodution(String year_of_prodution) {
        this.year_of_prodution = year_of_prodution;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getRide_type() {
        return Ride_type;
    }

    public void setRide_type(String ride_type) {
        Ride_type = ride_type;
    }

    private String rider_cnic_pic_url;
    private String rider_licence_pic;
    private String rider_vehical_pic;
    private String year_of_prodution;

    public User(String email, String name, String password, String phone, String rider_pic_Url, String rider_cnic_pic_url, String rider_licence_pic, String rider_vehical_pic, String year_of_prodution, String rates, String carType, String gender, String DOB, String ride_type) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.rider_pic_Url = rider_pic_Url;
        this.rider_cnic_pic_url = rider_cnic_pic_url;
        this.rider_licence_pic = rider_licence_pic;
        this.rider_vehical_pic = rider_vehical_pic;
        this.year_of_prodution = year_of_prodution;
        this.rates = rates;
        this.carType = carType;
        Gender = gender;
        this.DOB = DOB;
        Ride_type = ride_type;
    }

    private String rates;
    private String carType;
    private String Gender;
    private String DOB;
    private String Ride_type;

    public User(){

    }

    public User(String email, String name, String password, String phone, String rider_pic_Url, String rates, String carType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.rider_pic_Url = rider_pic_Url;
        this.rates = rates;
        this.carType = carType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getRider_pic_Url() {
        return rider_pic_Url;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public void setRider_pic_Url(String rider_pic_Url) {
        this.rider_pic_Url = rider_pic_Url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
