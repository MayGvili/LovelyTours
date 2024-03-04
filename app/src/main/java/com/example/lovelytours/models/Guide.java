package com.example.lovelytours.models;

import java.util.ArrayList;

public class Guide extends User {

    private ArrayList<String> createdToursId = new ArrayList<>();

    public Guide() {}

    public Guide(String id, String fullName, String phone, String imageUri) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.imageUri = imageUri;
        this.createdToursId = new ArrayList<>();
    }

    public ArrayList<String> getCreatedToursId() {
        return createdToursId;
    }

    public void setCreatedToursId(ArrayList<String> createdToursId) {
        this.createdToursId = createdToursId;
    }
}


