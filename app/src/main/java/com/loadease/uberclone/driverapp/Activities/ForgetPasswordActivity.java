package com.loadease.uberclone.driverapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email;
    Button btn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        email=findViewById(R.id.email);
        btn=findViewById(R.id.btn);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                varifyPhone();

            }
        });





    }


    public void varifyPhone()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference("DriversInformation");
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {


                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        String mail=dataSnapshot.child("email").getValue().toString();

                        if (mail.equals(email.getText().toString().trim()))
                        {
                            String id=dataSnapshot.child("id").getValue().toString();


                            startActivity(new Intent(getApplicationContext(),CreatePassword.class)
                                    .putExtra("id",id).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));



                        }
                        else
                        {
                            Toast.makeText(ForgetPasswordActivity.this, "phone not match", Toast.LENGTH_SHORT).show();

                        }


                    }




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

}