package com.example.lovelytours;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lovelytours.models.Guide;
import com.example.lovelytours.models.Tourist;
import com.example.lovelytours.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister, tvForgetPass,tvWelcomeTo,tvLovelyTours;
    Intent intent;
     FirebaseAuth mAuth;
     SharedPreferences sp;
     String email,password;
   private Dialog forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPassword=findViewById(R.id.etPassword);
        etEmail=findViewById(R.id.etEmail);
        forget = new Dialog(this);
        sp=getSharedPreferences("details",0);

        mAuth = FirebaseAuth.getInstance();
        sp.edit().putString("email", mAuth.getCurrentUser().getEmail());
        if (mAuth.getCurrentUser() != null) {
            fetchUserSession();
        }
        email=sp.getString("email",null);
        password=sp.getString("password",null);
        if (email!=null&& password!=null)
        {
            etEmail.setText(email);
            etPassword.setText(password);
        }
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        tvRegister=findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);
        tvWelcomeTo=findViewById(R.id.tvWelcomeTo);
        tvLovelyTours=findViewById(R.id.tvLovelyTours);
        mAuth = FirebaseAuth.getInstance();
        tvForgetPass=findViewById(R.id.tvForgetPass);
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.tvForgetPass) {
                    createForgetDialog();

                }}
        private void createForgetDialog()
    {

            forget.setContentView(R.layout.forgetpassword);
            forget.setCancelable(true);
            forget.show();

                EditText mailReset = forget.findViewById(R.id.forgetEmail);

                forget.findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        String userEmail = mailReset.getText().toString();

                        if(TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(MainActivity.this, "Enter Your Email ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                                    forget.dismiss();
                                } else {
                                    Toast.makeText(MainActivity.this, "Unable To Send, Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
               forget.findViewById(R.id.xImageButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                   public void onClick(View view) {
                       forget.dismiss();
                   }
                });
                if (forget.getWindow() != null) {
                    forget.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                forget.show();
            }
        });

    }

    private void fetchUserSession() {
        boolean isGuide = "Guide".equals(mAuth.getCurrentUser().getDisplayName());
        DataBaseManager.getUser(mAuth.getUid(),isGuide, new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                User user;
                if (isGuide){
                    user = dataSnapshot.getValue(Guide.class);
                } else {
                    user = dataSnapshot.getValue(Tourist.class);
                }
                Session.getSession().setCurrentUser(user);
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view==btnLogin)
        {
            login();

        }
        if(view==tvRegister)
        {
            intent= new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

        //if(view==tvForgetPass){
          //  intent= new Intent(MainActivity.this, ForgetPassActi.class);
           // startActivity(intent);
      //  }

    }

    private void login()
    {
        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Authentication is sucssesful",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor=sp.edit();//גישה לעורך
                    editor.putString("email",etEmail.getText().toString());
                    editor.putString("password",etPassword.getText().toString());
                    editor.commit();
                    intent= new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

}