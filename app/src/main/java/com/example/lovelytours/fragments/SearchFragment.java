package com.example.lovelytours.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lovelytours.DataBaseManager;
import com.example.lovelytours.FirebaseMessagingManager;
import com.example.lovelytours.Session;
import com.example.lovelytours.activities.CreateOrRegisterTourActivity;
import com.example.lovelytours.R;
import com.example.lovelytours.adapters.TourAdapter;
import com.example.lovelytours.callbacks.OnItemClickedListener;
import com.example.lovelytours.models.Tour;
import com.example.lovelytours.models.TourData;
import com.example.lovelytours.models.Tourist;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class SearchFragment extends Fragment {
    FirebaseDatabase myData;
    DatabaseReference myRef;
    ArrayList<TourData> tours;
    RecyclerView myRecycle;
    BottomNavigationView bottomNavigation1;
    Tour tour;
    TourAdapter tourAdapter;
    private ArrayList<TourData> filterToursList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        EditText searchET = view.findViewById(R.id.search);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterToursList.clear();
                filterToursList.addAll(tours.stream().filter(new Predicate<TourData>() {
                    @Override
                    public boolean test(TourData tourData) {
                        return tourData.getTour().getDescription().contains(charSequence) || charSequence.toString().isEmpty();
                    }
                }).collect(Collectors.toList()));
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        myData = FirebaseDatabase.getInstance();
        myRef = myData.getReference("Tours");
        tours = new ArrayList<>();
        myRecycle = view.findViewById(R.id.myRecycle);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        myRecycle.setLayoutManager(layoutManager);
        tourAdapter = new TourAdapter(filterToursList, true, position -> {
            openTourDetails(position);
        }, position -> {
            saveTourToFavorite(position);
        });
        myRecycle.setAdapter(tourAdapter);
        showTours();
        return view;
    }

    private void saveTourToFavorite(int position) {
        TourData selected = tours.get(position);
        if (selected.isFavorite()) {
            selected.setFavorite(false);
            ((Tourist)Session.getSession().getCurrentUser()).getFavoriteToursId().removeIf(s -> s.equals(selected.getTour().getId()));
        } else {
            selected.setFavorite(true);
            ((Tourist)Session.getSession().getCurrentUser()).getFavoriteToursId().add(selected.getTour().getId());
        }
        DataBaseManager.saveUser(Session.getSession().getCurrentUser(), unused -> Toast.makeText(getContext(), getString(R.string.tour_favorite), Toast.LENGTH_LONG).show());
        tourAdapter.notifyItemChanged(position);
    }

    private void showTours() {
        Tourist tourist = (Tourist) Session.getSession().getCurrentUser();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tours.clear();
                filterToursList.clear();
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    tour = child.getValue(Tour.class);
                    boolean isFavorite = false;
                    for (String id: tourist.getFavoriteToursId()) {
                        if (id.equals(tour.getId())) {
                            isFavorite = true;
                            break;
                        }
                    }
                    tours.add(new TourData(tour, isFavorite));
                }
                filterToursList.addAll(tours);
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    private void openTourDetails(int position) {
        Intent intent = new Intent(this.getContext(), CreateOrRegisterTourActivity.class);
        intent.putExtra("tour", tours.get(position).getTour());
        startActivity(intent);
    }

}