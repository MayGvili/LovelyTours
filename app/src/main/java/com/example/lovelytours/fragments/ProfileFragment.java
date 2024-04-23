package com.example.lovelytours.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lovelytours.R;
import com.example.lovelytours.activities.RegisterActivity;
import com.example.lovelytours.Session;
import com.example.lovelytours.activities.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class ProfileFragment extends Fragment {

    TextInputEditText fullNameET, phoneET, emailET;
    ImageView profileIV, editIV;
    StorageReference myStorage;
    Button signOut, favoriteBT;
    Bitmap imageBt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        fullNameET = view.findViewById(R.id.etFullname);
        phoneET = view.findViewById(R.id.etPhonenumber);
        emailET = view.findViewById(R.id.regEmail);
        profileIV = view.findViewById(R.id.profile);
        editIV = view.findViewById(R.id.edit);
        editIV.setOnClickListener(v -> onEditClick());
        signOut = view.findViewById(R.id.sign_out);
        signOut.setOnClickListener(v -> signOut());
        favoriteBT = view.findViewById(R.id.favorite);
        favoriteBT.setOnClickListener(v -> openFavoriteScreen());
        return view;
    }

    private void openFavoriteScreen() {
        startActivity(new Intent(getActivity(), FavoritesActivity.class));
    }

    private void onEditClick() {
        Intent intent = new Intent(requireContext(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myStorage = FirebaseStorage.getInstance().getReference();
        Picasso.get().load(Session.getSession().getCurrentUser().getImageUri())
                .centerCrop()
                .fit()
                .into(profileIV);

        fullNameET.setText(Session.getSession().getCurrentUser().getFullName());
        phoneET.setText(Session.getSession().getCurrentUser().getPhone());
        emailET.setText(Session.getSession().getCurrentUser().getEmail());
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Session.getSession().setCurrentUser(null);
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}