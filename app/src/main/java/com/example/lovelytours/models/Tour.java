package com.example.lovelytours.models;

public class Tour
{
    private int limPeople;
    private long date;
    private String description;
    private String image;
    private String name;
    private String time;

    private String startTime;
    private String endTime;

    private Location startLocation;
    private Location destination;


    public Tour(int limPeople, long date, String description, String image, String name, String time, String startTime, String endTime, Location startLocation, Location destination) {
        this.limPeople = limPeople;
        this.date = date;
        this.description = description;
        this.image = image;
        this.name = name;
        this.time = time;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.destination = destination;
    }

    public Tour()
    {

    }


    public int getLimPeople() {
        return limPeople;
    }

    public void setLimPeople(int limPeople) {
        this.limPeople = limPeople;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
