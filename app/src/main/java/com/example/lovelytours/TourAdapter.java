package com.example.lovelytours;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovelytours.models.Tour;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.tourViewHolder> {
    private ArrayList<Tour> tours;


    public TourAdapter(ArrayList<Tour> tours) {
        this.tours = tours;
    }
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @NonNull
    @Override
    public tourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tourView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_tour, parent, false);

        return new tourViewHolder(tourView);
    }

    @Override
    public void onBindViewHolder(@NonNull tourViewHolder holder, int position) {
        Tour tour = tours.get(position);
        Picasso.get().load(tour.getImage())
                .centerCrop()
                .fit()
                .into(holder.image);

        holder.date.setText(dateFormat.format(tour.getDate()));
        holder.time.setText("Start time: " + tour.getStartTime() + " End time: " + tour.getEndTime());
    }


    @Override
    public int getItemCount() {
        return tours.size();
    }

    public static class tourViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, time;
        public ImageView image;

        public tourViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.image);


        }
    }
}
