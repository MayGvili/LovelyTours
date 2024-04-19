package com.example.lovelytours.models;

import java.util.ArrayList;
import java.util.List;

public class Tourist extends User {


    private List<String> favoriteToursId = new ArrayList<>();

    public Tourist() {}

    public Tourist(String id, String email, String fullName, String phone, String imageUri) {
        super(id, email, fullName, phone, imageUri);
    }

    public List<String> getFavoriteToursId() {
        return favoriteToursId;
    }

    public void setFavoriteToursId(List<String> favoriteToursId) {
        this.favoriteToursId = favoriteToursId;
    }
}


