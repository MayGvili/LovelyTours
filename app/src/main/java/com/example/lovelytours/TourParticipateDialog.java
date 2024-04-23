package com.example.lovelytours;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovelytours.adapters.UsersAdapter;
import com.example.lovelytours.models.Tour;
import com.example.lovelytours.models.Tourist;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class TourParticipateDialog extends Dialog {

    private Tour tour;
    private UsersAdapter adapter;
    private ArrayList<Tourist> tourists = new ArrayList<>();

    public TourParticipateDialog(Context context, Tour tour) {
        super(context);
        this.tour = tour;
    }

    public TourParticipateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        AppCompatButton createBT = findViewById(R.id.create);
        adapter = new UsersAdapter(tourists);
        createBT.setVisibility(View.GONE);
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        DataBaseManager.getTourParticipates(tour, new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                    Tourist tourist = dataSnapshot1.getValue(Tourist.class);
                    tour.getParticipatedIds().forEach(s -> {
                        if (s.equals(tourist.getId())){
                            tourists.add(tourist);
                        }
                    });
                });

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getWindow().setLayout(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT);
    }
}
