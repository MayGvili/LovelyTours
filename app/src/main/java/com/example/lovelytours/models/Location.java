package com.example.lovelytours.models;

public class Location {

    private double latitude, longitude;
    private String title;

    public Location(){}
    public Location(double latitude, double longitude, String title) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }
}
