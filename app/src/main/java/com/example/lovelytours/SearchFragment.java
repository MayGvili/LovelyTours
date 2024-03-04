package com.example.lovelytours;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lovelytours.models.Tour;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    FirebaseDatabase myData;
    DatabaseReference myRef;
    ArrayList<Tour> tours;
    RecyclerView myRecycle;
    BottomNavigationView bottomNavigation1;
    Tour tour;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        myData = FirebaseDatabase.getInstance();
        myRef = myData.getReference("Tours");
        tours = new ArrayList<>();
        myRecycle = view.findViewById(R.id.myRecycle);
        ShowTours();


        // Inflate the layout for this fragment
        return view;
    }

    private void openCreateTourScreen() {
        Intent intent = new Intent(this.getContext(), CreateTourActivity.class);
        startActivity(intent);
    }

    private void ShowTours()
    {
            myRef.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {

                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    for( DataSnapshot child: children )
                    {
                        tour = child.getValue(Tour.class);
                        tours.add(tour);
                    }
                    // RecyclerView myRecycle = findViewById(R.id.myRecycle);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
                    myRecycle.setLayoutManager(layoutManager);
                    TourAdapter tourAdapter = new TourAdapter(tours);
                    myRecycle.setAdapter(tourAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

    }

}