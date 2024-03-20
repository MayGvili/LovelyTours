package com.example.lovelytours;

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
import android.widget.Toast;

import com.example.lovelytours.models.Guide;
import com.example.lovelytours.models.Tourist;
import com.example.lovelytours.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ProfileFragment extends Fragment {

    EditText fullNameET, phoneET, emailET;
    ImageView profileIV, editIV;
    StorageReference myStorage;
    Bitmap imageBt;

    private final ActivityResultLauncher<Intent> takePhotoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    imageBt = (Bitmap) result.getData().getExtras().get("data");
                    profileIV.setImageBitmap(imageBt);
                }

            });

    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = result.getData().getData();
                    try {
                        imageBt = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), uri);
                        profileIV.setImageBitmap(imageBt);
                    } catch (IOException e) {
                    }
                }
            });

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
        return view;
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
}