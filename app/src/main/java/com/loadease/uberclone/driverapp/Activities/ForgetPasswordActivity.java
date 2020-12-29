package com.loadease.uberclone.driverapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.R;

import java.util.HashMap;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email;
    Button btn;
    DatabaseReference databaseReference;

    String id="";

    EditText createPass,confirmPass;
    Button btn_confirm;
    DatabaseReference db;
    String pass1,pass2;
    Bundle extras;
    String id_Ui;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

         dialog = new ProgressDialog(this);
        dialog.setMessage("Validating...");
        email=findViewById(R.id.email);
        btn=findViewById(R.id.btn);



        createPass=findViewById(R.id.confirm_pass);
        confirmPass=findViewById(R.id.confirm_pass1);
        btn_confirm=findViewById(R.id.btn_confirm);
        extras = getIntent().getExtras();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (!TextUtils.isEmpty(email.getText().toString().trim())) {
    dialog.show();
    varifyMail();
}else {
    email.setError("Invalid Email");
}
            }
        });




        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(id))
                {
                    id_Ui =id;

                    if (createPass.getText().toString().equals(confirmPass.getText().toString()))
                    {
                        dialog.show();
                        updatePassword(createPass.getText().toString(),id_Ui);

                    }
                    else
                    {
                        createPass.setError("Password does not match");
//                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }





            }
        });




    }


    public void varifyMail()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference("DriversInformation");
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {

try {
    for (DataSnapshot dataSnapshot:snapshot.getChildren())
    {
        String mail=dataSnapshot.child("email").getValue().toString();

        if (mail.equals(email.getText().toString().trim()))
        {
            id=dataSnapshot.child("id").getValue().toString();


            findViewById(R.id.emaillyt).setVisibility(View.GONE);
            findViewById(R.id.createpasslyt).setVisibility(View.VISIBLE);

            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }
        else
        {if (dialog.isShowing()){
            dialog.dismiss();
        }
            email.setError("Email not Found");

        }


    }
}catch (Exception e){
    if (dialog.isShowing()){
        dialog.dismiss();
    }
}





                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (dialog.isShowing()){
                    dialog.dismiss();
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
                if (dialog.isShowing()){
                dialog.dismiss();
            }

                startActivity(new Intent(getApplicationContext(),LoginMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void onBackPressed() {

        if (!TextUtils.isEmpty(id)) {
            findViewById(R.id.emaillyt).setVisibility(View.VISIBLE);
            findViewById(R.id.createpasslyt).setVisibility(View.GONE);
id="";
            id_Ui="";

        }
        else {super.onBackPressed();
        }
    }
}