package com.example.lovelytours;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lovelytours.models.Tour;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class NearByFragment extends Fragment {


    private GoogleMap map;
    private List<Tour> toursList = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    private Location myLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.near_by_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        SupportMapFragment supportMapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                findMyLocationIfHasAccess();
                fetchTours();
                map = googleMap;
            }
        });

    }

    private void filterTours() {
        Calendar calendar = Calendar.getInstance();
        Calendar temp = Calendar.getInstance();
        temp.add(Calendar.DAY_OF_YEAR, 1);
        int tomorrow = temp.get(Calendar.DAY_OF_YEAR);

        toursList = toursList.stream().filter(new Predicate<Tour>() {
            @Override
            public boolean test(Tour tour) {
                if (tour.getLimPeople() > 0) {
                    temp.setTimeInMillis(tour.getDate());
                    int day = temp.get(Calendar.DAY_OF_YEAR);
                    return (tomorrow == day);
                }
                return false;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                findMyLocation();
            }
        }
    }

    private void fetchTours() {
        DataBaseManager.getTours(new Consumer<List<Tour>>() {
            @Override
            public void accept(List<Tour> tours) {
                toursList.addAll(tours);
                filterTours();
                placeLocations();
            }
        });
    }
    private void placeLocations() {
        Geocoder geocoder = new Geocoder(this.getContext());
        try {
            toursList.forEach(new Consumer<Tour>() {
                @Override
                public void accept(Tour tour) {
                    LatLng latLng = new LatLng(tour.getStartLocation().getLatitude(), tour.getStartLocation().getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(tour.getName()));
//                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
                }
            });


        } catch (Exception e){}
    }

    private void findMyLocationIfHasAccess() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            findMyLocation();
        }
    }

    private void findMyLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful()) {
                                myLocation = task.getResult();
                                markMyLocation();
                            }
                        }
                    });}

    }

    private void markMyLocation() {
        if (map != null && myLocation != null) {
            double latitude = myLocation.getLatitude();
            double longitude = myLocation.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.my_location)));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
        }
    }
}