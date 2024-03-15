package com.example.lovelytours;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovelytours.models.Guide;
import com.example.lovelytours.models.Tour;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyToursFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppCompatButton createBT;
    private ArrayList<Tour> tourList = new ArrayList<>();
    private TourAdapter adapter = new TourAdapter(tourList, null);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.tours_list_fragment, container, false);
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
        return view;

    }


    private void fetchTours() {
        tourList.clear();
        List<String> ids = Session.getSession().getCurrentUser().getToursIdsList();
        for (int i = 0; i < ids.size(); i++) {
            int finalI = i;
            DataBaseManager.getTour(ids.get(i), new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    tourList.add(finalI, dataSnapshot.getValue(Tour.class));
                    if (finalI == ids.size() - 1) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchTours();
    }
}
