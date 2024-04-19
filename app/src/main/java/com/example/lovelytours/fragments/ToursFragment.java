package com.example.lovelytours.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovelytours.activities.CreateOrRegisterTourActivity;
import com.example.lovelytours.DataBaseManager;
import com.example.lovelytours.R;
import com.example.lovelytours.Session;
import com.example.lovelytours.adapters.TourAdapter;
import com.example.lovelytours.TourParticipateDialog;
import com.example.lovelytours.callbacks.OnItemClickedListener;
import com.example.lovelytours.models.Tour;
import com.example.lovelytours.models.TourData;
import com.example.lovelytours.models.Tourist;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ToursFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppCompatButton createBT;
    private ArrayList<TourData> filterToursList = new ArrayList<>();
    private ArrayList<TourData> tourArrayList = new ArrayList<>();
    private TourAdapter adapter = new TourAdapter(filterToursList, (OnItemClickedListener) position -> {
        if (Session.getSession().isGuide()) {
            onTourClicked(position);
        }
    });
    private boolean showFavorites = false;

    public ToursFragment(boolean showFavorites) {
        this.showFavorites = showFavorites;
    }

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
                Intent intent = new Intent(getContext(), CreateOrRegisterTourActivity.class);
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
                filterToursList.addAll(tourArrayList.stream().filter(new Predicate<TourData>() {
                    @Override
                    public boolean test(TourData tourData) {
                        return charSequence.toString().startsWith(tourData.getTour().getName()) || charSequence.toString().isEmpty();
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
        List<String> ids = showFavorites ? ((Tourist)Session.getSession().getCurrentUser()).getFavoriteToursId() : Session.getSession().getCurrentUser().getToursIdsList();
        for (int i = 0; i < ids.size(); i++) {
            int finalI = i;
            DataBaseManager.getTour(ids.get(i), dataSnapshot -> {
                tourArrayList.add(finalI, new TourData(dataSnapshot.getValue(Tour.class), false));
                if (finalI == ids.size() - 1) {
                    filterToursList.addAll(tourArrayList);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void onTourClicked(int position) {
        Tour tour = filterToursList.get(position).getTour();
        new TourParticipateDialog(requireContext(), tour).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        fetchTours();
    }
}
