package com.example.lovelytours;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;


public class profile extends Fragment  {

EditText etFullnameProfile,etPhonenumberProfile,etEmailProfile;
    Button btnProfile;
    FirebaseAuth myAuth;
    FirebaseDatabase myData;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
       View view=inflater.inflate(R.layout.fragment_profile,container,false);
       myAuth=FirebaseAuth.getInstance();
       myData=FirebaseDatabase.getInstance();
       myRef=myData.getReference("Users").child(myAuth.getCurrentUser().getUid());
//       etFullnameProfile=(EditText)view.findViewById(R.id.etFullnameProfile);
//       etPhonenumberProfile=(EditText)view.findViewById(R.id.etPhonenumberProfile);
//       etEmailProfile=(EditText)view.findViewById(R.id.etEmailProfile);
//       btnProfile=(Button) view.findViewById(R.id.btnProfile);
       //btnProfile.setOnClickListener(this);
      // showmydetails();

       return view;
    }

  /*  private void showmydetails()
    {
        etEmailProfile.setText(myAuth.getCurrentUser().getEmail().toString());
        myRef.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Iterable<DataSnapshot> children=snapshot.getChildren();
                for (DataSnapshot child:children)
                {
                    Users user=child.getValue(Users.class);
                    etFullnameProfile.setText(user.getFullName());
                    etPhonenumberProfile.setText(user.getPhone());


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v)
    {


    }

   */
}