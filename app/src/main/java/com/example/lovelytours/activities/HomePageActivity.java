package com.example.lovelytours.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.lovelytours.AlertDialogManager;
import com.example.lovelytours.fragments.ToursFragment;
import com.example.lovelytours.fragments.NearByFragment;
import com.example.lovelytours.fragments.ProfileFragment;
import com.example.lovelytours.R;
import com.example.lovelytours.fragments.SearchFragment;
import com.example.lovelytours.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationItemView;
    private Fragment searchFragment, myToursFragment, profileFragment;

    FirebaseUser user;
    FirebaseAuth auth;

    private ActivityResultLauncher<String> notificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    AlertDialogManager.showMessage(this,  getString(R.string.alarm), getString(R.string.pay_attention));
                } else {
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        bottomNavigationItemView = findViewById(R.id.bottomNavigationView);
        if (Session.getSession().isGuide()) {
            bottomNavigationItemView.getMenu().removeItem(R.id.button_pageBar_search);
            bottomNavigationItemView.getMenu().removeItem(R.id.tickets);
            bottomNavigationItemView.getMenu().removeItem(R.id.button_pageBar_nearby);
        }else{
            bottomNavigationItemView.getMenu().removeItem(R.id.my_tours);
        }
        bottomNavigationItemView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.button_pageBar_nearby) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framfragment_container, NearByFragment.class, null)
                        .commit();
            } else if (itemId == R.id.button_pageBar_search) {
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framfragment_container,searchFragment, null)
                        .commit();
            } else if (itemId == R.id.button_pageBar_profile) {
                if (profileFragment == null) {
                    profileFragment = new ProfileFragment();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framfragment_container,profileFragment, null)
                        .setReorderingAllowed(true)
                        .commit();
            } else if (itemId == R.id.my_tours || itemId == R.id.tickets) {
                if (myToursFragment == null) {
                    myToursFragment = new ToursFragment(false);
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framfragment_container,myToursFragment, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
            return true;
        });

        Fragment firstFragment = Session.getSession().isGuide() ? new ToursFragment(false) : new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framfragment_container, firstFragment).commit();
    }

}