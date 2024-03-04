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

import java.util.ArrayList;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.tourViewHolder>
{
     private ArrayList<Tour>tours;


     public TourAdapter(ArrayList<Tour> tours)
     {
          this.tours = tours;
     }

     @NonNull
     @Override
     public tourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
     {
          View tourView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_tour, parent , false);

          return new tourViewHolder(tourView);
     }

     @Override
     public void onBindViewHolder(@NonNull tourViewHolder holder, int position)
     {
          Tour currentTours = tours.get(position);
          holder.tvTourName.setText(currentTours.getName());
         // holder.tvTourDate.setText(currentTours.getDate());
          String imageUri = null;
          holder.cardView.setOnClickListener(new View.OnClickListener()
          {
               @Override
               public void onClick(View v)
               {
                    Dialog d=new Dialog(v.getContext());
                    d.setContentView(R.layout.thetours_details);
                    new InviteTourDialog(holder.itemView.getContext(), currentTours).show();

                   d.show();
               }
          });
          imageUri = currentTours.getImage();
          FirebaseStorage storage = FirebaseStorage.getInstance();
          StorageReference storageReference = FirebaseStorage.getInstance().getReference();
          storageReference = storage.getReference("Tours");
          StorageReference storageReference1 = FirebaseStorage.getInstance().getReference();
         Picasso.get().load(imageUri).into(holder.ingTour);


     }



     @Override
     public int getItemCount()
     {
          return tours.size();
     }

     public static class tourViewHolder extends RecyclerView.ViewHolder
     {
          public TextView tvTourName;
          public TextView tvTourDate;
          public ImageView ingTour;
          CardView cardView;

          public tourViewHolder(@NonNull View itemView)
          {
               super(itemView);
               tvTourDate= itemView.findViewById(R.id.tvTourdate);
               tvTourName= itemView.findViewById(R.id.tvTourname);
               ingTour= itemView.findViewById(R.id.ingTour);
               cardView=itemView.findViewById(R.id.cardView);




          }
     }
}
