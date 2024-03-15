package com.example.lovelytours.models;

import java.util.ArrayList;

public class Guide extends User {

    public Guide() {}

    public Guide(String id, String fullName, String phone, String imageUri) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.imageUri = imageUri;
    }
}


