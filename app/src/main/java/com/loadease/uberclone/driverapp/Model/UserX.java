package com.loadease.uberclone.driverapp.Model;

public class UserX {
    private String email;
    private String name;
    private String password;
    private String phone;
    private String search,id,imageURL,status,avatarUrl,industry;
    public UserX() {
    }
    public UserX(String email, String name, String password, String phone, String search, String id, String imageURL, String status, String avatarUrl, String industry) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.search = search;
        this.id = id;
        this.imageURL = imageURL;
        this.status = status;
        this.avatarUrl = avatarUrl;
        this.industry = industry;
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
    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
}