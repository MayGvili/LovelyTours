package com.example.lovelytours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public abstract class AddNewTours extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    Spinner spinner;
    List <String> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tours);
      /*  spinner=(Spinner)findViewById(R.id.citySpinner);
        cities.add("Holon");
        cities.add("TelAviv");
        cities.add("RishonLezion");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, cities);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent,View view, int position,long id)
    {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),"Selected" + item, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
    */
    }
}