package com.example.lovelytours.models;

import java.io.Serializable;

public class Tour implements Serializable
{
    private String id;
    private int limPeople;
    private long date;
    private String description;
    private String image;
    private String name;
    private String startTime;
    private String endTime;

    private Location startLocation;
    private Location destination;

    public Tour(String id, int limPeople, long date, String description, String image, String name, String startTime, String endTime, Location startLocation, Location destination) {
        this.id = id;
        this.limPeople = limPeople;
        this.date = date;
        this.description = description;
        this.image = image;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.destination = destination;
    }

    public Tour(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLimPeople() {
        return limPeople;
    }

    public void setLimPeople(int limPeople) {
        this.limPeople = limPeople;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }
}
