package com.example.lovelytours.models;

import java.util.ArrayList;
import java.util.List;

public class User {
   protected String fullName;
    protected String phone;
    protected String imageUri;

    protected String id;

    protected List<Tour> toursList = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User() {
    }
    public User(String id, String fullName, String phone, String imageUri) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.imageUri = imageUri;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getImageUri() { return imageUri;}
    public void setImageUri(String imageUri) {this.imageUri = imageUri;}

    public List<Tour> getToursList() {
        return toursList;
    }

    public void setToursList(List<Tour> toursList) {
        this.toursList = toursList;
    }
}


