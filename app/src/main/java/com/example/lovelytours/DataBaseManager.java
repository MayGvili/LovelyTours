package com.example.lovelytours;


import com.example.lovelytours.models.Guide;
import com.example.lovelytours.models.Tour;
import com.example.lovelytours.models.Tourist;
import com.example.lovelytours.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DataBaseManager {

    private static final String USERS = "Users";

    private static final String GUIDES = "Guides";
    private static final String TOURISTS = "Tourists";

    private static final String TOURS = "Tours";

    public static void saveUser(User user, OnSuccessListener listener) {
        FirebaseDatabase.getInstance()
                .getReference(user instanceof Tourist ? TOURISTS : GUIDES)
                .child(user.getId())
                .setValue(user)
                .addOnSuccessListener(listener);
    }

    public static void getTours(Consumer<List<Tour>> onDone) {
        FirebaseDatabase.getInstance()
                .getReference(TOURS)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        List<Tour> toursList = new ArrayList<>();
                        dataSnapshot.getChildren().forEach(new Consumer<DataSnapshot>() {
                            @Override
                            public void accept(DataSnapshot dataSnapshot) {
                                toursList.add(dataSnapshot.getValue(Tour.class));
                            }
                        });
                        onDone.accept(toursList);
                    }
                });

    }

    public static void getUser(String uid, boolean isGuide, OnSuccessListener<DataSnapshot> listener) {
        FirebaseDatabase.getInstance()
                .getReference(isGuide ? GUIDES : USERS)
                .child(uid)
                .get()
                .addOnSuccessListener(listener);
    }

    public static void saveTour(Tour tour, OnSuccessListener<Void> listener) {
        ((Guide) Session.getSession().getCurrentUser()).getCreatedToursId().add(tour.getId());
        FirebaseDatabase.getInstance()
                .getReference(TOURS)
                .child(tour.getId())
                .setValue(tour)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        saveUser(Session.getSession().getCurrentUser(), listener);
                    }
                });
    }

    public static void getTour(String id, OnSuccessListener<DataSnapshot> listener) {
        FirebaseDatabase.getInstance()
                .getReference(TOURS)
                .child(id)
                .get()
                .addOnSuccessListener(listener);
    }

}
