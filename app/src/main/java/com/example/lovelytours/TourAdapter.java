package com.example.lovelytours;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovelytours.callbacks.OnItemClickedListener;
import com.example.lovelytours.models.Tour;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {
    private final OnItemClickedListener listener;
    private ArrayList<Tour> tours;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");



    public TourAdapter(ArrayList<Tour> tours, OnItemClickedListener listener) {
        this.tours = tours;
        this.listener = listener;
    }


    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tourView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_tour, parent, false);
        return new TourViewHolder(tourView);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tours.get(position);
        Picasso.get().load(tour.getImage())
                .centerCrop()
                .fit()
                .into(holder.image);

        holder.date.setText(dateFormat.format(tour.getDate()));
        holder.time.setText("Start time: " + tour.getStartTime() + " End time: " + tour.getEndTime());
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClicked(position);
            }
        });
        holder.name.setText(tour.getName());
    }


    @Override
    public int getItemCount() {
        return tours.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, time;
        public ImageView image;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.image);
        }
    }
}
