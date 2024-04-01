package com.example.lovelytours;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovelytours.callbacks.OnItemClickedListener;
import com.example.lovelytours.models.Tour;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyToursFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppCompatButton createBT;
    private ArrayList<Tour> filterToursList = new ArrayList<>();
    private ArrayList<Tour> tourArrayList = new ArrayList<>();
    private TourAdapter adapter = new TourAdapter(filterToursList, new OnItemClickedListener() {
        @Override
        public void onItemClicked(int position) {
            if (Session.getSession().isGuide()) {
                onTourClicked(position);
            }
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        createBT = view.findViewById(R.id.create);
        createBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateTourActivity.class);
                startActivity(intent);
            }
        });

        createBT.setVisibility(Session.getSession().isGuide() ? View.VISIBLE : View.GONE);
        EditText searchView = view.findViewById(R.id.search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterToursList.clear();
                filterToursList.addAll(tourArrayList.stream().filter(new Predicate<Tour>() {
                    @Override
                    public boolean test(Tour tour) {
                        return charSequence.toString().startsWith(tour.getName()) || charSequence.toString().isEmpty();
                    }
                }).collect(Collectors.toList()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;

    }


    private void fetchTours() {
        tourArrayList.clear();
        filterToursList.clear();
        List<String> ids = Session.getSession().getCurrentUser().getToursIdsList();
        for (int i = 0; i < ids.size(); i++) {
            int finalI = i;
            DataBaseManager.getTour(ids.get(i), new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    tourArrayList.add(finalI, dataSnapshot.getValue(Tour.class));
                    if (finalI == ids.size() - 1) {
                        filterToursList.addAll(tourArrayList);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void onTourClicked(int position) {
        Tour tour = filterToursList.get(position);
        new TourParticipateDialog(requireContext(), tour).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        fetchTours();
    }
}
