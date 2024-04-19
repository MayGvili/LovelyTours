package com.example.lovelytours.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovelytours.R;
import com.example.lovelytours.callbacks.OnItemClickedListener;
import com.example.lovelytours.models.Tour;
import com.example.lovelytours.models.TourData;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {
    private final OnItemClickedListener listener;
    private ArrayList<TourData> tours;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private boolean showFavoriteButton;
    private OnItemClickedListener favoriteListener;

    public TourAdapter(ArrayList<TourData> tours, OnItemClickedListener listener) {
        this.tours = tours;
        this.listener = listener;
    }

    public TourAdapter(ArrayList<TourData> tours, boolean showFavoriteButton, OnItemClickedListener listener, OnItemClickedListener favoriteListener) {
        this.tours = tours;
        this.listener = listener;
        this.showFavoriteButton = showFavoriteButton;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tourView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_tour, parent, false);
        return new TourViewHolder(tourView);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tours.get(position).getTour();
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
        if (showFavoriteButton) {
            holder.favorite.setOnClickListener(v-> {
                favoriteListener.onItemClicked(position);
            });
            holder.favorite.setVisibility(View.VISIBLE);
            if (tours.get(position).isFavorite()) {
                holder.favorite.setImageResource(R.drawable.baseline_favorite_24);
            } else {
                holder.favorite.setImageResource(R.drawable.baseline_unfavorite);
            }
        } else {
            holder.favorite.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return tours.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, time;
        public ImageView image, favorite;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.image);
            favorite = itemView.findViewById(R.id.favorite);
        }
    }
}
