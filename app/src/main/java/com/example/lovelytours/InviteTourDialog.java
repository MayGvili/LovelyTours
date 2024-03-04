package com.example.lovelytours;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lovelytours.models.Tour;

public class InviteTourDialog extends Dialog {

    private Tour tour;

    public InviteTourDialog(Context context, Tour tour) {
        super(context);
        this.tour = tour;
    }

    public InviteTourDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thetours_details);

        setTitle(tour.getName());
        setCancelable(true);
        WindowManager.LayoutParams ip= new WindowManager.LayoutParams();
        ip.copyFrom(getWindow().getAttributes());
        ip.width= WindowManager.LayoutParams.MATCH_PARENT;
        ip.height=WindowManager.LayoutParams.MATCH_PARENT;
        Button btnClose= findViewById(R.id.btnClose);
        TextView tourName= findViewById(R.id.tourName);
        TextView tourCity= findViewById(R.id.tourCity);
        TextView tourRegion= findViewById(R.id.tourRegion);
        TextView tourDate= findViewById(R.id.tourDate);
        TextView tourTime= findViewById(R.id.tourTime);
        TextView tourLimPeople= findViewById(R.id.tourLimPeople);
        TextView tvRest= findViewById(R.id.tvRest);
        TextView tourOrder= findViewById(R.id.tourOrder);
        TextView tourDescription=  findViewById(R.id.tourDescription);
        tourName.setText(tour.getName());
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (v== tourOrder)
                {
                    Intent intent = new Intent(v.getContext(), OrderTicket.class);
                    intent.putExtra("tourName",tour.getName());
                    v.getContext().startActivity(intent);
                }
            }
        });
        // tourOrder.setText("להזמנת מקומות לחץ כאן");
        tourLimPeople.setText(String.valueOf(tour.getLimPeople()));
       // tvRest.setText(String.valueOf(tour.getRest()));
        tourDescription.setText(tour.getDescription());
    }


}
