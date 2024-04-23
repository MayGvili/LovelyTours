package com.example.lovelytours.models;

public class TourData {

    private Tour tour;
    private boolean isFavorite;

    public TourData(Tour tour, boolean isFavorite) {
        this.tour = tour;
        this.isFavorite = isFavorite;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
