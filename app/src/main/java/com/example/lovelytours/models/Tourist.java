package com.example.lovelytours.models;

public class Tourist extends User {


    public Tourist() {
    }
    public Tourist(String id, String fullName, String phone, String imageUri) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.imageUri = imageUri;
    }

}


