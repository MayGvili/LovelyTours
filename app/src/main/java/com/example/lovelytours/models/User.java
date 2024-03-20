package com.example.lovelytours.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
   protected String fullName;
    protected String phone;
    protected String imageUri;
    protected String id;
    protected String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected List<String> toursIdsList = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User() {}

    public User(String id, String email, String fullName, String phone, String imageUri) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.imageUri = imageUri;
        this.toursIdsList = new ArrayList<>();
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

    public List<String> getToursIdsList() {
        return toursIdsList;
    }

    public void setToursIdsList(List<String> toursIdsList) {
        this.toursIdsList = toursIdsList;
    }
}


