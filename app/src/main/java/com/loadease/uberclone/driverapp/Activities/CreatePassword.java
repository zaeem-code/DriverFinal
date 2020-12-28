package com.loadease.uberclone.driverapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.R;

import java.util.HashMap;

public class CreatePassword extends AppCompatActivity {

    EditText createPass,confirmPass;
    Button btn_confirm;
    DatabaseReference db;
    String pass1,pass2;
    Bundle extras;
String id_Ui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);


        createPass=findViewById(R.id.confirm_pass);
        confirmPass=findViewById(R.id.confirm_pass1);
        btn_confirm=findViewById(R.id.btn_confirm);
        extras = getIntent().getExtras();





        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (extras != null)
                {
                    id_Ui = extras.getString("id");

                    if (createPass.getText().toString().equals(confirmPass.getText().toString()))
                    {
                        updatePassword(createPass.getText().toString(),id_Ui);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "pass does not match", Toast.LENGTH_SHORT).show();
                    }
                }





            }
        });










    }


    public void updatePassword(String pass,String id)
    {

     db= FirebaseDatabase.getInstance().getReference("DriversInformation");



        HashMap hashMap=new HashMap();
        hashMap.put("password",pass);

     db.child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
         @Override
         public void onSuccess(Object o) {

             startActivity(new Intent(getApplicationContext(),LoginMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
             Toast.makeText(CreatePassword.this, "success", Toast.LENGTH_SHORT).show();
         }
     });




    }

}