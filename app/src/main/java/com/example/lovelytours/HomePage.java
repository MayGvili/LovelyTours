package com.example.lovelytours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationItemView;
    //profile frofileFragment = new profile();
    //search searchFragment = new search();
    //tickets ticketsFragment = new tickets();
    //nearBy nearByFragment = new nearBy();
    private Fragment searchFragment, myToursFragment, profileFragment;


    FirebaseUser user;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        auth = FirebaseAuth.getInstance();

        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,frofileFragment).commit();
        //bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           /* @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.button_pageBar_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frofileFragment).commit();
                        return true;
                    case R.id.button_pageBar_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, searchFragment).commit();
                        return true;
                    case R.id.button_pageBar_tickets:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ticketsFragment).commit();
                        return true;
                    case R.id.button_pageBar_nearby:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, nearByFragment).commit();
                        return true;
                }


                return false;
            }
        });
        */

        user = auth.getCurrentUser();


        bottomNavigationItemView = findViewById(R.id.bottomNavigationView);
        if (Session.getSession().isGuide()) {
            bottomNavigationItemView.getMenu().removeItem(R.id.button_pageBar_search);
        }else{
            bottomNavigationItemView.getMenu().removeItem(R.id.my_tours);
        }
        //  bottomNavigationItemView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationItemView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.button_pageBar_nearby) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framfragment_container, NearByFragment.class, null)
                        .addToBackStack("name") // Name can be null
                        .commit();
            } else if (itemId == R.id.button_pageBar_search) {
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framfragment_container,searchFragment, null)
                        .addToBackStack("name") // Name can be null
                        .commit();
            } else if (itemId == R.id.button_pageBar_profile) {
                if (profileFragment == null) {
                    profileFragment = new profile();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framfragment_container,profileFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
            } else if (itemId == R.id.my_tours || itemId == R.id.tickets) {
                if (myToursFragment == null) {
                    myToursFragment = new MyToursFragment();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.framfragment_container,myToursFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
            }
            return true;
        });

        Fragment firstFragment = Session.getSession().isGuide() ? new MyToursFragment() : new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framfragment_container, firstFragment).commit();
    }


    // BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
    /*    @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch(item.getItemId()){
                case R.id.button_pageBar_nearby:
                    fragment = new nearBy();
                    break;
                case R.id.button_pageBar_search:
                    fragment = new search();
                    break;
                case R.id.button_pageBar_tickets:
                    fragment = new tickets();
                    break;
                case R.id.button_pageBar_profile:
                    fragment = new profile();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
            return true;
        }

   };
*/

}