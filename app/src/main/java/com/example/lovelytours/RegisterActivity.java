package com.example.lovelytours;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.lovelytours.models.Guide;
import com.example.lovelytours.models.Tourist;
import com.example.lovelytours.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth myAuth;
    StorageReference myStorage;
    EditText etFullname , etPhonenumber, regEmail, regPassword;
    Button btnRegister;

    AppCompatImageView cameraImg, galleryImg, profileImg;
    Bitmap imageBt;
    String imageUri;

    RadioGroup groupType;

    private boolean isTourist;




    private final ActivityResultLauncher<Intent> takePhotoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == Activity.RESULT_OK) {
                    imageBt = (Bitmap) result.getData().getExtras().get("data");
                    profileImg.setImageBitmap(imageBt);
                }

    });

    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = result.getData().getData();
                    try {
                        imageBt = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        profileImg.setImageBitmap(imageBt);
                    } catch (IOException e) {

                    }
                }

            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myAuth=FirebaseAuth.getInstance();
        myStorage = FirebaseStorage.getInstance().getReference();
        etFullname=findViewById(R.id.etFullname);
        etPhonenumber=findViewById(R.id.etPhonenumber);
        regEmail=findViewById(R.id.regEmail);
        regPassword=findViewById(R.id.regPassword);
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        profileImg = findViewById(R.id.profile);
        cameraImg = findViewById(R.id.take_photo);
        galleryImg = findViewById(R.id.gallery);
        groupType = findViewById(R.id.group);


        cameraImg.setOnClickListener(this);

        galleryImg.setOnClickListener(this);

        groupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isTourist = checkedId == R.id.tourist;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        galleryLauncher.launch(intent);

    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoLauncher.launch(intent);
    }


    @Override
    public void onClick(View v) {
        if(v==btnRegister) {
            register();
        } else  if (v == cameraImg) {
            openCamera();
        } else if (v == galleryImg) {
            openGallery();
        }
}

    private void register()
    {
        uploadImage(new Runnable(){
            @Override
            public void run() {
                myAuth.createUserWithEmailAndPassword(regEmail.getText().toString(),regPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            myAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder()
                                    .setDisplayName(isTourist ? "Tourist" : "Guide")
                                    .build());
                            Toast.makeText(RegisterActivity.this, "the authentication is successful", Toast.LENGTH_SHORT).show();
                            User user;
                            if (isTourist) {
                                user = new Tourist(myAuth.getUid(), etFullname.getText().toString(),etPhonenumber.getText().toString(), imageUri);;
                            } else {
                                user = new Guide(myAuth.getUid(), etFullname.getText().toString(),etPhonenumber.getText().toString(), imageUri);;
                            }

                            DataBaseManager.saveUser(user);
                            Session.getSession().setCurrentUser(user);
                            //save to shared prf
                            Intent intent= new Intent(RegisterActivity.this, HomePage.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(RegisterActivity.this, "the authentication is failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void uploadImage(Runnable onDone) {
        StorageReference imageRef = myStorage.child("users_images/" + myAuth.getUid()+".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBt.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(imageBytes);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    imageRef.getDownloadUrl().addOnSuccessListener(uri->{
                        imageUri = uri.toString();
                        onDone.run();
                    });
                } else {
                    AlertDialogManager.showMessage(RegisterActivity.this, getString(R.string.error), task.getException().getMessage());
                }
            }
        });


    }
}
