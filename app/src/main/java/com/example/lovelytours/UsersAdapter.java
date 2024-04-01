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
import com.example.lovelytours.models.Tourist;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private ArrayList<Tourist> tourists;


    public UsersAdapter(ArrayList<Tourist> tourists) {
        this.tourists = tourists;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tourView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        return new UserViewHolder(tourView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Tourist tourist = tourists.get(position);
        Picasso.get().load(tourist.getImageUri())
                .centerCrop()
                .fit()
                .into(holder.image);

        if (tourist.getFullName() != null) {
            holder.name.setText(tourist.getFullName());
        }

        if (tourist.getPhone() != null) {
            holder.phone.setText(tourist.getPhone());
        }

        holder.email.setText(tourist.getEmail());
    }


    @Override
    public int getItemCount() {
        return tourists.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name, email, phone;
        public ImageView image;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            image = itemView.findViewById(R.id.image);
        }
    }
}
